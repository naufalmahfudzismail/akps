package com.menlhk.akps.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menlhk.akps.R
import com.menlhk.akps.model.FileUploadMaterial
import com.menlhk.akps.util.ButtonPickFileInterface
import kotlinx.android.synthetic.main.item_arsip.view.*
import kotlinx.android.synthetic.main.upload_file_item.view.*

/**
 * Created By naufa on 25/01/2020
 */
class FileDynamicAdapter(val context: Context, val materials: MutableList<FileUploadMaterial> = mutableListOf(), val buttonView : ButtonPickFileInterface)
    : RecyclerView.Adapter<FileDynamicAdapter.ViewHolder>() {

    private var positionClicked = 0
    private var isEdited = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.upload_file_item,
                parent,
                false
            )
        )

    }

    fun getPositionClicked(): Int{
        return positionClicked
    }

    fun getIsEdited() : Boolean{
        return isEdited
    }

    override fun getItemCount(): Int {

        Log.e("jumlah", materials.size.toString())
        return materials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding(materials[position], context, position)
    }

    inner class ViewHolder(private val containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        fun binding(material : FileUploadMaterial, context : Context, position: Int){

            containerView.btn_pick_file.setOnClickListener {
                positionClicked = position
                buttonView.clickButton()

            }
            containerView.btn_edit_file.setOnClickListener {
                positionClicked = position
                isEdited = true
                buttonView.clickButton()

            }

            material.btnUpload = containerView.btn_pick_file
            material.edtBtnUpload = containerView.btn_edit_file
            material.textName = containerView.et_file_name
        }

    }

}