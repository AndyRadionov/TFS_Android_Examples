package com.radionov.tinkoffschool

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.button_start_activity.view.*

class FirstActivity : AppCompatActivity() {

    private var onBtnClickListener = View.OnClickListener {
        val intent = Intent(this@FirstActivity, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_PICTURE_ID, R.drawable.funny_image)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        initViews()
    }

    private fun initViews() {
        val view = LayoutInflater.from(this).inflate(R.layout.activity_first, null)
        setContentView(view)

        view.btn_start_activity.setOnClickListener(onBtnClickListener)
    }
}
