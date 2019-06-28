package com.mycakes.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cake(
    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("desc")
    @Expose
    val desc: String,

    @SerializedName("image")
    @Expose
    val image: String

)