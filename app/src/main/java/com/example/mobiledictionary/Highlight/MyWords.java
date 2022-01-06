package com.example.mobiledictionary.Highlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

//import com.example.mobiledictionary.EnglishController.EnglishWordHelper;
import com.example.mobiledictionary.WordHelper.WordHelper;
import com.example.mobiledictionary.English.EnglishWord;
import com.example.mobiledictionary.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MyWords extends AppCompatActivity implements ItemTouchHelperListener {
    private RecyclerView rcvHighlight;
    private HighlightAdapter adapter;
    private List<EnglishWord> mListHighlight;
    private LinearLayout rootView;
    private WordHelper englishWordHelper = new WordHelper(this,
            "TuDienSqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_words);
        rootView = findViewById(R.id.root_view);
        rcvHighlight = findViewById(R.id.rcv_highlight);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvHighlight.setLayoutManager(linearLayoutManager);

        mListHighlight = getListHighlight();
        adapter = new HighlightAdapter(mListHighlight);
        rcvHighlight.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvHighlight.addItemDecoration(itemDecoration);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecycelViewItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rcvHighlight);
    }

    private List<EnglishWord> getListHighlight() {
        return englishWordHelper.getHighlightList("NoiDung");
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof HighlightAdapter.HighlightViewHolder) {
            final String Highlightdelete = mListHighlight.get(viewHolder.getAdapterPosition()).getWord();
            final EnglishWord delete = mListHighlight.get(viewHolder.getAdapterPosition());
            int index = viewHolder.getAdapterPosition();

            englishWordHelper.UnHighlightWord(delete.getId(),"NoiDung");
            adapter.removeItem(englishWordHelper,index);

            Snackbar snackbar = Snackbar.make(rootView, "remove", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.undoItem(delete, index);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}