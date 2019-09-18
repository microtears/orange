package com.microtears.orange.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.microtears.orange.example.R

class RecyclerviewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerviewFragment()
    }

    private lateinit var viewModel: RecyclerviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
