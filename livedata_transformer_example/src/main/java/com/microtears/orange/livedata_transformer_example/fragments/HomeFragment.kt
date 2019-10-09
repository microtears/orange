package com.microtears.orange.livedata_transformer_example.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.microtears.orange.livedata.transformer.Observable
import com.microtears.orange.livedata.transformer.UncaughtExceptionHandler
import com.microtears.orange.livedata.transformer.observe

import com.microtears.orange.livedata_transformer_example.R
import com.microtears.orange.livedata_transformer_example.ResponseException

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        private const val TAG = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        Observable.errorHandler = object : UncaughtExceptionHandler {
//            override fun <T> uncaughtException(source: LiveData<T>, throwable: Throwable) {
//                Log.e(TAG, "uncaughtException", throwable)
//            }
//
//        }

        viewModel.userInfo.observe(this, Observer {
            if (it is ResponseException && it.errors.isNotEmpty()) {
                Log.e(TAG, "ResponseException ${it.errors.first().message()}", it)
                Toast.makeText(
                    context!!.applicationContext,
                    it.errors.first().message(),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Log.e(TAG, "ResponseException", it)

                Toast.makeText(context!!.applicationContext, it.message, Toast.LENGTH_LONG)
                    .show()
            }
        }, Observer {
            Log.d(TAG, "User $it")
        })
        viewModel.userRepositories.observe(this, null, Observer {
            Log.d(TAG, "Repositories $it")
        })
        viewModel.updateInfo("microtears")
    }

}
