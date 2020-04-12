package com.menlhk.akps.presenter

import android.content.Context
import com.menlhk.akps.contract.RefferenceView
import com.menlhk.akps.contract.UsulanView
import com.menlhk.akps.model.KabupatenResponse
import com.menlhk.akps.model.KawasanResponse
import com.menlhk.akps.model.ProvinsiResponse
import com.menlhk.akps.model.SkemaResponse
import com.menlhk.akps.rest.APIService
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Response
import java.lang.Exception

/**
 * Created By naufa on 09/09/2019
 */
class RefferencePresenter(private val context: Context) {
    private val service = APIService.makeRetrofitService()
    var job: Job? = null
    fun getPreference(view: RefferenceView) {
        view.showLoading()
        doAsync {
            runBlocking {
                try {

                    var resultProv: Response<List<ProvinsiResponse>>? = null
                    var resultKawasan: Response<List<KawasanResponse>>? = null
                    var skemaResult: Response<List<SkemaResponse>>? = null
                    var resultKabupaten: Response<List<KabupatenResponse>>? = null

                    val jobA = launch(Dispatchers.Main) {
                        val prov = service.getProvinsi()
                        resultProv = prov.await()
                    }
                    jobA.join()
                    val jobB = launch(Dispatchers.Main) {
                        val kawasan = service.getKawasan()
                        resultKawasan = kawasan.await()
                    }
                    jobB.join()
                    val jobC = launch(Dispatchers.Main) {
                        val skema = service.getSkema()
                        skemaResult = skema.await()
                    }
                    jobC.join()
                    val jobD = launch(Dispatchers.Main) {
                        val kabupaten = service.getAllKabupaten()
                        resultKabupaten = kabupaten.await()
                    }
                    jobD.join()

                    uiThread {
                        view.result(
                            resultProv?.body(),
                            skemaResult?.body(),
                            resultKawasan?.body(),
                            resultKabupaten?.body()
                        )
                        view.hideLoading()
                    }
                } catch (e: Exception) {
                    view.showError("Terjadi kesalahan mengambil data")
                }
            }
        }
    }


    fun getAllKabupaten(view: RefferenceView) {

        view.showLoading()
        doAsync {
            GlobalScope.launch(Dispatchers.Main) {
                val data = service.getAllKabupaten()
                val result = data.await()

                uiThread {
                    view.resultKabupaten(result.body())
                    view.hideLoading()
                }
            }
        }
    }


    fun getKabupaten(proid: String, view: RefferenceView) {

        doAsync {
            GlobalScope.launch(Dispatchers.Main) {
                val data = service.getKabupaten(proid)
                val result = data.await()

                uiThread {
                    view.resultKabupaten(result.body())
                    view.hideLoading()
                }
            }
        }
    }

}