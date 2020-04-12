package com.menlhk.akps.database.kabupaten

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created By naufa on 11/09/2019
 */

@Dao
interface KabupatenDao {

    @Query("SELECT * from kabupaten WHERE proid = :proid ORDER BY kabid")
    fun getKabupatenPerProvinsi(proid : String): LiveData<List<KabupatenDB>>

    @Query("SELECT * from kabupaten ")
    fun getAll(): LiveData<List<KabupatenDB>>

    @Query("SELECT COUNT(id) FROM kabupaten")
    fun getCountKab(): LiveData<Int>

    @Insert
    fun insert(kabupatenDB: KabupatenDB)

    @Query("DELETE FROM kabupaten")
    fun deleteAll()

    @Update
    fun update(kabupatenDB: KabupatenDB)

    @Delete
    fun delete(kabupatenDB: KabupatenDB)
}