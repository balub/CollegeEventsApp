package com.example.balu.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder>{
    private Context context;
    private List<Events> events ;

    public RecycleViewAdaptor(Context context, List<Events> events) {
        this.context = context;
        this.events = events;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itsm, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context)
                .asBitmap()
                .load(events.get(position).getEvent_img())
                .into(holder.event_img);
        holder.event_title.setText(events.get(position).getEvent_title());
        holder.event_actions.setText(events.get(position).getEvent_actions());
        //holder.event_website.setText(events.get(position).getEvent_website());
        holder.event_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebActivity.class);
                intent.putExtra("url",events.get(position).getEvent_website());
                context.startActivity(intent);

            }
        });
        //holder.event_details.setText(events.get(position).getEvent_details());
        holder.event_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("img",events.get(position).getEvent_img());
                intent.putExtra("title",events.get(position).getEvent_title());
                intent.putExtra("details",events.get(position).getEvent_details());
                intent.putExtra("contact",events.get(position).getEvent_contact());
                context.startActivity(intent);
            }
        });
        //holder.event_contact.setText(events.get(position).getEvent_contact());
          holder.event_contact.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //message or call
                  LayoutInflater inflater = LayoutInflater.from(context);
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
                  AlertDialog.Builder alert = new AlertDialog.Builder(context);
                  alert.setTitle("Action");
                  alert.setView(alertLayout);
                  alert.setCancelable(true);
                  AlertDialog dialog = alert.create();
                  dialog.show();
              }
          });









    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView event_img;
        private TextView event_title,event_details,event_website,event_contact,event_actions;
        public ViewHolder(View itemView) {
            super(itemView);
            event_img = (ImageView) itemView.findViewById(R.id.event_img);
            event_title = (TextView)itemView.findViewById(R.id.title);
            event_details = (TextView)itemView.findViewById(R.id.event_detials);
            event_website = (TextView)itemView.findViewById(R.id.website);
            event_contact = (TextView)itemView.findViewById(R.id.contact);
            event_actions = (TextView)itemView.findViewById(R.id.event_actions);

        }
    }
}
