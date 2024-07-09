package com.menkashah.notesapp.fragment;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menkashah.notesapp.Note;
import com.menkashah.notesapp.NoteAdapter;
import com.menkashah.notesapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<Note> notes;
    private Button addNoteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = view.findViewById(R.id.notes_recycler_view);
        addNoteButton = view.findViewById(R.id.add_note_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notes = new ArrayList<>();
        adapter = new NoteAdapter(notes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                // Handle note click (update note)
                showNoteDialog(note);
            }

            @Override
            public void onNoteLongClick(Note note) {
                // Handle note long click (delete note)
                deleteNote(note);
            }
        });

        recyclerView.setAdapter(adapter);

        // Load notes from database
        loadNotes();

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new note
                showNoteDialog(null);
            }
        });

        return view;
    }

    private void loadNotes() {
        // Load notes from the database and update the adapter
        // Example: notes = database.getNotes();
        // For now, let's add some dummy data
        notes.add(new Note(1, "Sample Note 1", "This is the description of sample note 1."));
        notes.add(new Note(2, "Sample Note 2", "This is the description of sample note 2."));
        adapter.setNotes(notes);
    }

    private void showNoteDialog(@Nullable final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(note == null ? "Add Note" : "Update Note");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_update_note, null, false);
        final EditText titleEditText = view.findViewById(R.id.note_title);
        final EditText descriptionEditText = view.findViewById(R.id.note_description);
        Button saveButton = view.findViewById(R.id.save_button);

        if (note != null) {
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
        }

        builder.setView(view);

        final AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                    Toast.makeText(getContext(), "Please enter both title and description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (note == null) {
                    // Add new note
                    addNote(new Note(notes.size() + 1, title, description));
                } else {
                    // Update existing note
                    updateNote(note, title, description);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNote(Note note) {
        notes.add(note);
        adapter.notifyDataSetChanged();
        // Save note to the database
    }

    private void updateNote(Note note, String title, String description) {
        note.setTitle(title);
        note.setDescription(description);
        adapter.notifyDataSetChanged();
        // Update note in the database
    }

    private void deleteNote(Note note) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(note);
                        adapter.notifyDataSetChanged();
                        // Delete note from the database
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

