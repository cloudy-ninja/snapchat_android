package com.example.snapchat_android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SnapChatActivity : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap_chat)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflate = menuInflater;
        inflate.inflate(R.menu.snaps, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.createSnap) {
            var intent = Intent(this, CreateSanpActivity::class.java);
            startActivity(intent);
        } else if (item?.itemId == R.id.logOut) {
            mAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item)
    }
}