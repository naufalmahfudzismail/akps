package com.menlhk.akps.model

data class User(
    val Data: Data,
    val Error: String,
    val Message: String,
    val StatusCode: Int
)

data class Data(
    val alamat: Any,
    val created_date: Any,
    val id: Int,
    val id_tipe_ps: Int,
    val jabatan: String,
    val kabid: Any,
    val last_login: Any,
    val level: Any,
    val nama: String,
    val organisasi: Any,
    val proid: Any,
    val role: Int,
    val salt_password: String,
    val status: Int,
    val telp: Any,
    val upt: Any,
    val username: String
)

