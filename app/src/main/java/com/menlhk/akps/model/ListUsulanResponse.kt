package com.menlhk.akps.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListUsulanResponse(
    @SerializedName("alamat")
    var alamat: String? = "", // utei8228
    @SerializedName("app")
    var app: Any? = Any(), // null
    @SerializedName("bpdas")
    var bpdas: Any? = Any(), // null
    @SerializedName("cc")
    var cc: Any? = Any(), // null
    @SerializedName("created_date")
    var createdDate: String? = "", // 2019-09-09 05:53:37
    @SerializedName("date_create")
    var dateCreate: Any? = Any(), // null
    @SerializedName("date_kembali")
    var dateKembali: Any? = Any(), // null
    @SerializedName("date_penugasan_upt")
    var datePenugasanUpt: Any? = Any(), // null
    @SerializedName("date_verifikasi_administrasi")
    var dateVerifikasiAdministrasi: Any? = Any(), // null
    @SerializedName("date_verify")
    var dateVerify: Any? = Any(), // null
    @SerializedName("deleted")
    var deleted: Any? = Any(), // null
    @SerializedName("desa")
    var desa: String? = "", // beji timur
    @SerializedName("email")
    var email: String? = "", // bejiemail
    @SerializedName("fungsi_kws")
    var fungsiKws: String? = "", // 3
    @SerializedName("hd_desa")
    var hdDesa: Any? = Any(), // null
    @SerializedName("hkm_kelompoktani")
    var hkmKelompoktani: String? = "", // snisk
    @SerializedName("htr_kelompoktani")
    var htrKelompoktani: Any? = Any(), // null
    @SerializedName("htr_perseorangan")
    var htrPerseorangan: Any? = Any(), // null
    @SerializedName("id")
    var id: Int? = 0, // 6760
    @SerializedName("_id")
    var _id: Any? = Any(), // null
    @SerializedName("id_admin")
    var idAdmin: Int? = 0, // 169
    @SerializedName("id_pengusul")
    var idPengusul: Any? = Any(), // null
    @SerializedName("id_status_ps")
    var idStatusPs: Int? = 0, // 1
    @SerializedName("id_tipe_ps")
    var idTipePs: Int? = 0, // 2
    @SerializedName("jabatan")
    var jabatan: String? = "", // kebersihan
    @SerializedName("jenis_kemitraan")
    var jenisKemitraan: Any? = Any(), // null
    @SerializedName("jenis_lembaga")
    var jenisLembaga: Any? = Any(), // null
    @SerializedName("jml_kk")
    var jmlKk: Int? = 0, // 12
    @SerializedName("jml_kk_update")
    var jmlKkUpdate: Any? = Any(), // null
    @SerializedName("jml_peduduk_perempuan")
    var jmlPedudukPerempuan: Any? = Any(), // null
    @SerializedName("jml_penduduk_laki")
    var jmlPendudukLaki: Any? = Any(), // null
    @SerializedName("jumlah_seluruh")
    var jumlahSeluruh: Any? = Any(), // null
    @SerializedName("kabid")
    var kabid: String? = "", // 1107
    @SerializedName("kabupaten")
    var kabupaten: Any? = Any(), // null
    @SerializedName("kecamatan")
    var kecamatan: String? = "", // beji
    @SerializedName("kemitraan")
    var kemitraan: Any? = Any(), // null
    @SerializedName("kepdes")
    var kepdes: Any? = Any(), // null
    @SerializedName("keterangan")
    var keterangan: Any? = Any(), // null
    @SerializedName("legalitas_pendirian")
    var legalitasPendirian: Any? = Any(), // null
    @SerializedName("lembaga_pendamping")
    var lembagaPendamping: Any? = Any(), // null
    @SerializedName("link_shp")
    var linkShp: Any? = Any(), // null
    @SerializedName("link_surat")
    var linkSurat: Any? = Any(), // null
    @SerializedName("link_surat_permohonan")
    var linkSuratPermohonan: Any? = Any(), // null
    @SerializedName("luas_hk")
    var luasHk: String? = "", // 0
    @SerializedName("luas_hl")
    var luasHl: String? = "", // 0
    @SerializedName("luas_hp")
    var luasHp: String? = "", // 0
    @SerializedName("luas_hpk")
    var luasHpk: String? = "", // 0
    @SerializedName("luas_total")
    var luasTotal: String? = "", // 15
    @SerializedName("luas_update")
    var luasUpdate: Any? = Any(), // null
    @SerializedName("luashp_hpt")
    var luashpHpt: String? = "", // 0
    @SerializedName("nama_lembaga")
    var namaLembaga: String? = "", // shsij
    @SerializedName("nama_pengusul")
    var namaPengusul: String? = "", // hsuss
    @SerializedName("no")
    var no: Any? = Any(), // null
    @SerializedName("no_kontak_pendamping")
    var noKontakPendamping: String? = "", // 08129955882
    @SerializedName("no_ktp")
    var noKtp: String? = "", // jsjs
    @SerializedName("no_surat")
    var noSurat: String? = "", // hsin
    @SerializedName("no_surat_kembali")
    var noSuratKembali: Any? = Any(), // null
    @SerializedName("no_telpon")
    var noTelpon: Any? = Any(), // null
    @SerializedName("pendamping")
    var pendamping: String? = "", // yeuwiw
    @SerializedName("perdes")
    var perdes: Any? = Any(), // null
    @SerializedName("proid")
    var proid: String? = "", // 11
    @SerializedName("status")
    var status: String? = "", // Tahapan Usulan
    @SerializedName("tanggal_diterima")
    var tanggalDiterima: Any? = Any(), // null
    @SerializedName("tanggal_surat")
    var tanggalSurat: String? = "", // 2019-08-01
    @SerializedName("tanggal_surat_kembali")
    var tanggalSuratKembali: Any? = Any(), // null
    @SerializedName("user_create")
    var userCreate: Any? = Any(), // null
    @SerializedName("user_verify")
    var userVerify: Any? = Any(), // null
    @SerializedName("wilayah")
    var wilayah: Any? = Any() // null


) : Serializable {


}