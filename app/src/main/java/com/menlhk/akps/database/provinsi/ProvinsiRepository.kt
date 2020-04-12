package com.menlhk.akps.database.provinsi

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Created By naufa on 11/09/2019
 */
class ProvinsiRepository(private val provinsiDao: ProvinsiDao) {
    val all: LiveData<List<ProvinsiDB>> = provinsiDao.getAll()

    @WorkerThread
    fun insert(provinsi : ProvinsiDB){
        doAsync {
            provinsiDao.insert(provinsi)
        }
    }

    fun update(provinsi: ProvinsiDB){
        doAsync {
            provinsiDao.update(provinsi)
        }
    }

    fun deleteAll(){
        doAsync {
            provinsiDao.deleteAll()
        }
    }

    fun delete(provinsi: ProvinsiDB){
        doAsync {
            provinsiDao.delete(provinsi)
        }
    }
}