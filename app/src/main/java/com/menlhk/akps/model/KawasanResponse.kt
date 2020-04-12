package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class KawasanResponse(
    @SerializedName("fungsi")
    var fungsi: String? = "", // Kawasan Konservasi
    @SerializedName("id")
    var id: Int? = 0 // 5
)