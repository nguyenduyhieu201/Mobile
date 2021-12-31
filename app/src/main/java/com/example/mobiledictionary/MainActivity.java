package com.example.mobiledictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
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


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    protected int id;
    public String mEnglishEditText;
    private Button mButtonSetEngVietLayout;
    protected EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mButtonSetEngVietLayout = findViewById(R.id.bSearch);
        mButtonSetEngVietLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.activity_main);
                EngViet engViet = new EngViet();
                engViet.onCreate(savedInstanceState);
            }
        });
    }
}