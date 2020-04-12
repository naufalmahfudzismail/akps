package com.menlhk.akps.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.menlhk.akps.R
import com.menlhk.akps.model.ListUsulanResponse
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.im_back
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        im_back.setOnClickListener {
            onBackPressed()
        }

        val usulan = intent.getSerializableExtra("usulan") as ListUsulanResponse

        val id = Locale("in", "ID")
        val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
        val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"

        val simpleBasicDateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, id)
        val newFormat = SimpleDateFormat(TARGET_DATE_FORMAT, id)


        tv_alamat.text = usulan.alamat
        tv_email.text = usulan.email
        tv_jabatan.text = usulan.jabatan
        tv_kel_tani.text = usulan.hkmKelompoktani
        tv_nama_pengusung.text = usulan.namaPengusul
        tv_no_ktp.text = usulan.noKtp
        tv_lembaga.text = usulan.namaLembaga
        tv_no_surat.text = usulan.noSurat
        tv_no_telpon.text = usulan.noTelpon.toString()
        tv_no_tlp_pendamping.text = usulan.noKontakPendamping
        tv_pendamping.text = usulan.pendamping
        tv_tgl_surat.text =   newFormat.format(simpleBasicDateFormat.parse(usulan.tanggalSurat!!)!!)
    }
}
