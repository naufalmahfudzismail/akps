package com.menlhk.akps.database.skema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By naufa on 11/09/2019
 */
@Entity(tableName = "skema")
class SkemaDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="serverId")
    var serverId: Int? = null, // 1
    @ColumnInfo(name="kode")
    var kode: String? = null, // HKm
    @ColumnInfo(name="tipe")
    var tipe: String? = null // Hutan Kemasyarakatan
)
