package com.github.luthfipun.mediaplayer.domain.util

import android.content.Context
import android.widget.Toast

fun Context.setAlert(msg: String?){
    Toast.makeText(this, msg ?: "Internal Server Errors", Toast.LENGTH_LONG).show()
}