package com.example.roomwordssample.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.roomwordssample.data.db.WordDao;
import com.example.roomwordssample.data.db.WordRoomDatabase;
import com.example.roomwordssample.model.Word;

import java.util.List;

public class WordRepository {

    private final WordDao mWordDao;
    private final LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    public void update(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            Log.d("WordRepository", "this is update: " + word.getWord());
            mWordDao.update(word);
        });
    }

    
    public void delete(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            Log.d("WordRepository", "this is delete: " + word.getWord());
            mWordDao.delete(word.getWord());
        });
    }

    // Room exécute toutes les requêtes sur un thread distinct.
    // Les données LiveData observées avertiront l'observateur lorsque les données auront changé.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // Vous devez appeler cela sur un thread non-UI ou votre application lancera une exception.
    // Room garantit que vous n'effectuez aucune opération longue sur le thread principal, bloquant l'interface utilisateur.
    public void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> mWordDao.insert(word));
    }

    public void deleteAll() {
        WordRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mWordDao.deleteAll();
            }
        });
    }
}