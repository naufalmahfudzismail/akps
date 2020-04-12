package com.menlhk.akps.database.skema

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Created By naufa on 11/09/2019
 */


class SkemaRepository(private val skemaDao: SkemaDao) {

    val all: LiveData<List<SkemaDB>> = skemaDao.getAll()

    @WorkerThread
    fun insert(skema : SkemaDB){
        doAsync {
            skemaDao.insert(skema)
        }
    }

    fun update(skema: SkemaDB){
        doAsync {
            skemaDao.update(skema)
        }
    }

    fun deleteAll(){
        doAsync {
            skemaDao.deleteAll()
        }
    }

    fun delete(skema: SkemaDB){
        doAsync {
            skemaDao.delete(skema)
        }
    }
}