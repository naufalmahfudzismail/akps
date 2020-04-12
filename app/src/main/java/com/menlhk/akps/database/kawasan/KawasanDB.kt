package com.menlhk.akps.database.kawasan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By naufa on 11/09/2019
 */
@Entity(tableName = "kawasan")
class KawasanDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="fungsi")
    var fungsi: String? = null, // Kawasan Konservasi
    @ColumnInfo(name="serverId")
    var serverId: Int? = null // 5
)
