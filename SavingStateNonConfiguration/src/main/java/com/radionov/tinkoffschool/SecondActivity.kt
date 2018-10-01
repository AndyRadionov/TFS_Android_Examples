package com.radionov.tinkoffschool

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private var pictureId: Int = 0
    private var picture: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initState()
        initViews()
    }

    override fun onPause() {
        super.onPause()
        if(isFinishing) {
            picture = null
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return picture
    }

    private fun initState() {
        pictureId = intent.extras.getInt(EXTRA_PICTURE_ID)

        picture = if (lastNonConfigurationInstance != null) {
            lastCustomNonConfigurationInstance as Drawable
        } else {
            AppCompatResources.getDrawable(this, pictureId)
        }
    }

    private fun initViews() {
        iv_picture.setImageDrawable(picture)
    }

    companion object {
        const val EXTRA_PICTURE_ID = "picture_id"
    }
}
