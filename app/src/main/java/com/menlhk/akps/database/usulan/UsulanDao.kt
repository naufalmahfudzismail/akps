package com.menlhk.akps.database.usulan

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created By naufa on 11/09/2019
 */

@Dao
interface UsulanDao {

    @Query("SELECT * from usulan")
    fun getAll(): LiveData<List<UsulanDB>>

    @Insert
    fun insert(usulanDB: UsulanDB)

    @Query("DELETE FROM usulan")
    fun deleteAll()

    @Update
    fun update(usulanDB: UsulanDB)

    @Delete
    fun delete(usulanDB: UsulanDB)
}