package com.menlhk.akps.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqih.talimyuk.tools.ProgressRequestBody
import com.menlhk.akps.R
import com.menlhk.akps.contract.UsulanView
import com.menlhk.akps.database.usulan.UsulanDB
import com.menlhk.akps.database.usulan.UsulanViewModel
import com.menlhk.akps.model.InserUsulanResponse
import com.menlhk.akps.presenter.UsulanPresenter
import com.menlhk.akps.util.SessionManager
import com.menlhk.akps.view.adapter.ArsipAdapter
import kotlinx.android.synthetic.main.arsip_fragment.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.FileNotFoundException

/**
 * Created By naufa on 14/09/2019
 */
class ArsipFragment : Fragment(), UsulanView, ProgressRequestBody.UploadCallBacks {

    private lateinit var arsipAdapter: ArsipAdapter
    private lateinit var usulanViewModel: UsulanViewModel
    private lateinit var usulanPresenter: UsulanPresenter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var usulanDB: UsulanDB
    lateinit var dialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.arsip_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        usulanViewModel = ViewModelProviders.of(this).get(UsulanViewModel::class.java)
        usulanPresenter = UsulanPresenter(context!!, this)

        usulanViewModel.all.observe(this, Observer {
            arsipAdapter = ArsipAdapter(it) { usulan ->
                uploadUsulan(usulan)
                usulanDB = usulan
            }
            rcy_arsip.layoutManager = LinearLayoutManager(context)
            rcy_arsip.itemAnimator = DefaultItemAnimator()
            rcy_arsip.adapter = arsipAdapter

        })

    }

    private fun uploadUsulan(usulan: UsulanDB) {

        val params = HashMap<String, RequestBody?>()

        params["no_surat"] = stringToRequestBody(usulan.noSurat)
        params["tanggal_surat"] = stringToRequestBody(usulan.tanggalSurat)
        params["id_tipe_ps"] = stringToRequestBody(usulan.idTipePs)
        params["nama_lembaga"] = stringToRequestBody(usulan.namaLembaga)
        params["hkm_kelompoktani"] = stringToRequestBody(usulan.hkmKelompoktani)
        params["nama_pengusul"] = stringToRequestBody(usulan.namaPengusul)
        params["no_ktp"] = stringToRequestBody(usulan.noKtp)
        params["alamat"] = stringToRequestBody(usulan.alamat)
        params["pendamping"] = stringToRequestBody(usulan.pendamping)
        params["no_kontak_pendamping"] = stringToRequestBody(usulan.noKontakPendamping)
        params["proid"] = stringToRequestBody(usulan.proid)
        params["kabid"] = stringToRequestBody(usulan.kabid)
        params["kecamatan"] = stringToRequestBody(usulan.kecamatan)
        params["desa"] = stringToRequestBody(usulan.desa)
        params["fungsi_kws"] = stringToRequestBody(usulan.fungsiKws)
        params["jml_kk"] = stringToRequestBody(usulan.jmlKk)
        params["luas_total"] = stringToRequestBody(usulan.luasTotal)
        params["no_telpon"] = stringToRequestBody(usulan.noTelpon)
        params["email"] = stringToRequestBody(usulan.email)
        params["jabatan"] = stringToRequestBody(usulan.jabatan)


        val spFiles = usulan.permohonan?.split(",")
        val dnFiles = usulan.daftarNama?.split(",")
        val shpFiles = usulan.shp?.split(",")


        val files: MutableList<MultipartBody.Part?> = mutableListOf()

        spFiles?.forEachIndexed { i, it ->
            val fileSP = File(it)
            //val type = getMimeType(fileSP.absolutePath)

            val fileReqBody = ProgressRequestBody(fileSP, this)
            val part1 = try {
                MultipartBody.Part.createFormData(
                    "surat_permohonan${i + 1}",
                    fileSP.name,
                    fileReqBody
                )
            } catch (e: FileNotFoundException) {
                null
            } catch (e: Exception) {
                null
            }
            files.add(part1)
        }


        dnFiles?.forEachIndexed { index, it ->
            val fileDN = File(it)
            //val type2 = getMimeType(fileDN.absolutePath)
            val fileReqBody2 = ProgressRequestBody(fileDN, this)
            val part2 = try {
                MultipartBody.Part.createFormData(
                    "daftar_nama${index + 1}",
                    fileDN.name,
                    fileReqBody2
                )
            } catch (e: FileNotFoundException) {
                null
            } catch (e: Exception) {
                null
            }
            files.add(part2)
        }

        shpFiles?.forEachIndexed { index, it ->
            val fileShp = File(it)
            //val type3 = getMimeType(fileShp.absolutePath)
            val fileReqBody3 = ProgressRequestBody(fileShp, this)
            val part3 = try {
                MultipartBody.Part.createFormData(
                    "shp_file${index + 1}",
                    fileShp.name,
                    fileReqBody3
                )
            } catch (e: FileNotFoundException) {
                null
            } catch (e: Exception) {
                null
            }
            files.add(part3)
        }

        params["jml_sp"] = stringToRequestBody(spFiles?.size.toString())
        params["jmlnama"] = stringToRequestBody(dnFiles?.size.toString())
        params["jml_shp"] = stringToRequestBody(shpFiles?.size.toString())

        val user = SessionManager.getInstance(context!!).getIdUser()
        usulanPresenter.insertUsulan(user.toString(), params, files)

    }


    private fun stringToRequestBody(desc: String?): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, desc.toString()
        )
    }


    override fun showLoading() {
        dialog = ProgressDialog(context)
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setMessage("Uploading...")
        dialog.isIndeterminate = false
        dialog.setCancelable(false)
        dialog.max = 100
        dialog.show()
    }

    override fun result(usulan: List<InserUsulanResponse>?) {
        if (usulan != null) {
            usulanViewModel.delete(usulanDB)
            usulanViewModel.all
            toast("Arsip usulan Suskes di upload")
        }
    }

    override fun hideLoading() {
        dialog.dismiss()
    }

    override fun showError(e: String) {
        toast(e)
    }

    override fun onProgressUpdate(percentage: Int) {
        dialog.progress = percentage
    }


}