package com.microtears.orange.example.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.microtears.orange.example.R
import com.microtears.orange.livedata.transformer.observe
import kotlinx.android.synthetic.main.live_data_fragment.*

class LiveDataFragment : Fragment() {

    companion object {
        fun newInstance() = LiveDataFragment()
    }

    private lateinit var viewModel: LiveDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_data_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        viewModel.realWord.observe(this, Observer {
            it.printStackTrace()
        }, Observer {
            helloText.text = it
        })
        viewModel.count.observe(this, Observer {

        })
        viewModel.realWordList.observe(this, Observer {

        })
        viewModel.count.observe(this, Observer {

        })
        viewModel.realWordSet.observe(this, Observer {

        })
        viewModel.newWord.observe(this, Observer {

        })
        viewModel.error.observe(this, Observer {
            Log.e("ErrorTest", "test error", it)
        }, Observer { })
        randomButton.setOnClickListener { viewModel.randomWord() }

    }

}
