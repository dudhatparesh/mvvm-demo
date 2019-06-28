package com.mycakes.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycakes.data.model.Cake
import java.util.*

class MainViewModel : ViewModel() {
    fun fetchCakes() {
        loadingState.value = LoadingState.LOADING
        Handler().postDelayed({
            cakes.value = listOf(
                Cake(
                    "Lemon cheesecake", "Lemon cheesecake",
                    "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
                ),
                Cake(
                    "Lemon cheesecake", "Lemon cheesecake",
                    "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
                ),
                Cake(
                    "Lemon cheesecake", "Lemon cheesecake",
                    "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
                )
            )
            loadingState.value = LoadingState.SUCCESS
        }, 5000)

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
}