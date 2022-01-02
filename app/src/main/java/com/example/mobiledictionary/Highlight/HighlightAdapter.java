package com.example.mobiledictionary.Highlight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiledictionary.EnglishWord;
import com.example.mobiledictionary.R;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder>{
    private List<EnglishWord> mListHighlight;

    public HighlightAdapter(List<EnglishWord> mListHighlight) {
        this.mListHighlight = mListHighlight;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        EnglishWord englishWord = mListHighlight.get(position);
        if(englishWord == null) {
            return;
        }
        holder.myword.setText(englishWord.getWord());
    }

    @Override
    public int getItemCount() {
        if(mListHighlight != null) {
            return mListHighlight.size();
        }
        return 0;
    }

    public class HighlightViewHolder extends RecyclerView.ViewHolder{
        TextView myword;
        LinearLayout foreground;
        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            myword = itemView.findViewById(R.id.textView_myword);
            foreground = itemView.findViewById(R.id.layout_foreground);
        }
    }
}