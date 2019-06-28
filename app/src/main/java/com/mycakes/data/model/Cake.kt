package com.mycakes.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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

):Parcelable