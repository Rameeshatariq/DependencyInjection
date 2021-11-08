package com.example.dependencyinjectionapp.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dependencyinjectionapp.Interface.OnQuestionClickListener;
import com.example.dependencyinjectionapp.R;
import com.example.dependencyinjectionapp.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    private final OnQuestionClickListener mOnQuestionClickListener;
    private List<Question> mQuestionList = new ArrayList<>(0);


    // View holder
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txt_title);

        }
    }

    public QuestionsAdapter(OnQuestionClickListener onQuestionClickListener) {
        mOnQuestionClickListener = onQuestionClickListener;
    }

    // Binding Data
    public void bindData(List<Question> questions) {
        mQuestionList = new ArrayList<>(questions);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_question_list_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // we will solve this after Question Class configuration with retrofit
        holder.mTitle.setText(mQuestionList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnQuestionClickListener.onQuestionClicked(mQuestionList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

}
