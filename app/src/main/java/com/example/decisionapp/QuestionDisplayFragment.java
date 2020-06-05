package com.example.decisionapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * fragment
 * display the clicked question on the main fragment
 */
public class QuestionDisplayFragment extends Fragment {

    private Questions question;
    private TextView questionTitle;
    private TextView questionText;
    private LinearLayout questionOptionsList;
    private Button randomOptionButton;
    private int color;

    public QuestionDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //catch passed arguments to the fragment
        if(getArguments() != null){
            question = (Questions) getArguments().getSerializable("question_key");
            color = getArguments().getInt("color_key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_display, container, false);

        //find all the views
        questionText = (TextView) view.findViewById(R.id.question_display_text);
        questionTitle = (TextView) view.findViewById(R.id.question_display_title);
        questionOptionsList = (LinearLayout) view.findViewById(R.id.options_display_list);
        randomOptionButton = (Button) view.findViewById(R.id.random_button);
        questionOptionsList.setBackgroundColor(color);

        questionTitle.setText(question.getQuestionName());
        questionText.setText(question.getQuestionText());

        ArrayList<String> options = question.getOption();
        Log.i("array_list", "data: "+question.getOption().toString());
        LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //add all the options to the list
        for(int i = 0; i<options.size(); i++ ){
            TextView o = new TextView(getActivity());
            o.setText(options.get(i));
            o.setLayoutParams(lp_text);
            o.setPadding(50,10,0,10);
            o.setGravity(Gravity.CENTER);
            o.setTextSize(20);
            o.setTextColor(Color.WHITE);

            questionOptionsList.addView(o);
        }


        randomOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new fragment
                SelectRandomOptionFragment selectRandomOptionFragment = new SelectRandomOptionFragment();
                //pass data to it
                Bundle args = new Bundle();
                args.putSerializable("question_key", question);
                selectRandomOptionFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,
                        selectRandomOptionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return  view;
    }
}
