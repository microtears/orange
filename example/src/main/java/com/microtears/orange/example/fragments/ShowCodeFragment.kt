package com.microtears.orange.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.microtears.orange.example.R
import kotlinx.coroutines.Job

class ShowCodeFragment : Fragment() {

    companion object {
        fun newInstance() = ShowCodeFragment()
    }

    private lateinit var viewModel: ShowCodeViewModel

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowCodeViewModel::class.java)
        viewModel.setup(
            ShowCodeFragmentArgs.fromBundle(arguments!!).filename
        )
        viewModel.code.observe(this, Observer {
            job?.cancel()
            job = viewModel.launch { loadCode(it.await()) }
        })
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }

    private fun loadCode(code: String) {

    }

}
