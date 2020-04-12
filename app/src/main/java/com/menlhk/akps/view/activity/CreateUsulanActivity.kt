package com.menlhk.akps.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_usulan.*
import com.fiqih.talimyuk.tools.ProgressRequestBody
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.menlhk.akps.R
import com.menlhk.akps.tools.FileUtils
import okhttp3.MultipartBody
import java.util.*
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import android.util.Log
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import android.location.LocationListener
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.menlhk.akps.contract.RefferenceView
import com.menlhk.akps.contract.UsulanView
import com.menlhk.akps.database.RefferenceViewModel
import com.menlhk.akps.database.kawasan.KawasanDB
import com.menlhk.akps.database.usulan.UsulanDB
import com.menlhk.akps.database.usulan.UsulanViewModel
import com.menlhk.akps.model.*
import com.menlhk.akps.presenter.RefferencePresenter
import com.menlhk.akps.presenter.UsulanPresenter
import com.menlhk.akps.util.ButtonPickFileInterface
import com.menlhk.akps.util.SessionManager
import com.menlhk.akps.view.adapter.FileDynamicAdapter
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

class CreateUsulanActivity : AppCompatActivity(), ProgressRequestBody.UploadCallBacks,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener, UsulanView {


    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null
    private val listener: LocationListener? = null
    private val UPDATE_INTERVAL: Long = 2 * 1000  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private var mLocationRequest: LocationRequest? = null
    private var locationManager: LocationManager? = null

    private lateinit var usulanPresenter: UsulanPresenter
    private var usulanResponse: MutableList<InserUsulanResponse> = mutableListOf()
    private var cbKawasan: MutableList<CheckBox> = mutableListOf()
    private lateinit var refferencePresenter: RefferencePresenter
    private lateinit var refferenceViewModel: RefferenceViewModel
    private lateinit var usulanViewModel: UsulanViewModel

    private lateinit var spAdapter: FileDynamicAdapter
    private lateinit var dnAdapter: FileDynamicAdapter
    private lateinit var shpAdapter: FileDynamicAdapter

    private var sps: MutableList<FileUploadMaterial> = mutableListOf()
    private var dns: MutableList<FileUploadMaterial> = mutableListOf()
    private var shps: MutableList<FileUploadMaterial> = mutableListOf()

    private val spPath: MutableList<String?> = mutableListOf()
    private val dnPath: MutableList<String?> = mutableListOf()
    private val shpPath: MutableList<String?> = mutableListOf()

    private val PERMISSION_REQUEST: Int = 1000
    private val PICK_SP_REQUEST: Int = 1001
    private val PICK_DN_REQUEST: Int = 1002
    private val PICK_SHP_REQUEST: Int = 1003

    private var selectedPrivateUri: Uri? = null


    /*  private var realPathSP: String = ""
      private var realPathDN: String = ""
      private var realPathSHP: String = ""*/

    val id = Locale("in", "ID")
    val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
    val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"

    val simpleBasicDateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, id)
    val newFormat = SimpleDateFormat(TARGET_DATE_FORMAT, id)

    var idProvinsi = ""
    var idSkema = ""
    var idKawasan = ""
    var idKabupaten = ""
    private var realDate = ""


    private lateinit var progressDialog: ProgressDialog
    private lateinit var dialogAlert: DialogInterface
    lateinit var dialog: ProgressDialog

    private var usulan = UsulanDB()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_usulan)

        im_back.setOnClickListener {
            finish()
        }

        progressDialog = ProgressDialog(this, R.style.Theme_MaterialComponents_Light_Dialog)
        refferenceViewModel = ViewModelProviders.of(this).get(RefferenceViewModel::class.java)
        usulanViewModel = ViewModelProviders.of(this).get(UsulanViewModel::class.java)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        usulanPresenter = UsulanPresenter(this, this)
        refferencePresenter = RefferencePresenter(this)

        var kawasans: MutableList<KawasanDB> = mutableListOf()

        refferenceViewModel.allSkema.observe(this, androidx.lifecycle.Observer {
            val namaSkema: MutableList<String> = mutableListOf()

            it.forEach { skema ->
                namaSkema.add(skema.tipe.toString())
            }

            val dropdown = findViewById<Spinner>(R.id.spinner_skema)
            val adapter = ArrayAdapter(
                this@CreateUsulanActivity,
                android.R.layout.simple_spinner_dropdown_item,
                namaSkema
            )
            dropdown.adapter = adapter
            dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    idSkema = it[0].id.toString()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    idSkema = it[position].id.toString()
                }

            }
        })


        refferenceViewModel.allKawasan.observe(this, androidx.lifecycle.Observer {
            kawasans = it as MutableList<KawasanDB>
            if (cbKawasan.isEmpty()) {
                kawasans.forEachIndexed { i, kaw ->
                    val cb = CheckBox(this@CreateUsulanActivity)
                    cb.text = kaw.fungsi
                    cb.id = kaw.id!! + 100
                    ll_kawasan.addView(cb)
                    cbKawasan.add(cb)
                }
            }
        })



        refferenceViewModel.allProvinsi.observe(this, androidx.lifecycle.Observer {

            val namaProvinsi: MutableList<String> = mutableListOf()

            it.forEach { prov ->
                namaProvinsi.add(prov.provinsi.toString())
            }

            val dropdown2 = findViewById<Spinner>(R.id.spinner_provinsi)
            val adapter2 = ArrayAdapter(
                this@CreateUsulanActivity,
                android.R.layout.simple_spinner_dropdown_item,
                namaProvinsi
            )
            dropdown2.adapter = adapter2
            dropdown2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    idProvinsi = it[0].proid.toString()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    idProvinsi = it[position].proid.toString()
                    refferenceViewModel.getKabupaten(idProvinsi)
                        .observe(this@CreateUsulanActivity, androidx.lifecycle.Observer { kabs ->
                            val namaKabupaten: MutableList<String> = mutableListOf()
                            kabs.forEach { kab ->
                                namaKabupaten.add(kab.kabkota.toString())
                            }

                            val dropdown4 =
                                findViewById<Spinner>(R.id.spinner_kabupaten)
                            val adapter4 = ArrayAdapter(
                                this@CreateUsulanActivity,
                                android.R.layout.simple_spinner_dropdown_item,
                                namaKabupaten
                            )
                            dropdown4.adapter = adapter4

                            dropdown4.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        idKabupaten = kabs[0].kabid.toString()
                                    }

                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        idKabupaten =
                                            kabs[position].kabid.toString()
                                    }

                                }
                        })
                }
            }
        })



        checkLocation()


        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)


        et_tanggal.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay
                    ->
                    et_tanggal.setText(newFormat.format(simpleBasicDateFormat.parse("$mYear-${mMonth + 1}-$mDay")!!))
                    realDate = "$mYear-${mMonth + 1}-$mDay"
                },
                year,
                month,
                day
            )

            dpd.show()
            dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            dpd.getButton(DatePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK)
            dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST
            )

        sps.add(FileUploadMaterial())
        shps.add(FileUploadMaterial())
        dns.add(FileUploadMaterial())

        spAdapter = FileDynamicAdapter(this, sps, object : ButtonPickFileInterface {
            override fun clickButton() {
                chooseSP()
            }
        })
        dnAdapter = FileDynamicAdapter(this, dns, object : ButtonPickFileInterface {
            override fun clickButton() {
                chooseDN()
            }
        })
        shpAdapter = FileDynamicAdapter(this, shps, object : ButtonPickFileInterface {
            override fun clickButton() {
                chooseSHP()
            }
        })

        rcy_sp.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcy_sp.setHasFixedSize(true)
        rcy_sp.isNestedScrollingEnabled = false
        rcy_sp.adapter = spAdapter

        rcy_dn.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcy_dn.setHasFixedSize(true)
        rcy_dn.isNestedScrollingEnabled = false
        rcy_dn.adapter = dnAdapter

        rcy_shp.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcy_shp.setHasFixedSize(true)
        rcy_shp.isNestedScrollingEnabled = false
        rcy_shp.adapter = shpAdapter


        /* im_surat_permohonan.setOnClickListener {
             chooseSP()
         }

         im_daftar_nama.setOnClickListener {
             chooseDN()
         }

         im_shp_file.setOnClickListener {
             chooseSHP()
         }
 */
        img_save.setOnClickListener {
            saveUsuluan()
        }
    }


    private fun saveUsuluan() {


        if (selectedPrivateUri != null) {

            val params = HashMap<String, RequestBody?>()
            val nomorSurat = et_nosurat.text.trim().toString()
            val tanggal = realDate.trim()
            val namaKPH = et_namakph.text.trim().toString()
            val kelompok = et_kelompok.text.trim().toString()
            val namaPengusul = et_namapengusul.text.trim().toString()
            val noKtp = et_noktp.text.trim().toString()
            val alamat = et_alamat.text.trim().toString()
            val noTelp = et_no_telp.text.trim().toString()
            val email = et_email.text.trim().toString()
            val jabatan = et_jabatan.text.trim().toString()
            val pendamping = et_pendamping.text.trim().toString()
            val noKontakPenamping =
                et_no_kontak_pendamping.text.trim().toString()
            val kecamatan = et_kecamatan.text.trim().toString()
            val desa = et_desa.text.trim().toString()
            val jumlahKK = et_jmlkk.text.trim().toString()
            val luasTotal = et_luastotal.text.trim().toString()

            val isTrough: Boolean =
                (nomorSurat != "" && namaKPH != "" && kelompok != "" && namaPengusul != "" && noKtp != ""
                        && alamat != "" && noTelp != "" && email != "" && jabatan != "" && pendamping != ""
                        && noKontakPenamping != "" +
                        "" && kecamatan != "" && desa != "" && jumlahKK != "" && luasTotal != "")

            if (isTrough) {
                val arrayKawasan: MutableList<String> = mutableListOf()

                cbKawasan.forEach {
                    if (it.isChecked) {
                        val id = it.id - 100
                        arrayKawasan.add(id.toString())
                    }
                }


                val kawasan = arrayKawasan.joinToString(",")


                params["no_surat"] = stringToRequestBody(nomorSurat)
                usulan.noSurat = nomorSurat.toString()
                params["tanggal_surat"] = stringToRequestBody(tanggal)
                usulan.tanggalSurat = tanggal.toString()
                params["id_tipe_ps"] = stringToRequestBody(idSkema)
                usulan.idTipePs = idSkema
                params["nama_lembaga"] = stringToRequestBody(namaKPH)
                usulan.namaLembaga = namaKPH.toString()
                params["hkm_kelompoktani"] = stringToRequestBody(kelompok)
                usulan.hkmKelompoktani = kelompok.toString()
                params["nama_pengusul"] = stringToRequestBody(namaPengusul)
                usulan.namaPengusul = namaPengusul.toString()
                params["no_ktp"] = stringToRequestBody(noKtp)
                usulan.noKtp = noKtp.toString()
                params["alamat"] = stringToRequestBody(alamat)
                usulan.alamat = alamat.toString()
                params["pendamping"] = stringToRequestBody(pendamping)
                usulan.pendamping = pendamping.toString()
                params["no_kontak_pendamping"] = stringToRequestBody(noKontakPenamping)
                usulan.noKontakPendamping = noKontakPenamping.toString()
                params["proid"] = stringToRequestBody(idProvinsi)
                usulan.proid = idProvinsi
                params["kabid"] = stringToRequestBody(idKabupaten)
                usulan.kabid = idKabupaten
                params["kecamatan"] = stringToRequestBody(kecamatan)
                usulan.kecamatan = kecamatan.toString()
                params["desa"] = stringToRequestBody(desa)
                usulan.desa = desa.toString()
                params["fungsi_kws"] = stringToRequestBody(kawasan)
                usulan.fungsiKws = kawasan
                params["jml_kk"] = stringToRequestBody(jumlahKK)
                usulan.jmlKk = jumlahKK.toString()
                params["luas_total"] = stringToRequestBody(luasTotal)
                usulan.luasTotal = luasTotal.toString()
                params["no_telpon"] = stringToRequestBody(noTelp)
                usulan.noTelpon = noTelp.toString()
                params["email"] = stringToRequestBody(email)
                usulan.email = email.toString()
                params["jabatan"] = stringToRequestBody(jabatan)
                usulan.jabatan = jabatan


                val files: MutableList<MultipartBody.Part?> = mutableListOf()
                spPath.forEachIndexed { i, it ->
                    val fileSP = File(it!!)
                    //val type = getMimeType(fileSP.absolutePath)

                    val fileReqBody = ProgressRequestBody(fileSP, this)
                    val part1 =
                        MultipartBody.Part.createFormData(
                            "surat_permohonan${i + 1}",
                            fileSP.name,
                            fileReqBody
                        )
                    files.add(part1)

                }

                dnPath.forEachIndexed { index, it ->
                    val fileDN = File(it!!)
                    //val type2 = getMimeType(fileDN.absolutePath)
                    val fileReqBody2 = ProgressRequestBody(fileDN, this)
                    val part2 =
                        MultipartBody.Part.createFormData(
                            "daftar_nama${index + 1}",
                            fileDN.name,
                            fileReqBody2
                        )
                    files.add(part2)
                }

                shpPath.forEachIndexed { index, it ->
                    val fileShp = File(it!!)
                    //val type3 = getMimeType(fileShp.absolutePath)
                    val fileReqBody3 = ProgressRequestBody(fileShp, this)
                    val part3 =
                        MultipartBody.Part.createFormData(
                            "shp_file${index + 1}",
                            fileShp.name,
                            fileReqBody3
                        )

                    files.add(part3)
                }


                val user = SessionManager.getInstance(this).getIdUser()

                params["jml_sp"] = stringToRequestBody(spPath.size.toString())
                params["jmlnama"] = stringToRequestBody(dnPath.size.toString())
                params["jml_shp"] = stringToRequestBody(shpPath.size.toString())

                usulan.shp = shpPath.joinToString(",")
                usulan.daftarNama = dnPath.joinToString(",")
                usulan.permohonan = spPath.joinToString(",")
                usulan.idUser = user.toString()

                if (isConnected()) {
                    usulanPresenter.insertUsulan(
                        id = user.toString(),
                        datas = params,
                        files = files
                    )
                } else {
                    dialogAlert = alert(
                        "Jaringan tidak tersedia, Data usulan ini di tangguhkan ke Arsip usulan",
                        "Offline"
                    ) {
                        okButton {
                            usulanViewModel.insert(usulan)
                            finish()
                            startActivity(intentFor<MainActivity>())
                        }
                    }.show()
                }
            } else {
                dialogAlert = alert(title = "Tidak Valid", message = "Harap isi semua field") {
                    okButton {
                        dialogAlert.dismiss()
                    }
                }.show()
            }
        } else {
            toast("Pilih file")
        }
    }


    private fun stringToRequestBody(desc: String?): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, desc.toString()
        )
    }

    override fun showError(e: String) {

        dialogAlert = alert(e, "Gangguan Jaringan, Data di tangguhkan ke Arsip usulan") {
            okButton {
                usulanViewModel.insert(usulan)
                finish()
                startActivity(intentFor<MainActivity>())
            }
        }.show()
    }

    override fun showLoading() {
        dialog = ProgressDialog(this)
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setMessage("Uploading...")
        dialog.isIndeterminate = false
        dialog.setCancelable(false)
        dialog.max = 100
        dialog.show()
    }

    override fun result(usulan: List<InserUsulanResponse>?) {
        if (usulan != null) {
            finish()
            toast("Data berhasil di usulkan")
            startActivity(intentFor<MainActivity>())
        }
    }

    override fun hideLoading() {
        dialog.dismiss()
    }


    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        startLocationUpdates()

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {

            Log.i("lokasi", mLocation!!.latitude.toString())
//            tv_latitude.text = mLocation!!.latitude.toString()
//            tv_longitude.text = mLocation!!.longitude.toString()

        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConnectionSuspended(i: Int) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_SHORT).show()
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(
            this,
            "Connection failed. Error: " + connectionResult.errorCode,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStart() {
        super.onStart()
        Log.i("lokasi1", mGoogleApiClient.toString())
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.disconnect()
        }
    }

    override fun onLocationChanged(location: Location) {
        Log.i("lokasi2", location.getLatitude().toString())
        val msg = "Updated Location: " +
                java.lang.Double.toString(location.getLatitude()) + "," +
                java.lang.Double.toString(location.getLongitude())
//        tv_latitude.text = mLocation!!.latitude.toString()
//        tv_longitude.text = mLocation!!.longitude.toString()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        // You can now create a LatLng Object for use with maps
        val latLng = LatLng(location.getLatitude(), location.getLongitude())
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton(
                "Location Settings",
                DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
            .setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    protected fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest, this
        )
        Log.d("request ", "--->>>>")
    }


    private fun isConnected(): Boolean {

        var status = false
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var netInfo: NetworkInfo? = cm.getNetworkInfo(0)
            when {
                netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED -> status = true
                else -> {
                    netInfo = cm.getNetworkInfo(1)
                    if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) status =
                        true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return status

    }

    private fun uploadFile() {
        if (selectedPrivateUri != null) {
            dialog = ProgressDialog(this)
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.setMessage("Uploading...")
            dialog.isIndeterminate = false
            dialog.setCancelable(false)
            dialog.max = 100
            dialog.show()

            val file = FileUtils.getFile(this, selectedPrivateUri)
            val requestFile = ProgressRequestBody(file!!, this)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)


//            val keterangan = etketerangan
//            val title = etjudul
//            val tanggal = tvtanggal
//            val tempat = ettempat

//            val ket = RequestBody.create(MediaType.parse("text/plain"), keterangan.text.toString())
//            val tit = RequestBody.create(MediaType.parse("text/plain"), title.text.toString())
//            val tan = RequestBody.create(MediaType.parse("text/plain"),tanggal.text.toString())
//            val tem = RequestBody.create(MediaType.parse("text/plain"), tempat.text.toString())

            Thread(Runnable {

                //                HomeAPI().postPost("Bearer " + SessionManager.getInstance(this).user.token, body,tit, ket, tan, tem)
//                    .enqueue(object : retrofit2.Callback<PostX>{
//                        override fun onFailure(call: Call<PostX>, t: Throwable) {
//                            Toast.makeText(this@CreatePostActivity, t.message, Toast.LENGTH_SHORT).show()
//                        }
//                        override fun onResponse(call: Call<PostX>, response: Response<PostX>) {
//                            Toast.makeText(this@CreatePostActivity, "Upload Successful", Toast.LENGTH_SHORT).show()
//                            dialog.cancel()
//                            finish()
//                            startActivity(Intent(this@CreatePostActivity, HomeActivity::class.java))
//                        }
//                    })
            }).start()
        } else {
            Toast.makeText(this, "Pilih File !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseSP() {
        val getContentIntent = FileUtils.createGetContentIntent()
        val intent = Intent.createChooser(getContentIntent, "Select a File")

        startActivityForResult(intent, PICK_SP_REQUEST)
    }

    private fun chooseDN() {
        val getContentIntent = FileUtils.createGetContentIntent()
        val intent = Intent.createChooser(getContentIntent, "Select a File")

        startActivityForResult(intent, PICK_DN_REQUEST)
    }

    private fun chooseSHP() {
        val getContentIntent = FileUtils.createGetContentIntent()
        val intent = Intent.createChooser(getContentIntent, "Select a File")

        startActivityForResult(intent, PICK_SHP_REQUEST)
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == PICK_SP_REQUEST) {
                    if (data != null) {
                        selectedPrivateUri = data.data
                        if (selectedPrivateUri != null && selectedPrivateUri!!.path!!.isNotEmpty()) {
                            selectedPrivateUri.let { returnUri ->
                                contentResolver.query(returnUri!!, null, null, null, null)
                            }?.use { cursor ->
                                cursor.moveToFirst()
                                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)



                                if (!spAdapter.getIsEdited()) {
                                    spPath.add(
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                    )
                                    sps.add(FileUploadMaterial())
                                    spAdapter.notifyDataSetChanged()
                                    //cursor.close()
                                } else {
                                    spPath[spAdapter.getPositionClicked()] =
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                }

                                sps[spAdapter.getPositionClicked()].textName?.text =
                                    cursor.getString(nameIndex)
                                sps[spAdapter.getPositionClicked()].edtBtnUpload?.visibility =
                                    View.VISIBLE
                                sps[spAdapter.getPositionClicked()].btnUpload?.visibility =
                                    View.GONE
                            }
                        }
                    }
                }
                if (requestCode == PICK_DN_REQUEST) {
                    if (data != null) {
                        selectedPrivateUri = data.data
                        if (selectedPrivateUri != null && selectedPrivateUri!!.path!!.isNotEmpty()) {
                            selectedPrivateUri.let { returnUri ->
                                contentResolver.query(returnUri!!, null, null, null, null)
                            }?.use { cursor ->
                                cursor.moveToFirst()
                                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)



                                if (!dnAdapter.getIsEdited()) {
                                    dnPath.add(
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                    )
                                    dns.add(FileUploadMaterial())
                                    dnAdapter.notifyDataSetChanged()
                                } else {
                                    dnPath[spAdapter.getPositionClicked()] =
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                }

                                dns[dnAdapter.getPositionClicked()].textName?.text =
                                    cursor.getString(nameIndex)
                                dns[dnAdapter.getPositionClicked()].edtBtnUpload?.visibility =
                                    View.VISIBLE
                                dns[dnAdapter.getPositionClicked()].btnUpload?.visibility =
                                    View.GONE
                                //cursor.close()
                            }
                        }
                    }
                }
                if (requestCode == PICK_SHP_REQUEST) {
                    if (data != null) {
                        selectedPrivateUri = data.data
                        if (selectedPrivateUri != null && selectedPrivateUri!!.path!!.isNotEmpty()) {
                            selectedPrivateUri.let { returnUri ->
                                contentResolver.query(returnUri!!, null, null, null, null)
                            }?.use { cursor ->
                                cursor.moveToFirst()
                                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)


                                if (!shpAdapter.getIsEdited()) {
                                    shpPath.add(
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                    )
                                    shps.add(FileUploadMaterial())
                                    shpAdapter.notifyDataSetChanged()
                                } else {
                                    shpPath[spAdapter.getPositionClicked()] =
                                        FileUtils.getPath(this, selectedPrivateUri!!)!!
                                }

                                shps[shpAdapter.getPositionClicked()].textName?.text =
                                    cursor.getString(nameIndex)
                                shps[shpAdapter.getPositionClicked()].edtBtnUpload?.visibility =
                                    View.VISIBLE
                                shps[shpAdapter.getPositionClicked()].btnUpload?.visibility =
                                    View.GONE
                                //cursor.close()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                dialogAlert = alert(
                    title = "Error",
                    message = "File yang dapat di Upload hanya bisa pada file yang berada pada perangkat ini"
                ) {
                    okButton {
                        dialogAlert.dismiss()
                    }
                }.show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(
                        this,
                        "Akses aplikasi terhadap penyimpana diizinkan",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(
                        this,
                        "Akses aplikasi terhadap penyimpana tidak diizinkan",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        dialog.progress = percentage
    }
}