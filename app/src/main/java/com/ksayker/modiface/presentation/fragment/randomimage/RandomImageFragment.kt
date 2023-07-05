package com.ksayker.modiface.presentation.fragment.randomimage

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ksayker.modiface.App
import com.ksayker.modiface.R
import com.ksayker.modiface.databinding.FragmentRandomImageBinding
import com.ksayker.modiface.presentation.base.BaseFragment
import com.ksayker.modiface.presentation.helper.PermissionHelper
import com.ksayker.modiface.presentation.utils.showToast
import com.ksayker.modiface.presentation.utils.stateArgs
import javax.inject.Inject


class RandomImageFragment :
    BaseFragment<FragmentRandomImageBinding, RandomImageViewModel, RandomImageState, RandomImageEvent, RandomImageLabel>() {

    @Inject
    lateinit var factory: RandomImageViewModelFactory.Factory

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private val initialState: RandomImageState by stateArgs(ARG_STATE)
    override val viewModel: RandomImageViewModel by viewModels {
        factory.create(initialState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        permissionHelper.registerForActivityResult(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRandomImageBinding.inflate(layoutInflater, container, false)

    override fun initUi() {
        with(binding) {
            ivSave.setOnClickListener {
                permissionHelper.checkPermission(
                    fragment = this@RandomImageFragment,
                    permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    onGranted = {
                        // TODO: refactor
                        viewModel.currentState.image?.let { image ->
                            viewModel.onEvent(RandomImageEvent.SaveUnsplashImage(image))
                        }
                    },
                    onShouldShowRational = { requireContext().showToast(R.string.randomImage_needPermission) }
                )
            }
            ibtBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun updateState(state: RandomImageState) {
        // TODO: Refactor
        state.image?.urls?.regular?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .into(binding.ivImage)
        }
    }

    override fun handleLabel(label: RandomImageLabel) {
        when (label) {
            RandomImageLabel.Back -> findNavController().popBackStack()
            RandomImageLabel.MessageImageSaveError -> requireContext().showToast(R.string.randomImage_imageSaveError)
            RandomImageLabel.MessageImageSaved -> requireContext().showToast(R.string.randomImage_imageSaved)
            RandomImageLabel.MessageImageLoadingError -> requireContext().showToast(R.string.randomImage_imageLoadingError)
        }
    }

    companion object {

        private const val ARG_STATE = "ARG_STATE"

        fun makeArgs(state: RandomImageState): Bundle {
            return Bundle().apply {
                putParcelable(ARG_STATE, state)
            }
        }
    }
}
