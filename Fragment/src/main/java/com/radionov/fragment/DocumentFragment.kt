package com.radionov.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_document.view.*

private const val ARG_DOC_NUMBER = "doc_number"

class DocumentFragment : Fragment() {

    private var docNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            docNumber = it.getInt(ARG_DOC_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_document, container, false)

        rootView.tv_document_text.text = getString(R.string.document_number, docNumber.toString())

        return rootView
    }

    companion object {
        fun newInstance(docNumber: Int) =
                DocumentFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_DOC_NUMBER, docNumber)
                    }
                }
    }
}
