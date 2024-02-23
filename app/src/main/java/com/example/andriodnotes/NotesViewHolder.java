package com.example.andriodnotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView note;
    TextView dateTime;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.notes_title);
        note = itemView.findViewById(R.id.notes_view);
        dateTime = itemView.findViewById(R.id.notes_date);
    }
}
