package com.github.luthfipun.mediaplayer.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoItem(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("uri")
    var uri: String? = null,
    @SerializedName("drm_scheme")
    var drmScheme: String? = null,
    @SerializedName("drm_license_uri")
    var drmLicenseUri: String? = null,
    @SerializedName("drm_force_default_license_uri")
    var drmForceDefaultLicenseUri: Boolean? = null,
    @SerializedName("ad_tag_uri")
    var adTagUri: String? = null,
    @SerializedName("clip_end_position_ms")
    var clipEndPositionMs: Long? = null,
    @SerializedName("clip_start_position_ms")
    var clipStartPositionMs: Long? = null
): Serializable