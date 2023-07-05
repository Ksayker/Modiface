package com.ksayker.modiface.presentation.fragment.editimage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksayker.modiface.App
import com.ksayker.modiface.R
import com.ksayker.modiface.core.utils.getBitmap
import com.ksayker.modiface.databinding.FragmentEditImageBinding
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.presentation.adapter.filter.FilterAdapter
import com.ksayker.modiface.presentation.base.BaseFragment
import com.ksayker.modiface.presentation.helper.PermissionHelper
import com.ksayker.modiface.presentation.utils.showToast
import com.ksayker.modiface.presentation.utils.stateArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditImageFragment :
    BaseFragment<FragmentEditImageBinding, EditImageViewModel, EditImageState, EditImageEvent, EditImageLabel>() {

    @Inject
    lateinit var factory: EditImageViewModelFactory.Factory

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val initialState: EditImageState by stateArgs(ARG_STATE)
    override val viewModel: EditImageViewModel by viewModels {
        factory.create(initialState)
    }

    private val adapter = FilterAdapter {
        viewModel.onEvent(EditImageEvent.OnFilterClicked(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        permissionHelper.registerForActivityResult(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEditImageBinding.inflate(layoutInflater, container, false)

    override fun initUi() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvListFilter.layoutManager = layoutManager
            rvListFilter.adapter = adapter

            ibtBack.setOnClickListener {
                findNavController().popBackStack()
            }
            ivSave.setOnClickListener {
                val selectedFilter = viewModel.currentState.filterItems.find { it.isSelected }
                val image = viewModel.currentState.image

                if (selectedFilter != null) {
                    permissionHelper.checkPermission(
                        fragment = this@EditImageFragment,
                        permissions = arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                        onGranted = { viewModel.onEvent(EditImageEvent.OnSaveClicked(selectedFilter.filter, image)) },
                        onShouldShowRational = { requireContext().showToast(R.string.editImage_needPermission) }
                    )
                }
            }
        }
    }

    override fun updateState(state: EditImageState) {
        adapter.items = state.filterItems

        displayImage(state)
    }

    override fun handleLabel(label: EditImageLabel) {
        when (label) {
            EditImageLabel.MessageImageSaved -> requireContext().showToast(R.string.editImage_imageSaved)
            EditImageLabel.MessageImageSaveError -> requireContext().showToast(R.string.editImage_imageSaveError)
            EditImageLabel.Back -> findNavController().popBackStack()
        }
    }

    private fun displayImage(state: EditImageState) {
        val bitmap = requireContext().getBitmap(state.image.uri)
        val filter = state.filterItems.find { it.isSelected }

        if (filter != null && bitmap != null) {
            viewLifecycleOwner.lifecycleScope.launch(dispatcherProvider.defaultDispatcher) {
                val newBitmap = filter.filter.apply(bitmap)
                withContext(Dispatchers.Main) {
                    binding.ivImage.setImageBitmap(newBitmap)
                }
            }
        } else {
            binding.ivImage.setImageBitmap(bitmap)
        }
    }

    companion object {

        private const val ARG_STATE = "ARG_STATE"

        fun makeArgs(state: EditImageState): Bundle {
            return Bundle().apply {
                putParcelable(ARG_STATE, state)
            }
        }
    }
}