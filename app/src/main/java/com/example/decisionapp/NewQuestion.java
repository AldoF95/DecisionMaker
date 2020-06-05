package com.example.decisionapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * fragment
 * creates a new question
 */

public class NewQuestion extends Fragment {

    private Button addNewOptionButton;
    private Button saveQuestionButton;
    private EditText questionTitle;
    private EditText questionText;
    private EditText optionText;
    private LinearLayout optionsList;

    private Questions new_question = new Questions();

    public NewQuestion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_question, container, false);

        //find all graphical elements
        addNewOptionButton = (Button) view.findViewById(R.id.add_new_option_button);
        saveQuestionButton = (Button) view.findViewById(R.id.save_question_button);
        questionText = (EditText) view.findViewById(R.id.question_text);
        questionTitle = (EditText) view.findViewById(R.id.question_title);
        optionText = (EditText) view.findViewById(R.id.option_text);
        optionsList = (LinearLayout) view.findViewById(R.id.options_list);


        /**
         * on every click a new option is added
         * the new option is displayed in a dynamic list
         */
        addNewOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the inserted text
                final String option_text = optionText.getText().toString();
                //add it to the Question object list
                new_question.addOption(option_text);
                //scale of the screen for scaling the programmatically added text
                float scale = getContext().getResources().getDisplayMetrics().density;

                //layout parameters
                LinearLayout.LayoutParams lp_button = new LinearLayout.LayoutParams(((int)(30*scale+0.5f)), ((int)(30*scale+0.5f)));
                LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp_text.weight=1;

                //create new views
                ImageButton remove_option_button = new ImageButton(getActivity());
                TextView added_option_text = new TextView(getActivity());
                LinearLayout new_option_box = new LinearLayout(getActivity());

                //change properties
                remove_option_button.setLayoutParams(lp_button);
                remove_option_button.setImageResource(R.drawable.delete_trash); //icon
                remove_option_button.setScaleType(ImageView.ScaleType.FIT_XY);
                remove_option_button.setPadding(10,10,10,10);
                remove_option_button.setBackgroundResource(R.drawable.remove_button);
                remove_option_button.setTag(option_text);

                added_option_text.setText(option_text);
                added_option_text.setLayoutParams(lp_text);
                added_option_text.setTextColor(Color.WHITE);
                added_option_text.setTextSize(18);

                new_option_box.setOrientation(LinearLayout.HORIZONTAL);
                new_option_box.setPadding(0, 10, 0, 10);
                new_option_box.addView(added_option_text);
                new_option_box.addView(remove_option_button);

                //add new views to the placeholder element
                optionsList.addView(new_option_box);
                optionsList.invalidate();
                optionText.setText("");

            }
        });

        /**
         * save button to sotore the Question object data in a text file
         */
        saveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save
                saveQuestion(getActivity());
                //return to the main fragment
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }

    /**
     * created the string of data to be saved in a txt file
     * @param context
     */
    private void saveQuestion(Context context){
        new_question.setQuestionName(questionTitle.getText().toString());
        new_question.setQuestionText(questionText.getText().toString());

        String data = new_question.getQuestionName()+","+new_question.getQuestionText()+","+new_question.getOption().toString()+"\n";
        saveDataToFile(data, context);
    }

    /**
     * save data to a file
     * @param data
     * @param context
     */
    private void saveDataToFile(String data, Context context){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("questions.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e){
            Log.e("Exception", "File save failed "+e.toString());
        }
    }

}
