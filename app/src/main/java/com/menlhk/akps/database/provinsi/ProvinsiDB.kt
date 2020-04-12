package com.menlhk.akps.database.provinsi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By naufa on 11/9/2019
 */
@Entity(tableName = "provinsi")
class ProvinsiDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="email")
    var email: String? = null, // apud_haha@yahoo.co.id
    @ColumnInfo(name="id_sumber")
    var idSumber: Int? = null, // 1
    @ColumnInfo(name="nama_gubernur")
    var namaGubernur: String? =  null, // null
    @ColumnInfo(name="proid")
    var proid: String? = null, // 94
    @ColumnInfo(name="provinsi")
    var provinsi: String? = null, // PAPUA
    @ColumnInfo(name="wilayah")
    var wilayah: String? = null // II
)
