package com.menlhk.akps.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.menlhk.akps.database.kabupaten.KabupatenDB
import com.menlhk.akps.database.kabupaten.KabupatenRepository
import com.menlhk.akps.database.kawasan.KawasanDB
import com.menlhk.akps.database.kawasan.KawasanRepository
import com.menlhk.akps.database.provinsi.ProvinsiDB
import com.menlhk.akps.database.provinsi.ProvinsiRepository
import com.menlhk.akps.database.skema.SkemaDB
import com.menlhk.akps.database.skema.SkemaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created By naufa on 10/09/2019
 */
class RefferenceViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    private val kabupatenRepository: KabupatenRepository
    private val kawasanRepository: KawasanRepository
    private val provinsiRepository: ProvinsiRepository
    private val skemaRepository: SkemaRepository

    val allProvinsi: LiveData<List<ProvinsiDB>>
    val allSkema: LiveData<List<SkemaDB>>
    val allKawasan: LiveData<List<KawasanDB>>

    init {

        val provinsiDao = AkpsRoomDatabase.getDatabase(application, scope).provinsiDao()
        val skemaDao = AkpsRoomDatabase.getDatabase(application, scope).skemaDao()
        val kawasanDao = AkpsRoomDatabase.getDatabase(application, scope).kawasanDao()
        val kabupatenDao = AkpsRoomDatabase.getDatabase(application, scope).kabupatenDao()

        kabupatenRepository = KabupatenRepository(kabupatenDao)
        kawasanRepository = KawasanRepository(kawasanDao)
        skemaRepository = SkemaRepository(skemaDao)
        provinsiRepository = ProvinsiRepository(provinsiDao)

        allProvinsi = provinsiRepository.all
        allKawasan = kawasanRepository.all
        allSkema = skemaRepository.all

    }

    fun getKabupaten(proid: String): LiveData<List<KabupatenDB>> {
        return kabupatenRepository.getKabupatenFromProvinsi(proid)
    }

    fun insertProvinsi(provinsi: ProvinsiDB) = scope.launch(Dispatchers.IO) {
        Log.e("INSERT INI LOH", provinsi.provinsi.toString())
        provinsiRepository.insert(provinsi)
    }

    fun insertKawasan(kawasan: KawasanDB) = scope.launch(Dispatchers.IO) {
        Log.e("INSERT INI LOH", kawasan.id.toString())
        kawasanRepository.insert(kawasan)
    }

    fun insertKabupaten(kabupaten: KabupatenDB) = scope.launch(Dispatchers.IO) {
        Log.e("INSERT INI LOH", kabupaten.proid.toString())
        kabupatenRepository.insert(kabupaten)
    }

    fun insertSkema(skema: SkemaDB) = scope.launch(Dispatchers.IO) {
        Log.e("INSERT INI LOH", skema.kode.toString())
        skemaRepository.insert(skema)
    }


    fun updateProvinsi(provinsi: ProvinsiDB) = scope.launch(Dispatchers.IO) {
        provinsiRepository.update(provinsi)
    }

    fun updateKawasan(kawasan: KawasanDB) = scope.launch(Dispatchers.IO) {
        kawasanRepository.update(kawasan)
    }

    fun updateKabupaten(kabupaten: KabupatenDB) = scope.launch(Dispatchers.IO) {
        kabupatenRepository.update(kabupaten)
    }

    fun updateSkema(skema: SkemaDB) = scope.launch(Dispatchers.IO) {
        skemaRepository.update(skema)
    }


    fun deleteProvinsi(provinsi: ProvinsiDB) = scope.launch(Dispatchers.IO) {
        provinsiRepository.delete(provinsi)
    }

    fun deleteKawasan(kawasan: KawasanDB) = scope.launch(Dispatchers.IO) {
        kawasanRepository.delete(kawasan)
    }

    fun deleteKabupaten(kabupaten: KabupatenDB) = scope.launch(Dispatchers.IO) {
        kabupatenRepository.delete(kabupaten)
    }

    fun deleteSkema(skema: SkemaDB) = scope.launch(Dispatchers.IO) {
        skemaRepository.delete(skema)
    }


    fun getCountKB() : LiveData<Int>{
        return kabupatenRepository.count()
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        provinsiRepository.deleteAll()
        skemaRepository.deleteAll()
        kabupatenRepository.deleteAll()
        kawasanRepository.deleteAll()
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}