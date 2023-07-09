package com.example.project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity3 extends AppCompatActivity {

    TextView textView , textView1,textView2 ,textView3;
    EditText name , email , password , password1 ;
    Button signup , back ;
    ImageView imageView ;
    Animation top , bottom , right , left ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<users> user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        extracted();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString().equals(password1.getText().toString()))
                {
                    if(!getLogin(new users(name.getText().toString(),password.getText().toString(),email.getText().toString())))
                    {
                        Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                        startActivity(intent);

                           db.collection("Users").add(new users(name.getText().toString(),password.getText().toString(),email.getText().toString()));


                    }
                    else
                    {
                        Toast.makeText(MainActivity3.this,"Your userName already used ",Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity3.this,"Password does not match",Toast.LENGTH_LONG).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(intent);
        }});
    }
    public static boolean getLogin(users c)
    {
        for(int i = 0 ; i < user.size();i++)
        {
            if(c.getName().equals(user.get(i).getName())||c.getEmail().equals(user.get(i).getEmail()))
            {
                return true ;
            }
        }

        return false ;
    }

    private void extracted() {
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password1 = findViewById(R.id.password1);

        signup = findViewById(R.id.signup);
        back = findViewById(R.id.back);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);
        right = AnimationUtils.loadAnimation(this,R.anim.right);
        left = AnimationUtils.loadAnimation(this,R.anim.left);

        imageView = findViewById(R.id.imageView3);

        extracted1();
        back.setAnimation(bottom);

    }

    private void extracted1() {
        imageView.setAnimation(top);

        textView.setAnimation(left);
        textView1.setAnimation(left);
        textView2.setAnimation(left);
        textView3.setAnimation(left);

        name.setAnimation(right);
        email.setAnimation(right);
        password.setAnimation(right);
        password1.setAnimation(right);
        signup.setAnimation(bottom);
    }


}