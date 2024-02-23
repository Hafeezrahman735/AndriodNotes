package com.example.andriodnotes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class NewNote extends AppCompatActivity {

    private EditText newNoteTitle;
    private EditText newNoteMessage;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        newNoteTitle = findViewById(R.id.new_title);
        newNoteMessage = findViewById(R.id.new_message);

        Intent intent = getIntent();
        if (intent.hasExtra("edit_note")) {
            Notes note = (Notes) intent.getSerializableExtra("edit_note");
            newNoteMessage.setText(note.getMessage());
            newNoteTitle.setText(note.getTitle());
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String textTitle = newNoteTitle.getText().toString();
                String textMessage = newNoteMessage.getText().toString();
                Notes newNote = new Notes(textTitle, textMessage);

                String key = "new_note_object";

                Intent intent = getIntent();
                if (intent.hasExtra("edit_note")) {
                    key = "update_note";

                }
                Intent data = new Intent();
                data.putExtra(key, newNote);
                if (intent.hasExtra("edit_pos")) {
                    int pos = intent.getIntExtra("edit_pos", 0);
                    data.putExtra("update_pos",pos);
                }
                setResult(RESULT_OK,data);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NewNote.super.onBackPressed();
            }
        });

        builder.setTitle("Your note is not saved!");
        builder.setMessage("Save note '" + newNoteTitle.getText().toString() + "' ?");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.edit_save) {

            String textTitle = newNoteTitle.getText().toString();

            if (textTitle.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {});

                builder.setTitle("Your note will not be saved without a title!");

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {

                String textMessage = newNoteMessage.getText().toString();
                Notes newNote = new Notes(textTitle, textMessage);

                String key = "new_note_object";

                Intent intent = getIntent();
                if (intent.hasExtra("edit_note")) {
                    key = "update_note";

                }
                Intent data = new Intent();
                data.putExtra(key, newNote);
                if (intent.hasExtra("edit_pos")) {
                    int pos = intent.getIntExtra("edit_pos", 0);
                    data.putExtra("update_pos", pos);
                }
                setResult(RESULT_OK, data);
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}