package com.menlhk.akps.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.menlhk.akps.R
import com.menlhk.akps.database.usulan.UsulanDB
import kotlinx.android.synthetic.main.item_arsip.view.*
import kotlinx.android.synthetic.main.item_arsip.view.kelompok_tani
import kotlinx.android.synthetic.main.item_arsip.view.no_surat

class ArsipAdapter(
    private val list: List<UsulanDB>,
    val clickListener: (UsulanDB) -> Unit
) :
    RecyclerView.Adapter<ArsipAdapter.UsulanViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UsulanViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return UsulanViewHolder(inflater.inflate(R.layout.item_arsip, p0, false))
    }

    override fun onBindViewHolder(p0: UsulanViewHolder, p1: Int) {
        p0.bind(list[p1], clickListener)
    }

    override fun getItemCount(): Int = list.size


    inner class UsulanViewHolder(val containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        fun bind(usulan: UsulanDB, clickListener: (UsulanDB) -> Unit) {

            containerView.no_surat.text = usulan.noSurat
            containerView.kelompok_tani.text = usulan.hkmKelompoktani
            containerView.tanggal.text = usulan.tanggalSurat
            containerView.btn_upload.setOnClickListener { clickListener(usulan) }
        }
    }


}