package com.ksayker.modiface.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.ksayker.modiface.R
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<S, E, L>, S : BaseState, E : BaseEvent, L : BaseLabel> :
    Fragment() {

    protected lateinit var binding: VB

    protected abstract val viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bind(layoutInflater, container)

        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect(::updateState)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.label.collect(::handleLabel)
        }

        initUi()
    }

    protected abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): VB

    protected open fun initUi() {
    }

    protected open fun updateState(state: S) {
    }

    protected open fun handleLabel(label: L) {
    }
}