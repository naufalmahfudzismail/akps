package com.menlhk.akps.contract

import com.menlhk.akps.model.User

interface LoginContract{

    interface View{
        fun showLoading()
        fun loginResponse(loginResponse: User?)
        fun showError(error : String?)
        fun hideLoading()
    }

    interface Presenter{
        fun getUser(username:String, password:String)
        fun destroyFetch()
    }
}