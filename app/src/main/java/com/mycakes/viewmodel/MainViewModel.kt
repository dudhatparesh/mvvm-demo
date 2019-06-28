package com.mycakes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycakes.data.model.Cake
import com.mycakes.data.repository.CakeRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainViewModel constructor(private val cakeRepository: CakeRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    fun fetchCakes() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            cakeRepository.fetchCakes()
                .subscribe({
                    lastFetchedTime = Date()
                    if (it.isEmpty()) {
                        errorMessage.value = "No Cake found"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        cakes.value = it
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    lastFetchedTime = Date()
                    errorMessage.value = it.localizedMessage
                    loadingState.value = LoadingState.ERROR
                })
        )

    }

    var lastFetchedTime: Date? = null

    val cakes: MutableLiveData<List<Cake>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}