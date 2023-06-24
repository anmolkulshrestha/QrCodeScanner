package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments





import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.anmolsoftwaredeveloper12345.qrcodescanner.listeners.lumalistener
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.CustomBarcode
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.LuminosityAnalyzer
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.Scanner
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.common.util.concurrent.ListenableFuture
import com.anmolsoftwaredeveloper12345.qrcodescanner.R
import com.google.mlkit.vision.common.InputImage

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

const val REQUEST_IMAGE_OPEN = 1
class ScanFragment : Fragment() {

    lateinit var imageAnalysis: ImageAnalysis
    var isCameraPermissionGranted=false
    var isStoragePermissionGranted=false
    lateinit var permissionLauncher:ActivityResultLauncher<Array<String>>
    lateinit var camera_preview:PreviewView
    lateinit var switchcamera:FloatingActionButton
    lateinit var flashlight:FloatingActionButton
    lateinit var scangallery:FloatingActionButton
    lateinit var cameraProviderFuture:ListenableFuture<ProcessCameraProvider>
    lateinit var cameraProvider: ProcessCameraProvider
    lateinit var imageCapture: ImageCapture
    private var cameraControl: CameraControl? = null
    var fullPhotoUri: Uri?=null
    var thumbnail: Bitmap?=null

    private var cameraInfo: CameraInfo? = null
    var enableTorch:Boolean=false
   var cameraSelector: CameraSelector=CameraSelector.DEFAULT_BACK_CAMERA
    lateinit var camera:Camera


     private lateinit var cameraExecutor: ExecutorService
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

       var view= inflater.inflate(R.layout.fragment_scan, container, false)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            isStoragePermissionGranted=true
         }else{
            isStoragePermissionGranted=false
         }
        scangallery=view.findViewById(R.id.scangallery)
         flashlight=view.findViewById(R.id.flashlight)
         switchcamera=view.findViewById(R.id.switchcamera)

        camera_preview=view.findViewById(R.id.camera_preview)
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                permissions->
            isCameraPermissionGranted=permissions[Manifest.permission.CAMERA]?:isCameraPermissionGranted
            isStoragePermissionGranted=permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]?:isStoragePermissionGranted
            if(!isCameraPermissionGranted && !isStoragePermissionGranted){
                requestOrUpdatePermissions()
            }
        }

    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.VISIBLE

        
       cameraExecutor=Executors.newSingleThreadExecutor()

        requestOrUpdatePermissions()
        startCamera()

        switchcamera.setOnClickListener {

            if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA ){

                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                startCamera()}
            else {

                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                startCamera()}
        }
 flashlight.setOnClickListener {

    toggleTorch()

 }

        scangallery.setOnClickListener {
            selectImage()
        }

    }


    private fun toggleTorch() {
        if (cameraInfo?.torchState?.value == TorchState.ON) {
            cameraControl?.enableTorch(false)
        } else {
            cameraControl?.enableTorch(true)
        }
    }
    fun requestOrUpdatePermissions(){

        isCameraPermissionGranted= ContextCompat.checkSelfPermission(
        requireContext(),Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
         isStoragePermissionGranted=ContextCompat.checkSelfPermission(
             requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
        val permissionrequest:MutableList<String> = ArrayList()
        if(!isCameraPermissionGranted) { permissionrequest.add(Manifest.permission.CAMERA) }
        if(!isStoragePermissionGranted) { permissionrequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if(permissionrequest.isNotEmpty()){ permissionLauncher.launch(permissionrequest.toTypedArray()) }
    }

    fun scanBarcodeFromImage(uri: Uri){
var inputImage:InputImage=InputImage.fromFilePath(requireContext(), uri)




        Scanner().scan(object: lumalistener {
            override fun sendScannedBarcodeItem(obj: CustomBarcode) {
                findNavController().navigate(ScanFragmentDirections.actionScanFragmentToViewScannedBarcodeFragment(obj))
            }
        },inputImage)
    }
    private fun startCamera() {
         cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

     cameraProviderFuture.addListener({

             cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(camera_preview.surfaceProvider)
                }
          imageCapture = ImageCapture.Builder()

                .build()


           imageAnalysis = ImageAnalysis.Builder()
               .setTargetResolution(Size(1080, 1080))
               .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer(

                       object : lumalistener {
                           override fun  sendScannedBarcodeItem(obj: CustomBarcode) {

                               findNavController().navigate(ScanFragmentDirections.actionScanFragmentToViewScannedBarcodeFragment(obj))
                               clearAnalyzer()
                           }
                       },requireContext())
                    )
                }



            try { cameraProvider.unbindAll()

               camera=  cameraProvider.bindToLifecycle(
                    this, cameraSelector,preview,imageAnalysis,imageCapture)



            } catch(exc: Exception) {

            }
            cameraControl = camera.cameraControl
            cameraInfo = camera.cameraInfo
        }, ContextCompat.getMainExecutor(requireContext()))



    }
    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

            startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
           thumbnail = data!!.getParcelableExtra("data")
             fullPhotoUri = data!!.data

           scanBarcodeFromImage(fullPhotoUri!!)

        }
    }
  private fun clearAnalyzer(){
     imageAnalysis.clearAnalyzer()

 }


    }
