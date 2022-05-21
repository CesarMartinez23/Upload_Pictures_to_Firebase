package com.example.uploadpicturesfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.uploadpicturesfirebase.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var imageUri: Uri

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }

        binding.btnUploadImage.setOnClickListener {
            uploadImage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageReference = FirebaseStorage.getInstance().getReference("Images/${nameImage()}")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.imgUpload.setImageURI(null)
            Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }.addOnFailureListener {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            Toast.makeText(this, "Failed to Upload", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.imgUpload.setImageURI(imageUri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun nameImage() : String {
        var fileName = ""
        if (binding.namePicture.text.toString().isNotEmpty()){
            fileName = binding.namePicture.text.toString()
            binding.namePicture.text.clear()
        }else{
            val formatter = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault())
            val now = Date()
            fileName = formatter.format(now)
        }
        return fileName
    }
}