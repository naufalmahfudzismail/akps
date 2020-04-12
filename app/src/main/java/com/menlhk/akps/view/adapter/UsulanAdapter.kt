package com.menlhk.akps.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.menlhk.akps.R
import com.menlhk.akps.model.ListUsulanResponse
import com.menlhk.akps.model.Usulan
import kotlinx.android.synthetic.main.item_usulan.view.*

class UsulanAdapter(
    private val list: List<ListUsulanResponse>,
    val clickListener: (ListUsulanResponse) -> Unit
) :
    RecyclerView.Adapter<UsulanAdapter.UsulanViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UsulanViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return UsulanViewHolder(inflater.inflate(R.layout.item_usulan, p0, false))

    }

    override fun onBindViewHolder(p0: UsulanViewHolder, p1: Int) {
        val mUsulan = list[p1]
        p0.bind(mUsulan, clickListener)
    }

    override fun getItemCount(): Int = list.size


    inner class UsulanViewHolder(val containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        fun bind(usulan: ListUsulanResponse, clickListener: (ListUsulanResponse) -> Unit) {

            containerView.no_surat.text = usulan.noSurat
            containerView.kelompok_tani.text = usulan.hkmKelompoktani
            containerView.status.text = usulan.status

            containerView.setOnClickListener { clickListener(usulan) }
        }
    }


}