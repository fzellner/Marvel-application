package com.fzellner.character.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hero(
    val id:Long,
    val name:String,
    val description:String,
    val thumbnail: String
) : Parcelable