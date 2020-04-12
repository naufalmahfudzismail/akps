package com.menlhk.akps.database.kabupaten

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By naufa on 11/9/2019
 */

@Entity(tableName = "kabupaten")
class KabupatenDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="email")
    var email: String? = null, // null
    @ColumnInfo(name="id_sumber")
    var idSumber: Int? = null, // 2
    @ColumnInfo(name="kabid")
    var kabid: String? = null, // 11null9
    @ColumnInfo(name="kabkota")
    var kabkota: String? = null, // PIDIE
    @ColumnInfo(name="nama_walikota")
    var namaWalikota: String? = null, // null
    @ColumnInfo(name="proid")
    var proid: String? = null // 11
) {
}