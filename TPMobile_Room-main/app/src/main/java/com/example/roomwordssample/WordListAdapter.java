package com.example.roomwordssample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomwordssample.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mWords = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Word word);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word current = mWords.get(position);
        holder.wordItemView.setText(current.getWord());
        holder.itemView.setTag(current); // Set the Word object as a tag
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void setWords(List<Word> words) {
        mWords.clear(); // Clear the old list
        mWords.addAll(words); // Add the new list
        notifyDataSetChanged(); // Notify the adapter
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick((Word) v.getTag());
                    }
                }
            });
        }
    }
}