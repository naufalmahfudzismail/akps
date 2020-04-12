package com.menlhk.akps.presenter

import android.content.Context
import com.menlhk.akps.contract.LoginContract
import com.menlhk.akps.rest.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created By naufa on 09/09/2019
 */
class LoginPresenter(private val context : Context, private  val view : LoginContract.View ) {

    private val service = APIService.makeRetrofitService()

    fun login(username : String, password : String){
        view.showLoading()
        doAsync {
            runBlocking {
                launch(Dispatchers.IO){
                    try {
                        val login = service.login(username, password)
                        val result = login.await()

                        uiThread {
                            view.loginResponse(result.body())
                            view.hideLoading()
                        }
                    }catch (e : Exception){
                        uiThread {
                            view.showError(e.message.toString())
                            view.hideLoading()
                        }
                    }
                }
            }

        }
    }

}