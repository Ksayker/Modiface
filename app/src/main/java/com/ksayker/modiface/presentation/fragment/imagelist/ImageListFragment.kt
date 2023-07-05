package com.ksayker.modiface.presentation.fragment.imagelist

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ksayker.modiface.App
import com.ksayker.modiface.R
import com.ksayker.modiface.databinding.FragmentImageListBinding
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.base.BaseFragment
import com.ksayker.modiface.presentation.fragment.editimage.EditImageFragment
import com.ksayker.modiface.presentation.fragment.editimage.EditImageState
import com.ksayker.modiface.presentation.fragment.editphoto.EditPhotoFragment
import com.ksayker.modiface.presentation.fragment.editphoto.EditPhotoState
import com.ksayker.modiface.presentation.fragment.imagelist.adapter.ImageAdapter
import com.ksayker.modiface.presentation.fragment.randomimage.RandomImageFragment
import com.ksayker.modiface.presentation.fragment.randomimage.RandomImageState
import com.ksayker.modiface.presentation.helper.PermissionHelper
import com.ksayker.modiface.presentation.utils.showToast
import com.ksayker.modiface.presentation.utils.stateArgs
import javax.inject.Inject


class ImageListFragment :
    BaseFragment<FragmentImageListBinding, ImageListViewModel, ImageListState, ImageListEvent, ImageListLabel>() {

    @Inject
    lateinit var factory: ImageListViewModelFactory.Factory

    private val initialState: ImageListState by stateArgs(ARG_STATE)
    override val viewModel: ImageListViewModel by viewModels {
        factory.create(initialState)
    }

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private val adapter = ImageAdapter(
        onImageClocked = {
            // TODO refactor navigation
            findNavController().navigate(
                R.id.action_imageListFragment_to_editImageFragment,
                EditImageFragment.makeArgs(EditImageState(Image(uri = it.uri)))
            )
        },
        onDeleteImageClocked = {
            viewModel.onEvent(ImageListEvent.DeleteImage(it))
        }
    )


    private val launchGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data?.data != null) {
                    data.data?.let {
                        viewModel.onEvent(ImageListEvent.AddNewImage(it.toString()))
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        permissionHelper.registerForActivityResult(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentImageListBinding.inflate(layoutInflater, container, false)

    override fun initUi() {
        with(binding) {
            val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            rvImageList.layoutManager = layoutManager
            rvImageList.adapter = adapter

            ivOpenGallery.setOnClickListener { openGallery() }
            ivOpenCamera.setOnClickListener {
                permissionHelper.checkPermission(
                    fragment = this@ImageListFragment,
                    permissions = arrayOf(Manifest.permission.CAMERA),
                    onGranted = {
                        findNavController().navigate(
                            R.id.action_imageListFragment_to_editPhotoFragment,
                            EditPhotoFragment.makeArgs(EditPhotoState())
                        )
                    },
                    onShouldShowRational = { requireContext().showToast(R.string.imageList_needPermission) }
                )
            }
            ivOpenDownload.setOnClickListener {
                findNavController().navigate(
                    R.id.action_imageListFragment_to_randomImageFragment,
                    RandomImageFragment.makeArgs(RandomImageState(shouldLoadNewImage = true))
                )
            }
        }
    }

    override fun updateState(state: ImageListState) {
        adapter.items = state.items
    }

    private fun openGallery() {
        launchGallery.launch(
            Intent().apply {
                type = IMAGE_TYPE
                action = Intent.ACTION_OPEN_DOCUMENT
            })
    }

    companion object {

        private const val ARG_STATE = "ARG_STATE"

        private const val SPAN_COUNT = 2
        private const val IMAGE_TYPE = "image/*"

        fun makeArgs(state: ImageListState): Bundle {
            return Bundle().apply {
                putParcelable(ARG_STATE, state)
            }
        }
    }
}