package com.menlhk.akps.database.usulan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created By naufa on 11/09/2019
 */
@Entity(tableName = "usulan")
class UsulanDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    
    
    @ColumnInfo(name = "alamat")
    var alamat: String? = null, // jalan kenduir
    
    @ColumnInfo(name = "shp")
    var shp: String? = null, 
    @ColumnInfo(name = "daftar_nama")
    var daftarNama: String? = null, 
    @ColumnInfo(name = "permohonan")
    var permohonan: String? = null, 
    
    @ColumnInfo(name = "desa")
    var desa: String? = null, // lenteng
    @ColumnInfo(name = "email")
    var email: String? = null, // jakarta@gmail
    @ColumnInfo(name = "fungsi_kws")
    var fungsiKws: String? = null, // 3
    @ColumnInfo(name = "hkm_kelompoktani")
    var hkmKelompoktani: String? = null, // 12

    @ColumnInfo(name = "id_user")
    var idUser: String? = null, // 169
    
    @ColumnInfo(name = "id_tipe_ps")
    var idTipePs: String? = null, // 4
    
    @ColumnInfo(name = "jabatan")
    var jabatan: String? = null, // karyawan
    @ColumnInfo(name = "jml_kk")
    var jmlKk: String? = null, // 12
    @ColumnInfo(name = "kabid")
    var kabid: String? = null, // 3171
    @ColumnInfo(name = "kecamatan")
    var kecamatan: String? = null, // lenten agung
    @ColumnInfo(name = "luas_total")
    var luasTotal: String? = null, // 200
    @ColumnInfo(name = "nama_lembaga")
    var namaLembaga: String? = null, // 124
    @ColumnInfo(name = "nama_pengusul")
    var namaPengusul: String? = null, // banbank
    @ColumnInfo(name = "no_kontak_pendamping")
    var noKontakPendamping: String? = null, // null
    @ColumnInfo(name = "no_ktp")
    var noKtp: String? = null, // 2927277182
    @ColumnInfo(name = "no_surat")
    var noSurat: String? = null, // 8726282
    @ColumnInfo(name = "no_telpon")
    var noTelpon: String? = null, // null
    @ColumnInfo(name = "pendamping")
    var pendamping: String? = null, // bu risna
    @ColumnInfo(name = "proid")
    var proid: String? = null, // 31
    @ColumnInfo(name = "tanggal_surat")
    var tanggalSurat: String? = null // 2019-8-2
)
