package com.example.project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity4 extends AppCompatActivity {
    final Context context = this;
    ImageView imageView ;
    Spinner spinner ;
    Spinner spinner2;
    RecyclerView listView ;
    Button button ;
    Animation top , right ;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    EditText search;
    List<Home> result = new ArrayList<>();
    SearchView searchView ;
    public static FirebaseFirestore db;
    static List<Home> c2= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        extracted();
        extracted1();


    }

    private void extracted1() {
//        WriteBatch batch = db.batch();
  //      CollectionReference mCollectionRef = db.collection("Dormitories");






            String [] tokens = new String[2];
            List<String> c = new ArrayList<>();
            c.add("");
            c.add("Boys Dormitories");
            c.add("Girls Dormitories");
            c.add("Family Dormitories");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_list,c);
            spinner.setAdapter(arrayAdapter);
            String x;
            //getHomes finalHomes = homes;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String x = spinner.getSelectedItem().toString();
                    if(x.equals("")){
                        Context MainActivity = null;
                        listView.setLayoutManager(new LinearLayoutManager(MainActivity));
                        listView.setAdapter(null);

                        return;
                    }
                    getHome(x);
                    String[] str = new String[result.size()];
                    int[] imageids= new int[result.size()];
                    String[] nums = new String[result.size()];
                    double[] lats = new double[result.size()];
                    double[] lngs = new double[result.size()];
                    for (int j = 0; j < result.size(); j++) {
                        str[j] = result.get(j).toString();
                        if(j%3==0)
                            imageids[j]=R.drawable.image;
                        else if(j%3==1)
                            imageids[j]=R.drawable.image1;
                        else if (j%3==2)
                            imageids[j]=R.drawable.image2;
                        nums[j]=(result.get(j).getOwnerNumber());
                        lats[j]=result.get(j).getLat();
                        lngs[j]=result.get(j).getLng();
                    }
                    Context MainActivity = null;
                    listView.setLayoutManager(new LinearLayoutManager(MainActivity));
                    CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(str,imageids,nums,lats,lngs);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  spinner2=  view1.findViewById(R.id.location_spinner);

                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.filter, null);
                    spinner2 = promptsView.findViewById(R.id.location_spinner);
                    radioGroup=promptsView.findViewById(R.id.radioGroup);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);
                   // getHomes kareem = new getHomes();
                   // List<Home> c2 =  kareem.getAllHomes();
                    HashSet<String> locations = new HashSet<>();
                    for (int i = 0; i < c2.size(); i++) {
                        locations.add(c2.get(i).getLocation());
                    }
                    List<String> temp = new ArrayList<>();
                    temp.add("Any");
                    temp.addAll(locations);
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(MainActivity4.this,R.layout.spinner_list1,temp);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner2.setAdapter(arrayAdapter2);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            result=new ArrayList<>();
                                            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                                            if (selectedRadioButtonId != -1) {
                                                selectedRadioButton = promptsView.findViewById(selectedRadioButtonId);
                                                 tokens[0] = selectedRadioButton.getText().toString();
                                                }
                                            else
                                                tokens[0]="Any";
                                            tokens[1] = spinner2.getSelectedItem().toString();

                                            String x = spinner.getSelectedItem().toString();
                                            List<Home> c3 = getHomes(x);
                                            //List<Home> result = new ArrayList<>();
                                            if(!(tokens[1].equals("Any"))){
                                            for(int i=0;i<c3.size();i++){
                                                if(c3.get(i).getLocation().equals(tokens[1]))
                                                    result.add(c3.get(i));
                                            }}
                                            else
                                                result=c3;
                                            if(!tokens[0].equals("Any")){
                                                if(tokens[0].equals("Lowest to Highest"))
                                                    Collections.sort(result);
                                                else {
                                                    Collections.sort(result);
                                                    Collections.reverse(result);
                                                }
                                            }
                                            String[] str = new String[result.size()];
                                            int[] imageids= new int[result.size()];
                                            String[] nums = new String[result.size()];
                                            double[] lats = new double[c2.size()];
                                            double[] lngs = new double[c2.size()];
                                            for (int j = 0; j < result.size(); j++) {
                                                str[j] = result.get(j).toString();
                                                if(j%3==0)
                                                    imageids[j]=R.drawable.image;
                                                else if(j%3==1)
                                                    imageids[j]=R.drawable.image1;
                                                else if (j%3==2)
                                                    imageids[j]=R.drawable.image2;
                                                nums[j]=(result.get(j).getOwnerNumber());
                                                lats[j]=result.get(j).getLat();
                                                lngs[j]=result.get(j).getLng();
                                            }

                                            Context MainActivity = null;
                                            listView.setLayoutManager(new LinearLayoutManager(MainActivity));
                                            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(str,imageids,nums,lats,lngs);
                                            listView.setAdapter(adapter);


                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    filterList(s);
                    return true;
                }
            });

    }

    private void extracted() {
        listView = (RecyclerView) findViewById(R.id.listView);
        searchView=findViewById(R.id.searchview);
        searchView.clearFocus();
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.filter);
        spinner = findViewById(R.id.spinner);
        top = AnimationUtils.loadAnimation(this,R.anim.top);
        right = AnimationUtils.loadAnimation(this,R.anim.right);
        imageView.setAnimation(top);
        listView.setAnimation(right);
        spinner.setAnimation(right);
        button.setAnimation(right);
    }
    private void filterList(String text){
        List<Home> result1 = new ArrayList<>();
        for(int i = 0 ; i < result.size();i++)
        {
            if((result.get(i).getHomeName().toLowerCase()).contains(text.toLowerCase()))
            {
                result1.add(result.get(i));
            }
        }
        if(result1.isEmpty()){
            listView.setAdapter(null);
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
        }
        else{
           // for (int i = 0; i < result1.size(); i++) {
             //   System.out.println(result1.get(i).getHomeName());
            //}
            String[] str = new String[result1.size()];
            int[] imageids= new int[result1.size()];
            String[] nums = new String[result1.size()];
            double[] lats = new double[result1.size()];
            double[] lngs = new double[result1.size()];
            for (int j = 0; j < result1.size(); j++) {
                str[j] = result1.get(j).toString();
                if(j%3==0)
                    imageids[j]=R.drawable.image;
                else if(j%3==1)
                    imageids[j]=R.drawable.image1;
                else if (j%3==2)
                    imageids[j]=R.drawable.image2;
                nums[j]=(result1.get(j).getOwnerNumber());
                lats[j]=result1.get(j).getLat();
                lngs[j]=result1.get(j).getLng();
            }

            Context MainActivity = null;
            listView.setLayoutManager(new LinearLayoutManager(MainActivity));
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(str,imageids,nums,lats,lngs);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }

    public List<Home> getHomes(String gender)
    {
        List<Home> c3 = new ArrayList<>();
        for(int i = 0 ; i < c2.size();i++)
        {
            if(gender.equals(c2.get(i).getGender()))
                c3.add(c2.get(i));
        }
        return c3 ;
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

    public void getHome(String nameHome)
    {
        result = new ArrayList<>();
        for(int i = 0 ; i < c2.size();i++)
        {
            if(c2.get(i).getGender().equals(nameHome))
                result.add(c2.get(i));
        }
    }


}