package com.menlhk.akps.database.usulan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.menlhk.akps.database.AkpsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created By naufa on 12/09/2019
 */
class UsulanViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val usulanRepository : UsulanRepository

    val all : LiveData<List<UsulanDB>>

    init{
        val usulanDao = AkpsRoomDatabase.getDatabase(application, scope).usulanDao()
        usulanRepository = UsulanRepository(usulanDao)
        all = usulanRepository.all
    }


    fun insert(usulanDB: UsulanDB) = scope.launch(Dispatchers.IO){
        usulanRepository.insert(usulanDB)
    }

    fun update(usulanDB: UsulanDB){
        usulanRepository.update(usulanDB)
    }

    fun delete(usulanDB: UsulanDB){
        usulanRepository.delete(usulanDB)
    }

    fun deleteAll(){
        usulanRepository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}