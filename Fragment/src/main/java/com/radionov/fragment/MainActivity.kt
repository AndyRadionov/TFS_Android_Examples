package com.radionov.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    private fun setupListeners() {
        btn_add_document.setOnClickListener {
            val currentDocId = supportFragmentManager.backStackEntryCount + 1
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.document_container, DocumentFragment.newInstance(currentDocId))
                    .addToBackStack(currentDocId.toString())
                    .commit()
        }

        btn_remove_document.setOnClickListener {
            supportFragmentManager
                    .popBackStack()
        }
    }

    private fun setupListenersWoBackStack() {
        btn_add_document.setOnClickListener {
            val currentDocId = supportFragmentManager.fragments.size + 1
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.document_container, DocumentFragment.newInstance(currentDocId))
                    .commit()
        }

        btn_remove_document.setOnClickListener {
            val lastIndex = supportFragmentManager.fragments.lastIndex
            if (lastIndex < 0) {
                return@setOnClickListener
            }

            val frag = supportFragmentManager.fragments[lastIndex]
            supportFragmentManager
                    .beginTransaction()
                    .remove(frag)
                    .commit()
        }
    }
}
