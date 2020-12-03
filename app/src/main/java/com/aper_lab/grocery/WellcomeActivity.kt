package com.aper_lab.grocery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.aper_lab.grocery.databinding.ActivityWellcomeBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_wellcome.*

class WellcomeActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    lateinit var binding: ActivityWellcomeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_wellcome)

        g_sign_in.setOnClickListener {
            signIn();
        }

        if(FirebaseAuth.getInstance().currentUser != null){
            User.signIn(FirebaseAuth.getInstance().currentUser!!);

            binding.progressBar.visibility = View.INVISIBLE;

            var i: Intent = Intent(this,
                MainActivity::class.java);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(i);

        }
        else{
            binding.progressBar.visibility = View.GONE;
            binding.gSignIn.visibility = View.VISIBLE;
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
                    //Toast.makeText(this, "signed in! " + user.displayName, Toast.LENGTH_SHORT).show();
                    var i: Intent = Intent(this,
                        MainActivity::class.java);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(i);
                }



                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this,"Login Failed!",Toast.LENGTH_SHORT).show();
                setResult(1, intent);
                finish();
            }
        }
    }
}
