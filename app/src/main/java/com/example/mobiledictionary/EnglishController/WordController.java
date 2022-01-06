package com.example.mobiledictionary.EnglishController;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mobiledictionary.R;
import com.example.mobiledictionary.WordHelper.WordHelper;


public class WordController {
    public WordController() {

    }
    public int search(EditText search_1, TextView word, WordHelper englishWordHelper, String tableName,
                      TextView meaning, CompoundButton mButtonHighlight,
                      Button mButtonOpen_Dialog_Note) {
        String key1 = search_1.getText().toString().trim();
        word.setText(key1);
        Cursor meaningCursor = englishWordHelper.SearchWord(key1, tableName);

        int idWord = 0;
        String value = "";
        int count = meaningCursor.getCount();
        if (meaningCursor.getCount() > 0) {
            meaningCursor.moveToNext();
            value = meaningCursor.getString(2);
            idWord = meaningCursor.getInt(0);
        }
        else value = null;
        if (value == null) {
            meaning.setText("Khong co tu nao");
        }
        else {
            mButtonHighlight.setVisibility(View.VISIBLE);
            mButtonOpen_Dialog_Note.setVisibility(View.VISIBLE);
            mButtonHighlight.setChecked(false);
            if (englishWordHelper.getHighlightWord(idWord,tableName) == 0) {
                mButtonHighlight.setChecked(false);
                mButtonHighlight.setButtonDrawable(R.drawable.icon_star_48);
            }
            else {
                mButtonHighlight.setChecked(true);
                mButtonHighlight.setButtonDrawable(R.drawable.icon_star);
            }
            meaning.setText(value);
        }
        return idWord;
    }
    public void HighlightWord (CompoundButton mButtonHighlight, WordHelper englishWordHelper,
                               int idWord, String tableName) {
        mButtonHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();
                //checked = true thi highlight = 1
                if (checked){
                    englishWordHelper.HighlightWord(idWord,tableName);
                    mButtonHighlight.setButtonDrawable(R.drawable.icon_star);
                }
                //checked = false thi highlight = 0
                else{
                    englishWordHelper.UnHighlightWord(idWord,tableName);
                    mButtonHighlight.setButtonDrawable(R.drawable.icon_star_48);
                }
            }
        });
    }
}
