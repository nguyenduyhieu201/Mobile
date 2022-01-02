package com.example.mobiledictionary.Highlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mobiledictionary.EnglishWord;
import com.example.mobiledictionary.R;

import java.util.ArrayList;
import java.util.List;

public class MyWords extends AppCompatActivity {
    private RecyclerView rcvHighlight;
    private HighlightAdapter adapter;
    private List<EnglishWord> mListHighlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_words);

        rcvHighlight = findViewById(R.id.rcv_highlight);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvHighlight.setLayoutManager(linearLayoutManager);

        //mListHighlight = getListHighlight();
        adapter = new HighlightAdapter(mListHighlight);
        rcvHighlight.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvHighlight.addItemDecoration(itemDecoration);
    }

    /*private List<EnglishWord> getListHighlight() {
        List<EnglishWord> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new EnglishWord("User name" + (i+ )))
        }
    }*/

}