package com.example.mobiledictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mobiledictionary.English.EngViet;
import com.example.mobiledictionary.English.EnglishWord;
import com.example.mobiledictionary.EnglishController.WordController;
import com.example.mobiledictionary.Highlight.MyWords;
import com.example.mobiledictionary.Vietnamese.VietEng;
import com.example.mobiledictionary.WordHelper.WordHelper;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    protected int id;
    protected EditText search;
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public WordController wordController = new WordController();
    public Calendar rightNow = Calendar.getInstance();
    private WordHelper highlightWordHelper = new WordHelper(this,
            "TuDienSqlite", null, 1);
//    public List<EnglishWord> highLightWord = highlightWordHelper.getHighlightList(
//            "NoiDung", "VietEngDemo");

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
                        openVietAnh();
                        break;
                }
            }
        };

        findViewById(R.id.bSearch).setOnClickListener(handler);
        findViewById(R.id.bTuCuaBan).setOnClickListener(handler);
        findViewById(R.id.bVietAnh).setOnClickListener(handler);
        //noi dung va tieu de notification
        List<EnglishWord> mListHighlight =
                highlightWordHelper.getHighlightList("NoiDung","VietEngDemo");
        EnglishWord randomHighlightWord = wordController.getRandomWord(mListHighlight);

        //notification
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "lemubitA")
                .setSmallIcon(R.drawable.icon_star)
                .setContentTitle(randomHighlightWord.getWord())
                .setContentText(randomHighlightWord.getMeaning())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (wordController.compareTime(rightNow, 16, 26) == true)
            notificationManager.notify(100, builder.build());
//        int hour24 = rightNow.get(Calendar.HOUR_OF_DAY);
//        Log.d("curH", String.valueOf(hour24));
    }

    //chuyển sang cửa sổ tra từ Anh-Việt
    public void openEngViet() {
        search = (EditText) findViewById(R.id.edittext_main_search);
        String text = search.getText().toString();

        Intent intent = new Intent(this, EngViet.class);
        intent.putExtra(EXTRA_TEXT, text);
        startActivity(intent);
    }
    //chuyển sang cửa sổ highlight
    public void openHighlight() {
        Intent intent = new Intent(this, MyWords.class);
        startActivity(intent);
    }

    //chuyển sang cửa sổ tra việt anh
    public void openVietAnh() {
        Intent intent = new Intent(this, VietEng.class);
        startActivity(intent);
    }

    //tạo channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "studentChannel";
            String description = "Channel for student notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}