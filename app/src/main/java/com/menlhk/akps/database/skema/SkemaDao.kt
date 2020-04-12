package com.menlhk.akps.database.skema

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created By naufa on 11/09/2019
 */

@Dao
interface SkemaDao {

    @Query("SELECT * from skema")
    fun getAll(): LiveData<List<SkemaDB>>

    @Insert
    fun insert(skemaDB: SkemaDB)

    @Query("DELETE FROM skema")
    fun deleteAll()

    @Update
    fun update(skemaDB: SkemaDB)

    @Delete
    fun delete(skemaDB: SkemaDB)
}