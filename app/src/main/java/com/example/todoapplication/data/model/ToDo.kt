package com.example.todoapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToDo(
    val id: Int,
    val title: String,
    val desc: String?,
    val date: String,
    val saveType: String,
    var isDone: Boolean
): Parcelable

