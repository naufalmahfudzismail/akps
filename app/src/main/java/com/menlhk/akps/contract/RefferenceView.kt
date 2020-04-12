package com.menlhk.akps.contract

import com.menlhk.akps.model.*


interface RefferenceView {

    fun showLoading()
    fun result(provinsi : List<ProvinsiResponse>?, skema : List<SkemaResponse>?,
               kawasan : List<KawasanResponse>?, kabupatens : List<KabupatenResponse>? = null){

    }
    fun hideLoading()

    fun showError(error : String){

    }
    fun resultKabupaten(result : List<KabupatenResponse>?){

    }

}