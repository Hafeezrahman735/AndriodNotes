package com.example.andriodnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{

    private final List<Notes> notesList;
    private final MainActivity mainAct;

    NotesListAdapter(List<Notes> noteList, MainActivity ma) {
        this.notesList = noteList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Notes onenotes = notesList.get(position);

        holder.note.setText(onenotes.getMessage());
        holder.title.setText(onenotes.getTitle());
        holder.dateTime.setText(new Date().toString());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
