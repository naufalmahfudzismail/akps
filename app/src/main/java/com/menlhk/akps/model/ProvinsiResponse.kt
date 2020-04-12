package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class ProvinsiResponse(
    @SerializedName("email")
    var email: String? = "", // apud_haha@yahoo.co.id
    @SerializedName("id_sumber")
    var idSumber: Int? = 0, // 1
    @SerializedName("nama_gubernur")
    var namaGubernur: Any? = Any(), // null
    @SerializedName("proid")
    var proid: String? = "", // 94
    @SerializedName("provinsi")
    var provinsi: String? = "", // PAPUA
    @SerializedName("wilayah")
    var wilayah: String? = "" // II
)