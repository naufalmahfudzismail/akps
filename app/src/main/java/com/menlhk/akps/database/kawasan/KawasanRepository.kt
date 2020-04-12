package com.menlhk.akps.database.kawasan

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.menlhk.akps.database.kawasan.KawasanDB
import org.jetbrains.anko.doAsync

/**
 * Created By naufa on 11/09/2019
 */

class KawasanRepository(private val kawasanDao: KawasanDao) {

    val all: LiveData<List<KawasanDB>> = kawasanDao.getAll()

    @WorkerThread
    fun insert(kawasan : KawasanDB){
        doAsync {
            kawasanDao.insert(kawasan)
        }
    }

    fun update(kawasan: KawasanDB){
        doAsync {
            kawasanDao.update(kawasan)
        }
    }

    fun deleteAll(){
        doAsync {
            kawasanDao.deleteAll()
        }
    }

    fun delete(kawasan: KawasanDB){
        doAsync {
            kawasanDao.delete(kawasan)
        }
    }

}