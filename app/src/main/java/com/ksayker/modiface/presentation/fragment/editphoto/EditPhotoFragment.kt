package com.ksayker.modiface.presentation.fragment.editphoto

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksayker.modiface.App
import com.ksayker.modiface.R
import com.ksayker.modiface.databinding.FragmentEditPhotoBinding
import com.ksayker.modiface.presentation.adapter.filter.FilterAdapter
import com.ksayker.modiface.presentation.base.BaseFragment
import com.ksayker.modiface.presentation.helper.BitmapSaveHelper
import com.ksayker.modiface.presentation.helper.ImageConverterHelper
import com.ksayker.modiface.presentation.helper.PermissionHelper
import com.ksayker.modiface.presentation.utils.showToast
import com.ksayker.modiface.presentation.utils.stateArgs
import java.util.concurrent.Executors
import javax.inject.Inject

// TODO refactor camera
class EditPhotoFragment :
    BaseFragment<FragmentEditPhotoBinding, EditPhotoViewModel, EditPhotoState, EditPhotoEvent, EditPhotoLabel>() {

    @Inject
    lateinit var factory: EditPhotoViewModelFactory.Factory

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var converter: ImageConverterHelper

    @Inject
    lateinit var bitmapSaveHelper: BitmapSaveHelper

    private val initialState: EditPhotoState by stateArgs(ARG_STATE)
    override val viewModel: EditPhotoViewModel by viewModels {
        factory.create(initialState)
    }

    private var imageCapture: ImageCapture? = null

    private val adapter = FilterAdapter {
        viewModel.onEvent(EditPhotoEvent.OnFilterClicked(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        permissionHelper.registerForActivityResult(this)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEditPhotoBinding.inflate(layoutInflater, container, false)

    @androidx.camera.core.ExperimentalGetImage
    override fun initUi() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvListFilter.layoutManager = layoutManager
            rvListFilter.adapter = adapter

            ibtBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btCapture.setOnClickListener {
                permissionHelper.checkPermission(
                    fragment = this@EditPhotoFragment,
                    permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    onGranted = { takePicture() },
                    onShouldShowRational = { requireContext().showToast(R.string.editPhoto_needPermission) }
                )
            }

            initCamera(binding)
        }
    }

    override fun updateState(state: EditPhotoState) {
        adapter.items = state.filterItems
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun initCamera(binding: FragmentEditPhotoBinding) {
        val executor = Executors.newSingleThreadExecutor()
        val imageView = binding.ivPreview

        val analyzerUseCase = ImageAnalysis.Builder()
            .setResolutionSelector(
                ResolutionSelector.Builder()
                    .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)
                    .build()
            )
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val imageAnalyzer = ImageAnalysis.Analyzer { image ->
            val filteredBitmap = image.applySelectedFilter()
            imageView.post {
                imageView.setImageBitmap(filteredBitmap)
            }
            image.close()
        }
        analyzerUseCase.setAnalyzer(executor, imageAnalyzer)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                imageCapture = ImageCapture.Builder().build()
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        viewLifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture,
                        analyzerUseCase
                    )
                } catch (e: Exception) {
                    // no op
                }
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun takePicture() {
        imageCapture?.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    image.applySelectedFilter(enableAutoRotation = false)?.let { bitmap ->
                        bitmapSaveHelper.saveBitmap(bitmap) {
                            requireContext().showToast(R.string.editPhoto_captured)
                        }
                    }
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    requireContext().showToast(exception.message ?: getString(R.string.error))
                }
            }
        )
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun ImageProxy.applySelectedFilter(enableAutoRotation: Boolean = true): Bitmap? {
        var bitmap: Bitmap? = null
        val degrees = if (enableAutoRotation) {
            //TODO deprecated
            when (requireActivity().windowManager.defaultDisplay.rotation) {
                Surface.ROTATION_0 -> 90f
                Surface.ROTATION_90 -> 0f
                Surface.ROTATION_180 -> 270f
                else -> 180f
            }
        } else {
            0f
        }

        fun rotate(bitmap: Bitmap): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degrees)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        fun allocateBitmapIfNecessary(width: Int, height: Int): Bitmap {
            if (bitmap == null || bitmap!!.width != width || bitmap!!.height != height) {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            return bitmap!!
        }

        val resultBitmap = image?.let { inputImage ->
            val selectedFilter = viewModel.currentState.filterItems.find { it.isSelected }
            val allocatedBitmap = allocateBitmapIfNecessary(width, height)
            converter.yuvOrJpegToRgb(inputImage, allocatedBitmap)

            if (selectedFilter != null) {
                rotate(selectedFilter.filter.apply(allocatedBitmap))
            } else {
                rotate(allocatedBitmap)
            }
        }

        bitmap?.recycle()
        bitmap = null

        return resultBitmap
    }

    companion object {

        private const val ARG_STATE = "ARG_STATE"

        fun makeArgs(state: EditPhotoState): Bundle {
            return Bundle().apply {
                putParcelable(ARG_STATE, state)
            }
        }
    }
}