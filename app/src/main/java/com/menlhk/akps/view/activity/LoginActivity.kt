package com.menlhk.akps.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.menlhk.akps.R
import com.menlhk.akps.contract.LoginContract
import com.menlhk.akps.contract.RefferenceView
import com.menlhk.akps.database.RefferenceViewModel
import com.menlhk.akps.database.kabupaten.KabupatenDB
import com.menlhk.akps.database.kawasan.KawasanDB
import com.menlhk.akps.database.provinsi.ProvinsiDB
import com.menlhk.akps.database.skema.SkemaDB
import com.menlhk.akps.model.*
import com.menlhk.akps.presenter.LoginPresenter
import com.menlhk.akps.presenter.RefferencePresenter
import com.menlhk.akps.util.SessionManager
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginContract.View {


    private lateinit var progressDialog: ProgressDialog
    private lateinit var response: User
    private lateinit var presenter: LoginPresenter
    private var count: Int? = null
    private lateinit var refferencePresenter: RefferencePresenter
    private lateinit var refferenceViewModel: RefferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog =
            ProgressDialog(this@LoginActivity, R.style.Theme_MaterialComponents_Light_Dialog)
        refferencePresenter = RefferencePresenter(this)
        refferenceViewModel = ViewModelProviders.of(this).get(RefferenceViewModel::class.java)

        refferenceViewModel.getCountKB().observe(this, androidx.lifecycle.Observer {
            count = it
        })


        btn_login.setOnClickListener {
            Log.i("tes", "mess")
            val user = et_username.text?.trim().toString()
            val pass = et_password.text?.trim().toString()

            if (validateLogin(user, pass)) {
                doRequest()
            }
        }
    }

    private fun validateLogin(user: String, pass: String): Boolean {
        return when {
            user.trim().isEmpty() -> {
                et_username.error = "Masukan Username Anda"
                et_username.isFocusable = true
                et_username.requestFocus()
                false
            }
            pass.trim().isEmpty() -> {
                et_password.error = "Masukan Password Anda"
                et_password.requestFocus()
                false
            }
            else -> true
        }

    }

    private fun doRequest() {
        Log.i("tes2", "dorequest")
        presenter = LoginPresenter(this, this)
        presenter.login(et_username.text.toString(), et_password.text.toString())
    }

    override fun showLoading() {
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()
    }

    override fun loginResponse(loginResponse: User?) {
        Log.i("status_code: ", loginResponse?.StatusCode.toString())

        if (loginResponse != null) {
            response = loginResponse
        }
    }

    override fun showError(error: String?) {
        toast(error.toString())
        progressDialog.dismiss()
    }

    override fun hideLoading() {
        if (response.StatusCode.toString() == "400" || response.StatusCode.toString() == "401") {
            toast(response.Error)
            progressDialog.dismiss()
        } else {
            if (count!! <= 0) {
                retriveRefference()
            } else {
                goToHome()
            }
        }
    }

    private fun retriveRefference(): Boolean {

        var done = true
        refferencePresenter.getPreference(object : RefferenceView {
            override fun showLoading() {
                progressDialog.setMessage("Downloading data")
                progressDialog.show()
            }

            override fun hideLoading() {
                progressDialog.dismiss()
                goToHome()

            }

            override fun result(
                provinsi: List<ProvinsiResponse>?,
                skema: List<SkemaResponse>?,
                kawasan: List<KawasanResponse>?,
                kabupatens: List<KabupatenResponse>?
            ) {

                if (provinsi != null && skema != null && kawasan != null && kabupatens != null) {
                    provinsi.forEach {
                        val prov = ProvinsiDB(
                            proid = it.proid,
                            provinsi = it.provinsi,
                            email = it.email,
                            namaGubernur = it.namaGubernur.toString(),
                            wilayah = it.wilayah,
                            idSumber = it.idSumber
                        )
                        refferenceViewModel.insertProvinsi(prov)
                    }

                    skema.forEach {
                        val skem = SkemaDB(
                            serverId = it.id,
                            kode = it.kode,
                            tipe = it.tipe
                        )
                        refferenceViewModel.insertSkema(skem)
                    }

                    kawasan.forEach {
                        val kaws = KawasanDB(
                            fungsi = it.fungsi,
                            serverId = it.id
                        )
                        refferenceViewModel.insertKawasan(kaws)
                    }

                    kabupatens.forEach {
                        val kab = KabupatenDB(
                            kabid = it.kabid,
                            proid = it.proid,
                            kabkota = it.kabkota,
                            namaWalikota = it.namaWalikota.toString(),
                            idSumber = it.idSumber,
                            email = it.email.toString()
                        )

                        refferenceViewModel.insertKabupaten(kab)
                    }
                } else {
                    done = false
                }


            }


            override fun showError(error: String) {
                done = false
                toast(error)
            }
        })

        return done
    }

    private fun goToHome() {
        SessionManager.getInstance(applicationContext).saveUser(response)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        if (SessionManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}