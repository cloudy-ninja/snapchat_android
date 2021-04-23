package com.example.snapchat_android

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class CreateSanpActivity : AppCompatActivity() {
    var imageView: ImageView? = null;
    var imageName: String = UUID.randomUUID().toString() + ".jpg";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_sanp)

        imageView = findViewById(R.id.imageView);
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onChooseMedia(view: View) {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            );
        } else {
            getPhoto()
        }
    }

    private fun getPhoto() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("WHY", "It should work!");

        var selectedImage = data!!.data

        if(resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                imageView?.setImageBitmap(bitmap);
            }catch (e: Exception) {

            }

        }
    }

    fun onNext(view: View) {
        Log.d("Upload Image", "OK");
        imageView?.isDrawingCacheEnabled = true
        imageView?.buildDrawingCache()
        val bitmap = (imageView?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var mountainsRef = FirebaseStorage.getInstance().getReference().child("images").child(imageName);
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(this, "Upload file failed", Toast.LENGTH_LONG).show();
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            Toast.makeText(this, "Upload file failed", Toast.LENGTH_SHORT).show();
            Log.d("URL", taskSnapshot.toString());
        }
    }

}