package com.github.luthfipun.mediaplayer.domain.util

import com.google.android.exoplayer2.C
import java.util.*

fun setDrmSchemeType(scheme: String): UUID {
    return when (scheme) {
        "widevine" -> C.WIDEVINE_UUID
        "playready" -> C.PLAYREADY_UUID
        else -> C.UUID_NIL
    }
}