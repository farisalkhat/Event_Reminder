package com.example.event_reminder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.event_reminder.EventReminder.EventReminder;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private ArrayList<EventReminder> mEventList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public static class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;

        public EventViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.testEvent1);
            mTextView2 = itemView.findViewById(R.id.testEvent2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });



        }
    }

    public EventAdapter(ArrayList<EventReminder> eventList){
        mEventList =  eventList;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        EventViewHolder evh = new EventViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventReminder event = mEventList.get(position);
        holder.mTextView1.setText(event.getEventName());
        holder.mTextView2.setText(event.getEventDescription());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}
