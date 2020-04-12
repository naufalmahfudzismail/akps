package com.menlhk.akps.model

data class Usulan(
    val id:String,
    val no_surat : String,
    val tanggal : String,
    val skema : String,
    val nama_kph : String,
    val kelompok : String,
    val nama_pengusul : String,
    val no_ktp: String,
    val alamat:String,
    val pendamping: String,
    val provinsi : String,
    val kabupaten : String,
    val kecamatan : String,
    val desa : String,
    val fungsi_kawasan : String,
    val jml_kk : Int,
    val luas_total : Int,
    val surat_permohonan : String,
    val daftar_nama : String,
    val shp_file : String
)