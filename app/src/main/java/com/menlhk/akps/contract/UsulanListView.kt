package com.menlhk.akps.contract

import com.menlhk.akps.model.ListUsulanResponse
import com.menlhk.akps.model.UsulanResponse

/**
 * Created By naufa on 09/09/2019
 */
interface UsulanListView {

    fun showLoading()
    fun result(usulan: List<ListUsulanResponse>?)
    fun hideLoading()
    fun showError(error: String)
}