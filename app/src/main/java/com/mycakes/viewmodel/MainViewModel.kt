package com.mycakes.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycakes.data.model.Cake
import com.mycakes.data.repository.CakeRepository
import com.mycakes.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val cakeRepository: CakeRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    fun fetchCakes() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            cakeRepository.fetchCakes()
                .map { cakes ->
                    cakes.distinct()
                }
                .subscribe({
                    lastFetchedTime = Date()
                    if (it.isEmpty()) {

                        loadingState.value = LoadingState.ERROR("No Cake found")
                    } else {

                        loadingState.value = LoadingState.SUCCESS(it)
                    }
                }, {
                    lastFetchedTime = Date()

                    it.printStackTrace()
                    loadingState.value = LoadingState.ERROR(
                        when (it) {
                            is UnknownHostException -> "No Network"
                            else -> it.localizedMessage
                        }
                    )


                })
        )

    }

    var lastFetchedTime: Date? = null

    val loadingState = MutableLiveData<LoadingState>()

    sealed class LoadingState {
        object LOADING : LoadingState()
        data class SUCCESS(val cakes: List<Cake>) : LoadingState()
        data class ERROR(val message: String) : LoadingState()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getActivity(): Class<out Activity> {
        return MainActivity::class.java
    }
}