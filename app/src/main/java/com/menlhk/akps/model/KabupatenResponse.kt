package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class KabupatenResponse(
    @SerializedName("email")
    var email: Any? = Any(), // null
    @SerializedName("id_sumber")
    var idSumber: Int? = 0, // 2
    @SerializedName("kabid")
    var kabid: String? = "", // 1109
    @SerializedName("kabkota")
    var kabkota: String? = "", // PIDIE
    @SerializedName("nama_walikota")
    var namaWalikota: Any? = Any(), // null
    @SerializedName("proid")
    var proid: String? = "" // 11
)