package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class SkemaResponse(
    @SerializedName("id")
    var id: Int? = 0, // 1
    @SerializedName("kode")
    var kode: String? = "", // HKm
    @SerializedName("tipe")
    var tipe: String? = "" // Hutan Kemasyarakatan
)