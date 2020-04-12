package com.menlhk.akps.view.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.menlhk.akps.R
import com.menlhk.akps.contract.UsulanListView
import com.menlhk.akps.database.RefferenceViewModel
import com.menlhk.akps.model.ListUsulanResponse
import com.menlhk.akps.presenter.UsulanPresenter
import com.menlhk.akps.util.SessionManager
import com.menlhk.akps.view.adapter.FragmentAdapter
import com.menlhk.akps.view.adapter.UsulanAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.usulan_fragment.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: FragmentAdapter
    private lateinit var dialogAlert: DialogInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = FragmentAdapter(supportFragmentManager)
        view_pager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(view_pager)

        btn_logout.setOnClickListener {

            dialogAlert = alert(
                "Apakah anda yakin ingin keluar dari akun ini?",
                "Log out"
            ) {
                okButton {
                    SessionManager.getInstance(this@MainActivity).clear()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                cancelButton {
                    dialogAlert.dismiss()
                }
            }.show()

        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> {
                        text_title.text = "Data Usulan"
                    }
                    1 -> {
                        text_title.text = "Arsip Usulan"
                    }
                }

            }

        })

    }

}
