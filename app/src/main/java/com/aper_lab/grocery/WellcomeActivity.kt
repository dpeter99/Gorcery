package com.aper_lab.grocery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_wellcome.*
import java.util.*

class WellcomeActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellcome)

        g_sign_in.setOnClickListener {
            signIn();
        }
    }

    fun signIn(){
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName("com.aper-lab.grocery", true, "24")
            .setHandleCodeInApp(true) // This must be set to true
            .setUrl("localhost") // This URL needs to be whitelisted
            .build();


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    )).build(),
            RC_SIGN_IN);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null) {
                    Toast.makeText(this, "signed in!" + user.displayName, Toast.LENGTH_SHORT)
                        .show();
                    setResult(2, intent);
                    finish();
                }



                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this,"failled!",Toast.LENGTH_SHORT).show();
                setResult(1, intent);
                finish();
            }
        }
    }
}
