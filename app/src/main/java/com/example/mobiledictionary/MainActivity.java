package com.example.mobiledictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mobiledictionary.EnglishController.EnglishWordHelper;
import com.example.mobiledictionary.Highlight.MyWords;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    protected int id;
    protected EditText search;
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.bSearch:
                        openEngViet();
                        break;

                    case R.id.bTuCuaBan:
                        openHighlight();
                        break;

                    case R.id.bVietAnh:
                        openEngViet();
                        break;
                }
            }
        };

        findViewById(R.id.bSearch).setOnClickListener(handler);
        findViewById(R.id.bTuCuaBan).setOnClickListener(handler);
        findViewById(R.id.bVietAnh).setOnClickListener(handler);
    }


    public void openEngViet() {
        search = (EditText) findViewById(R.id.edittext_main_search);
        String text = search.getText().toString();

        Intent intent = new Intent(this, EngViet.class);
        intent.putExtra(EXTRA_TEXT, text);
        startActivity(intent);
    }

    public void openHighlight() {

        Intent intent = new Intent(this, MyWords.class);
        startActivity(intent);
    }
}