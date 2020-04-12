package com.menlhk.akps.database.usulan

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Created By naufa on 12/09/2019
 */
class UsulanRepository(private val usulanDao : UsulanDao) {

    val all: LiveData<List<UsulanDB>> = usulanDao.getAll()

    @WorkerThread
    fun insert(usulan : UsulanDB){
        doAsync {
            usulanDao.insert(usulan)
        }
    }

    fun update(usulan: UsulanDB){
        doAsync {
            usulanDao.update(usulan)
        }
    }

    fun deleteAll(){
        doAsync {
            usulanDao.deleteAll()
        }
    }

    fun delete(usulan: UsulanDB){
        doAsync {
            usulanDao.delete(usulan)
        }
    }
}