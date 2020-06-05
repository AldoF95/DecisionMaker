package com.example.decisionapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Random;

/**
 * made adapter for displaying question titles as a list
 * it populates ListView element
 */
public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Questions> questionsArrayListList;
    private LayoutInflater inflater;

    /**
     * constructor gets the list of data
     * @param context
     * @param questionsArrayList
     */
    public QuestionAdapter(Context context, ArrayList<Questions> questionsArrayList){
        this.context = context;
        this.questionsArrayListList = questionsArrayList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return questionsArrayListList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionsArrayListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.question_box, null);

        Button questionButton = (Button) view.findViewById(R.id.question_button);
        //random background color every time that is loaded
        Random random = new Random();
        final int color = Color.argb(175,
                                        random.nextInt(256),
                                        random.nextInt(256),
                                        random.nextInt(256));
        questionButton.setBackgroundColor(color);
        questionButton.setText(questionsArrayListList.get(position).getQuestionName());

        /**
         * create an click event listener for every button
         * pass to the new fragment all the data of the object
         */
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionDisplayFragment displayQuestion = new QuestionDisplayFragment();
                Questions q = (Questions) questionsArrayListList.get(position);
                //pass data as bundle
                Bundle args = new Bundle();
                args.putSerializable("question_key", q);
                args.putInt("color_key", color);
                displayQuestion.setArguments(args);
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragmentContainer, displayQuestion).addToBackStack(null).commit();
            }
        });
        return view;
    }
}
