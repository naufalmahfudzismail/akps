package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName

data class InserUsulanResponse(
    @SerializedName("alamat")
    var alamat: String? = "", // jalan kenduir
    @SerializedName("berkas")
    var berkas: String? = "", // 1568020707-72390_KELOMPOK KESEKIAN-SISTEM OPERASI AMOEBA.pptx
    @SerializedName("created_date")
    var createdDate: String? = "", // 2019-09-09 09:18:27
    @SerializedName("desa")
    var desa: String? = "", // lenteng
    @SerializedName("email")
    var email: String? = "", // jakarta@gmail
    @SerializedName("fungsi_kws")
    var fungsiKws: String? = "", // 3
    @SerializedName("hkm_kelompoktani")
    var hkmKelompoktani: String? = "", // 12
    @SerializedName("id")
    var id: Int? = 0, // 1145
    @SerializedName("id_admin")
    var idAdmin: String? = "", // 169
    @SerializedName("id_status_ps")
    var idStatusPs: Int? = 0, // 1
    @SerializedName("id_tipe_ps")
    var idTipePs: String? = "", // 4
    @SerializedName("id_usulan")
    var idUsulan: Int? = 0, // 6773
    @SerializedName("jabatan")
    var jabatan: String? = "", // karyawan
    @SerializedName("jml_kk")
    var jmlKk: String? = "", // 12
    @SerializedName("kabid")
    var kabid: String? = "", // 3171
    @SerializedName("kecamatan")
    var kecamatan: String? = "", // lenten agung
    @SerializedName("luas_total")
    var luasTotal: String? = "", // 200
    @SerializedName("nama_lembaga")
    var namaLembaga: String? = "", // 124
    @SerializedName("nama_pengusul")
    var namaPengusul: String? = "", // banbank
    @SerializedName("no_kontak_pendamping")
    var noKontakPendamping: Any? = Any(), // null
    @SerializedName("no_ktp")
    var noKtp: String? = "", // 2927277182
    @SerializedName("no_surat")
    var noSurat: String? = "", // 8726282
    @SerializedName("no_telpon")
    var noTelpon: Any? = Any(), // null
    @SerializedName("pendamping")
    var pendamping: String? = "", // bu risna
    @SerializedName("proid")
    var proid: String? = "", // 31
    @SerializedName("tanggal_surat")
    var tanggalSurat: String? = "" // 2019-8-2
)