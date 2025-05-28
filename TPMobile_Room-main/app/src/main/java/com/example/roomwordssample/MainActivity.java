package com.example.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.roomwordssample.databinding.ActivityMainBinding;
import com.example.roomwordssample.model.Word;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WordListAdapter mAdapter = new WordListAdapter();
        binding.contentMain.recyclerview.setAdapter(mAdapter);
        binding.contentMain.recyclerview.setHasFixedSize(true);

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, words -> {
            if (words != null) {
                mAdapter.setWords(words); // Update the adapter with the new list
            }
        });

        mAdapter.setOnItemClickListener(word -> {
            Log.d("MainActivity", "Item clicked: " + word.getWord());
            new AlertDialog.Builder(this)
                    .setTitle("Choose Action")
                    .setMessage("Do you want to delete or edit this word?")
                    .setPositiveButton("Edit", (dialog, which) -> {
                        Log.d("MainActivity", "Edit clicked for: " + word.getWord());
                        showEditDialog(word);
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        Log.d("MainActivity", "Delete clicked for: " + word.getWord());
                        mWordViewModel.delete(word);
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        });

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        Button refreshButton = findViewById(R.id.btn_refresh);
        refreshButton.setOnClickListener(v -> {
            // Trigger a refresh of the data
            mWordViewModel.getAllWords().observe(this, words -> {
                if (words != null) {
                    mAdapter.setWords(words); // Update the adapter with the new list
                }
            });
            Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show();
        });
    }

    private void showEditDialog(Word word) {
        final EditText input = new EditText(this);
        input.setText(word.getWord());
        new AlertDialog.Builder(this)
                .setTitle("Edit Word")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String updatedWord = input.getText().toString();
                    if (!updatedWord.isEmpty()) {
                        // Update the word object and call the ViewModel's update method
                        word.setWord(updatedWord);
                        mWordViewModel.update(word);
                    } else {
                        Toast.makeText(this, "Word cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
}