package com.menlhk.akps.database.kawasan

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created By naufa on 11/09/2019
 */

@Dao
interface KawasanDao {

    @Query("SELECT * from kawasan")
    fun getAll(): LiveData<List<KawasanDB>>

    @Insert
    fun insert(kawasanDB: KawasanDB)

    @Query("DELETE FROM kawasan")
    fun deleteAll()

    @Update
    fun update(kawasanDB: KawasanDB)

    @Delete
    fun delete(kawasanDB: KawasanDB)
}