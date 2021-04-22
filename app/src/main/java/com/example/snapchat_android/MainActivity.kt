package com.example.snapchat_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    var emailEditText: EditText? = null;
    var pwdEditText: EditText? = null;

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = auth.currentUser;
        if (currentUser != null) {
            login()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        pwdEditText = findViewById(R.id.emailEditText);
    }

    fun onLogin(view: View) {
        auth.signInWithEmailAndPassword(
            emailEditText?.text.toString(),
            pwdEditText?.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Login Success!",
                        Toast.LENGTH_SHORT
                    ).show()
                    login();
                } else {
                    auth.createUserWithEmailAndPassword(
                        emailEditText?.text.toString(),
                        pwdEditText?.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    baseContext,
                                    "SignUp Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                login();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    baseContext,
                                    "SignUp Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
    }

    private fun login() {
        var intent: Intent = Intent(baseContext, SnapChatActivity::class.java);
        startActivity(intent);
    }

}