package com.mycakes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycakes.data.model.Cake

class MainViewModel : ViewModel() {

    val cakes: MutableLiveData<List<Cake>> = MutableLiveData<List<Cake>>().apply {
        value = listOf(
            Cake("Lemon cheesecake", "A cheesecake made of lemon", "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"),
            Cake("Demo 2 ", "Demo 2", "Demo 2"),
            Cake("Demo", "Demo", "Demo 2"),
            Cake("Demo 2 ", "Demo 2", "Demo 2"),
            Cake("Demo", "Demo", "Demo 2"),
            Cake("Demo 2 ", "Demo 2", "Demo 2"),
            Cake("Demo", "Demo", "Demo 2"),
            Cake("Demo 2 ", "Demo 2", "Demo 2"),
            Cake("Demo", "Demo", "Demo 2"),
            Cake("Demo 2 ", "Demo 2", "Demo 2")
        )
    }

    val errorMessage: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }

    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
}