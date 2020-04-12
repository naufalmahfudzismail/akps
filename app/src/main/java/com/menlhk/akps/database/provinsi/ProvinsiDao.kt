package com.menlhk.akps.database.provinsi

import androidx.lifecycle.LiveData
import androidx.room.*
import com.menlhk.akps.database.provinsi.ProvinsiDB

/**
 * Created By naufa on 11/09/2019
 */

@Dao
interface ProvinsiDao {

    @Query("SELECT * from provinsi ORDER BY proid")
    fun getAll(): LiveData<List<ProvinsiDB>>

    @Insert
    fun insert(provinsiDB: ProvinsiDB)

    @Query("DELETE FROM provinsi")
    fun deleteAll()

    @Update
    fun update(provinsiDB: ProvinsiDB)

    @Delete
    fun delete(provinsiDB: ProvinsiDB)
    
    
}