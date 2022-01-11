package com.example.mobiledictionary;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mobiledictionary.English.EngViet;
import com.example.mobiledictionary.English.EnglishWord;
import com.example.mobiledictionary.EnglishController.WordController;
import com.example.mobiledictionary.Highlight.MyWords;
import com.example.mobiledictionary.Notification.NotificationWord;
import com.example.mobiledictionary.Notification.Receiver;
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

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    public int hour24;
    public int minute;
    public Calendar calendar = Calendar.getInstance();
    long current = Calendar.getInstance().getTimeInMillis();
    public int hourPos;
    public int minPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sharedPref = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String languageSelected = sharedPref.getString("accent","");
        if (languageSelected.equals("English")) {
            editor.putBoolean("anhAnhSelected",true);
            editor.apply();
        }
        else {
            editor.putBoolean("anhAnhSelected",false);
            editor.apply();
        }

        hour24 = Integer.valueOf(sharedPref.getString("hourSet","00"));
        minute = Integer.valueOf(sharedPref.getString("minSet","00"));
        calendar.set(Calendar.HOUR_OF_DAY, hour24);
        calendar.set(Calendar.MINUTE,minute);
        long timeSet = calendar.getTimeInMillis();

        hourPos = sharedPref.getInt("hourPos", 0);
        minPos = sharedPref.getInt("minPos", 0);
        editor.putInt("hourSetting", hourPos);
        editor.putInt("minSetting", minPos);
        editor.apply();

//        highlightWordHelper.CreateData("VietEngDemo");
//        highlightWordHelper.InsertData("VietEngDemo","xin chao","hi");
//        highlightWordHelper.InsertData("VietEngDemo","xin chao 3","hello");
//        highlightWordHelper.InsertData("VietEngDemo","dog","cho");
//        highlightWordHelper.InsertData("VietEngDemo","cat","meo");
//        highlightWordHelper.InsertData("VietEngDemo","tot","goodbye");
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

                    case R.id.bCaiDat:
                        openSetting();
                        break;
                }
            }
        };

        findViewById(R.id.bSearch).setOnClickListener(handler);
        findViewById(R.id.bTuCuaBan).setOnClickListener(handler);
        findViewById(R.id.bVietAnh).setOnClickListener(handler);
        findViewById(R.id.bCaiDat).setOnClickListener(handler);
        //noi dung va tieu de notification
        List<EnglishWord> mListHighlight =
                highlightWordHelper.getHighlightList("NoiDung","VietEngDemo");
        EnglishWord randomHighlightWord = wordController.getRandomWord(mListHighlight);

        //notification
        createNotificationChannel();
        Intent intent = new Intent(MainActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //cài đặt thời gian nhắc nhở
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        2, pendingIntent);

        if (timeSet > current) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeSet,
                    24 * 60 * 60 * 1000, pendingIntent);
        }
        else {
            hour24 = hour24 + 1;
            calendar.set(Calendar.HOUR_OF_DAY, hour24);
            timeSet = calendar.getTimeInMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeSet,
                    24 * 60 * 60 * 1000, pendingIntent);
        }
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

    public void openSetting() {
        Intent intent = new Intent(this, Setting.class);
        Log.d("opensetting", "open setting");
        startActivity(intent);
    }

    //tạo channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "studentChannel";
            String description = "Channel for student notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Log.d("Create successfull", "created");
    }

}


//    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
////        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
////                SystemClock.elapsedRealtime() +
////                        10 * 1000, pendingIntent);
//        Intent intent = new Intent(this, NotificationWord.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        startActivity(intent);
//
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
//                "lemubitA")
//                .setSmallIcon(R.drawable.icon_star)
//                .setContentTitle(randomHighlightWord.getWord())
//                .setContentText(randomHighlightWord.getMeaning())
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setVisibility(VISIBILITY_PUBLIC);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);