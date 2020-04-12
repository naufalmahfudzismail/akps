package com.menlhk.akps.database.kabupaten

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

/**
 * Created By naufa on 11/09/2019
 */

class KabupatenRepository(private val kabupatenDao: KabupatenDao) {

    val all: LiveData<List<KabupatenDB>> = kabupatenDao.getAll()

    fun getKabupatenFromProvinsi(proid: String): LiveData<List<KabupatenDB>> {
        return kabupatenDao.getKabupatenPerProvinsi(proid)
    }

    @WorkerThread
    fun insert(kabupaten: KabupatenDB) {
        doAsync {
            kabupatenDao.insert(kabupaten)
        }
    }


    fun count(): LiveData<Int> {
        return kabupatenDao.getCountKab()
    }

    fun update(kabupaten: KabupatenDB) {
        doAsync {
            kabupatenDao.update(kabupaten)
        }
    }

    fun deleteAll() {
        doAsync {
            kabupatenDao.deleteAll()
        }
    }

    fun delete(kabupaten: KabupatenDB) {
        doAsync {
            kabupatenDao.delete(kabupaten)
        }
    }

}