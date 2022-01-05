package com.example.mobiledictionary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobiledictionary.EnglishController.EnglishWordHelper;


public class EngViet extends AppCompatActivity {

    private TextView mTextMessage;
    private Button mButton;
    private EditText mEditText;
    int flag = 0;
    private TextView word;
    private EditText search_1;
    private LinearLayout lineShowMeanWord;
    //String word = "";
    int idWord = 0;
    private CompoundButton mButtonHighlight;
    private Button mButtonOpen_Dialog_Note;
    private EnglishWordHelper englishWordHelper = new EnglishWordHelper(this,
            "TuDienSqlite", null, 1);
    private String key1;

    public EngViet () {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState2) {
        super.onCreate(savedInstanceState2);
        setContentView(R.layout.activity_main);
        mButtonOpen_Dialog_Note = findViewById(R.id.button_open_dialog_note);
        lineShowMeanWord =  findViewById(R.id.line_show_mean_word);
        word =  findViewById(R.id.word);
        mButtonHighlight = (ToggleButton) findViewById(R.id.buttonHighlight);
        mButtonOpen_Dialog_Note = findViewById(R.id.button_open_dialog_note);
        mButton = findViewById(R.id.bVietAnh_search);
        mButtonHighlight.setVisibility(View.GONE);
        mButtonOpen_Dialog_Note.setVisibility(View.GONE);
        search_1 = findViewById(R.id.edittext);
        mButton.setEnabled(true);
  //      englishWordHelper.CreateData("NoiDung");
 //       englishWordHelper.InsertData("NoiDung","hi","xin chao");
  //      englishWordHelper.InsertData("NoiDung","hello","xin chao 2");
  //      englishWordHelper.InsertData("NoiDung","cat","meo");
 //       englishWordHelper.InsertData("NoiDung","dog","cho");
        //tìm kiếm từ vựng

        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        search_1.setText(text);
        if (text != null)  idWord = search(englishWordHelper,"NoiDung");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idWord = search(englishWordHelper,"NoiDung");
            }
        });

        //highlight từ vựng
        mButtonHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();
                //checked = true thi highlight = 1
                if (checked){
                    englishWordHelper.HighlightWord(idWord,"NoiDung");
                    mButtonHighlight.setButtonDrawable(R.drawable.icon_star);
                }
                //checked = false thi highlight = 0
                else{
                    englishWordHelper.UnHighlightWord(idWord,"NoiDung");
                    mButtonHighlight.setButtonDrawable(R.drawable.icon_star_48);
                }
            }
        });

        //mở đoạn ghi chú
        mButtonOpen_Dialog_Note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Dialog_Note(idWord, Gravity.CENTER);
            }
        });
    }

    // hàm tìm kiếm từ vựng
    public int search(EnglishWordHelper englishWordHelper, String tableName) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        String key1 = search_1.getText().toString().trim();
        word.setText(key1);
        TextView meaning= findViewById(R.id.meaning);
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
            if (englishWordHelper.getHighlightWord(idWord,"NoiDung") == 0) {
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

    //mở đoạn dialog khi ấn vào nút
    private void open_Dialog_Note (int idWOrd, int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_note);
        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edittext_note = dialog.findViewById(R.id.edit_text_note);
        Button button_add_note = dialog.findViewById(R.id.button_add_note);
        Button button_cancer_dialog_note = dialog.findViewById(R.id.button_cancer_note);

        String note_1 = englishWordHelper.getNoteWord(idWord, "NoiDung");
        if (note_1 == "") {
        } else {
            edittext_note.setText(note_1, TextView.BufferType.EDITABLE);
        }

        button_cancer_dialog_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = edittext_note.getText().toString().trim();
                englishWordHelper.NoteWord(note, idWord, "NoiDung");
            }
        });
        dialog.show();
    }

}
