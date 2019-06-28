package com.mycakes.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycakes.data.repository.CakeRepository
import com.mycakes.viewmodel.MainViewModel

class MainViewModelFactory constructor(private val cakeRepository: CakeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(cakeRepository) as T
    }

}