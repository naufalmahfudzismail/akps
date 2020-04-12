package com.menlhk.akps.presenter

import android.content.Context
import com.google.gson.JsonSyntaxException
import com.menlhk.akps.R
import com.menlhk.akps.contract.UsulanListView
import com.menlhk.akps.contract.UsulanView
import com.menlhk.akps.rest.APIRepository
import com.menlhk.akps.rest.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created By naufa on 09/09/2019
 */
class UsulanPresenter(private val context: Context, private val usulanView: UsulanView? = null) {

    private val service = APIService.makeRetrofitService()

    fun insertUsulan(
        id: String,
        datas: HashMap<String, RequestBody?>,
        files: List<MultipartBody.Part?>
    ) {
        usulanView?.showLoading()

        doAsync {
            runBlocking {
                launch(Dispatchers.IO) {
                    try {
                        val data = service.insertUsulan(id, datas, files)
                        val result = data.await()
                        uiThread {
                            usulanView?.result(result.body())
                            usulanView?.hideLoading()
                        }
                    } catch (e: NullPointerException) {
                        usulanView?.showError(context.getString(R.string.input_offline))
                        usulanView?.hideLoading()

                    }
                    catch (e : JsonSyntaxException){
                        usulanView?.showError(context.getString(R.string.input_offline))
                        usulanView?.hideLoading()
                    }

                    catch (e : Exception){
                        usulanView?.showError(e.localizedMessage.toString())
                        usulanView?.hideLoading()
                    }
                }
            }
        }

    }

    fun getUsulan(id : String, view : UsulanListView){

        view.showLoading()

        doAsync {
            GlobalScope.launch(Dispatchers.Main){
                try {
                    val data = service.getUsulan(id)
                    val result = data.await().body()
                    uiThread {
                        view.result(result)
                        view.hideLoading()
                    }
                }catch (e : Exception){
                    uiThread {
                        view.showError("Tidak ada jaringan")
                        view.hideLoading()
                    }
                }
            }
        }

    }
}