package com.smartherd.ggsignin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

private SignInButton signInButton;
public GoogleSignInClient mGoogleSignInClient;

public FirebaseAuth mAuth;
private Button btnSignOut;
private final static int RC_SIGN_IN=1;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton=findViewById(R.id.sign_in_button);
        mAuth= FirebaseAuth.getInstance();
        btnSignOut= findViewById(R.id.sign_out_button);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view)  {

            signIn();

        }

        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                btnSignOut.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "You Are Logged Out", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void signIn(){
        Intent signInIntent =mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task =GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }


    }

    protected void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

            firebaseAuthWithGoogle(acc);
        }
           catch(ApiException e){
               Toast.makeText(MainActivity.this, "Signed In Failed", Toast.LENGTH_SHORT).show();
               firebaseAuthWithGoogle(null);
           }
    }










    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            openActivity_splash_screen();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }
                    }
                });
    }

   private void updateUI(FirebaseUser fUser ) {
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account!= null){
            String personName= account.getDisplayName();
            String personGivenName= account.getGivenName();
            String personFamilyName= account.getFamilyName();
            String personEmail= "account.getEmail()";
            String personId= account.getId();
            Uri personPhoto= account.getPhotoUrl();

            Toast.makeText(MainActivity.this, "Welcome "+personName + "!", Toast.LENGTH_SHORT).show();

        }
   }


   public void openActivity_splash_screen(){
        Intent intent= new Intent(this, Splash_Screen.class);
        startActivity(intent);
   }

}