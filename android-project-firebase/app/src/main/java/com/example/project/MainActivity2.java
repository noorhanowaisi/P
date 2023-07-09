package com.example.project;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    static int counter = 0 ;
    static TextView textView,textView1;
    static EditText name , password ;
    static ImageView imageView ;
    static Button login , signup ;
    static Animation top,bottom ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<users> user = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        extracted();
        readUsers();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getLogin(new users(name.getText().toString(),password.getText().toString(),"")))
                {
                    Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity2.this,"Your userName or password is wrong ",Toast.LENGTH_LONG).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });

    }

    private void extracted() {
        imageView = findViewById(R.id.imageView5);
        textView = findViewById(R.id.textView2);
        textView1 = findViewById(R.id.textView3);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView.setAnimation(top);
        textView.setAnimation(top);
        name.setAnimation(top);
        textView1.setAnimation(bottom);
        password.setAnimation(bottom);
        login.setAnimation(bottom);
        signup.setAnimation(bottom);
    }

    public static boolean getLogin(users c)
    {
        for(int i = 0 ; i < user.size();i++)
        {
            if(c.getName().equals(user.get(i).getName()) && c.getPassword().equals(user.get(i).getPassword()))
            {
                return true ;
            }
        }

        return false ;
    }
    public void readUsers(){
        db.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<users> types = documentSnapshots.toObjects(users.class);

                            // Add all to your list
                            user.addAll(types);
                            Toast.makeText(getApplicationContext(), "Success2", Toast.LENGTH_LONG).show();
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

    }



    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}