package com.example.project;





import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Intent;
//import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CaptionedImagesAdapter
        extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    private String[] captions;
    private int[] imageIds;
    private String[] nums;
    private double[] lats;
    private double[] lngs;

    public CaptionedImagesAdapter(String[] captions,int img[],String nums[],double[] lats,double[] lngs ){
        this.captions = captions;
        this.imageIds=img;
        this.nums=nums;
        this.lats=lats;
        this.lngs=lngs;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Button call = cardView.findViewById(R.id.call);
        Button whats = cardView.findViewById(R.id.whatsapp);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(dr);
        TextView txt = (TextView)cardView.findViewById(R.id.txtName);
        txt.setText(captions[position]);
        int ps= position;
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"+972"+nums[ps]));//change the number
                    view.getContext().startActivity(callIntent);

            }
        });
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://wa.me/+972"+nums[ps];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                view.getContext().startActivity(i);
            }
        });
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MapsMarkerActivity.latLng=new LatLng(lats[ps],lngs[ps]);
                Intent intent = new Intent(v.getContext(),MapsMarkerActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}