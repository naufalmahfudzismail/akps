package com.menlhk.akps.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.menlhk.akps.R
import com.menlhk.akps.contract.UsulanListView
import com.menlhk.akps.database.RefferenceViewModel
import com.menlhk.akps.model.ListUsulanResponse
import com.menlhk.akps.presenter.UsulanPresenter
import com.menlhk.akps.util.SessionManager
import com.menlhk.akps.view.activity.CreateUsulanActivity
import com.menlhk.akps.view.activity.DetailActivity
import com.menlhk.akps.view.adapter.UsulanAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.usulan_fragment.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.longToast

/**
 * Created By naufa on 14/09/2019
 */

class UsulanFragment : Fragment(), UsulanListView, SwipeRefreshLayout.OnRefreshListener {


    private lateinit var usulanPresenter: UsulanPresenter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var usulanAdapter: UsulanAdapter
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.usulan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        usulanPresenter = UsulanPresenter(context!!)
        progressDialog = ProgressDialog(context!!, R.style.Theme_MaterialComponents_Light_Dialog)
        refreshData.setOnRefreshListener(this)

        userId = SessionManager.getInstance(context!!).getIdUser()
        usulanPresenter.getUsulan(userId.toString(), this)

        fab_post.setOnClickListener {
            val intent = Intent(context, CreateUsulanActivity::class.java)
            startActivity(intent)
        }

    }

    override fun showLoading() {
        if (!refreshData.isRefreshing) {
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Loading...")
            progressDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun result(usulan: List<ListUsulanResponse>?) {


        if (usulan != null) {
            jumlah.text = "Total data : ${usulan.size}"

            usulanAdapter = UsulanAdapter(usulan) {

                startActivity(
                    intentFor<DetailActivity>(
                        "usulan" to it
                    )
                )
            }
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.itemAnimator = DefaultItemAnimator()
            recycler_view.adapter = usulanAdapter
        }

    }

    override fun showError(error: String) {
        longToast(error)
    }


    override fun hideLoading() {
        if (refreshData.isRefreshing) refreshData.isRefreshing = false
        else progressDialog.dismiss()
    }

    override fun onRefresh() {
        usulanPresenter.getUsulan(userId.toString(), this)
    }

}