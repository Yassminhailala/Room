package com.example.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomwordssample.data.repository.WordRepository;
import com.example.roomwordssample.model.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private final WordRepository mRepository;
    private final LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }
public void update(Word word) {
    mRepository.update(word);
}
public void delete(Word word) {
    mRepository.delete(word);
}
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
} 