package com.example.balu.collegeapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private TextView details_title,details_detail;
    private ImageView details_img;
    private FloatingActionButton details_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String title = getIntent().getStringExtra("title");
        String details = getIntent().getStringExtra("details");
        String imgLink = getIntent().getStringExtra("img");
        String contact = getIntent().getStringExtra("contact");
        details_img = (ImageView) findViewById(R.id.details_img);
        details_title = (TextView) findViewById(R.id.details_title);
        details_detail = (TextView) findViewById(R.id.details_detail);
        details_contact = (FloatingActionButton)findViewById(R.id.details_contact);

        Glide.with(this)
                .asBitmap()
                .load(imgLink)
                .into(details_img);
        details_title.setText(title);
        details_detail.setText(details);
        details_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.contact_alert, null);
                final TextView message = alertLayout.findViewById(R.id.message);
                final TextView call = alertLayout.findViewById(R.id.call);
                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//open Whatsapp
                    }
                });
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //open call activity
                    }
                });
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailsActivity.this);
                alert.setTitle("Action");
                alert.setView(alertLayout);
                alert.setCancelable(true);
                AlertDialog dialog = alert.create();
                dialog.show();




            }
        });







    }
}
