package com.mycakes.data.repository

import com.mycakes.data.model.Cake
import com.mycakes.data.remote.WebServices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CakeRepositoryImpl(private val webServices: WebServices) : CakeRepository {
    override fun fetchCakes(): Single<List<Cake>> {
        return webServices.fetchCakes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cakes ->
                cakes.distinct().sortedBy { cake -> cake.title }
            }

    }
}