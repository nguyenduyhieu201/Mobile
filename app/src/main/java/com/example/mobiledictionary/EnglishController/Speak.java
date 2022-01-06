package com.example.mobiledictionary.EnglishController;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.UUID;
import android.speech.tts.TextToSpeech;

public class Speak extends Activity {
    private TextToSpeech textToSpeech;
    private EditText editText;
    private Button button;
    private boolean ready;

    private Locale getUserSelectedLanguage(String languageSelected) {
        if (languageSelected == "VietNam") {
            Locale vietNam = new Locale("vi", "VN");
            return vietNam;
        }
        else {
            return Locale.ENGLISH;
        }
    }

    private void setTextToSpeechLanguage(String languageSelected) {
        Locale language = this.getUserSelectedLanguage(languageSelected);
        if (language == null) {
            this.ready = false;
            Toast.makeText(this, "Not language selected", Toast.LENGTH_SHORT).show();
            return;
        }
        int result = textToSpeech.setLanguage(language);
        if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA) {
            this.ready = false;
            Toast.makeText(this, "Missing language data", Toast.LENGTH_SHORT).show();
            return;
        } else if (result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
            this.ready = false;
            Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            return;
        } else {
            this.ready = true;
            Locale currentLanguage = textToSpeech.getVoice().getLocale();
            Toast.makeText(this, "Language " + currentLanguage, Toast.LENGTH_SHORT).show();
        }
    }

    private void speakOut() {
        if (!ready) {
            Toast.makeText(this, "Text to Speech not ready", Toast.LENGTH_LONG).show();
            return;
        }
        // Text to Speak
        String toSpeak = this.editText.getText().toString();
        Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show();
        // A random String (Unique ID).
        String utteranceId = UUID.randomUUID().toString();
        textToSpeech.speak(toSpeak, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
}
