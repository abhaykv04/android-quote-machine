package abhaykv04.quotemachine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Narayanan on 11-07-2017.
 */

public class SignIn extends AppCompatActivity {
    private EditText e1;
    private EditText e2;
    private Button b;
    private Button bu;
    int logid=0;

    private DatabaseReference dr;

    private  FirebaseAuth.AuthStateListener mauthlist;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        b = (Button) findViewById(R.id.bi);
        bu = (Button) findViewById(R.id.bu);

        dr = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mauthlist = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Toast.makeText(getApplicationContext(),"Signing In...",Toast.LENGTH_LONG).show();logid=1;
                    Intent intent = new Intent(SignIn.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {signIn();}
        });
        bu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {signup();}
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mauthlist);
    }


    private void signIn()
    { String email = e1.getText().toString();
        String pass= e2.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass))
        {
            Toast.makeText(SignIn.this,"Couldnt leave pass or id blank",Toast.LENGTH_LONG).show();
        }
        else{


            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignIn.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignIn.this,"Could'nt Login",Toast.LENGTH_LONG).show();

                    }

                }
            });}
    }
    private void signup()
    {

        String email = e1.getText().toString();
        String pass = e2.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success, update UI with the signed-in user's information
                    Intent intent = new Intent(SignIn.this,MainActivity.class);
                    startActivity(intent);
                    finish();



                    FirebaseUser user = mAuth.getCurrentUser();}
                else{
                    // If sign in fails, display a message to the user.

                    Toast.makeText(SignIn.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
