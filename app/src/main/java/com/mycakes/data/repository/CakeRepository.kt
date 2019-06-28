package com.mycakes.data.repository

import com.mycakes.data.model.Cake
import io.reactivex.Single

interface CakeRepository {
    fun fetchCakes(): Single<List<Cake>>
}