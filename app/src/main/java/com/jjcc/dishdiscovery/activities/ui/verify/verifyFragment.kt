package com.jjcc.dishdiscovery.activities.ui.verify

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjcc.dishdiscovery.R

class verifyFragment : Fragment() {

    companion object {
        fun newInstance() = verifyFragment()
    }

    private lateinit var viewModel: VerifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}