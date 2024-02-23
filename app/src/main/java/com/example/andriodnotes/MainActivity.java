package com.example.andriodnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final ArrayList<Notes> myNotes = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher;

    //private EditText notesTitle;
    //private EditText notesMessage;
    private RecyclerView recyclerView; // Layout's recyclerview
    private NotesListAdapter noteadapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //notesMessage = findViewById(R.id.notes_view);
        //notesTitle = findViewById(R.id.notes_title);

        recyclerView = findViewById(R.id.recycler);
        // Data to recyclerview adapter
        noteadapter = new NotesListAdapter(myNotes, this);
        recyclerView.setAdapter(noteadapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::getNotesfromActivity);

        getSupportActionBar().setTitle("Android Notes " + myNotes.size());



    }

    public void getNotesfromActivity(ActivityResult activityResult){
        if (activityResult.getResultCode() == RESULT_OK){
            Intent data = activityResult.getData();
            if (data == null)
                return;
            if (data.hasExtra("new_note_object")){
                Notes n = (Notes) data.getSerializableExtra("new_note_object");
                myNotes.add(0,n);
                noteadapter.notifyItemInserted(0);
                getSupportActionBar().setTitle("Android Notes " + myNotes.size());
                //linearLayoutManager.scrollToPosition(myNotes.size()-1);

            } else if (data.hasExtra("update_note") && data.hasExtra("update_pos")){
                Notes editn = (Notes) data.getSerializableExtra("update_note");
                int position = data.getIntExtra("update_pos",0);
                Notes toUpdate = myNotes.get(position);
                toUpdate.setNotes(editn.getMessage());
                toUpdate.setTitle(editn.getTitle());
                noteadapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    protected void onResume() {
        //myNotes = loadFile();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.info) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.add) {
            Intent intent2 = new Intent(this, NewNote.class);
            activityResultLauncher.launch(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int pos = recyclerView.getChildLayoutPosition(view);
        Notes no = myNotes.get(pos);

        Intent intent2 = new Intent(this, NewNote.class);
        intent2.putExtra("edit_note",no);
        intent2.putExtra("edit_pos",pos);
        activityResultLauncher.launch(intent2);
    }

    @Override
    public boolean onLongClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Notes no = myNotes.get(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myNotes.remove(pos);
                noteadapter.notifyItemRemoved(pos);
            }
        });
        builder.setNegativeButton("NO", (dialogInterface, i) -> {});

        builder.setTitle("Delet Note '" + no.getTitle() + "'?");

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

/*
    private Notes loadFile() {

        Log.d(TAG, "loadFile: Loading JSON File");
        try {
            InputStream is = getApplicationContext().openFileInput("Notes.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            String title = jsonObject.getString("title");
            String mess = jsonObject.getString("message");

            return new Notes(title, mess);

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "no file found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

 */
}
