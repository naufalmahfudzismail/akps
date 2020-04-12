package com.menlhk.akps.contract

import com.menlhk.akps.model.InserUsulanResponse
import com.menlhk.akps.model.UsulanResponse

/**
 * Created By naufa on 09/09/2019
 */
interface UsulanView {

    fun showLoading()
    fun result(usulan: List<InserUsulanResponse>?)
    fun hideLoading()
    fun showError(e : String)

}