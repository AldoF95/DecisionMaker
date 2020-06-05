package com.example.decisionapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * main fragment
 * display list of questions as buttons
 */
public class QuestionFragment extends Fragment {
   private ArrayList<Questions> questionsList = new ArrayList<>();
   private Button addButton;
   private ListView questionButtonDisplay;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        questionButtonDisplay = (ListView) view.findViewById(R.id.listItems);
        addButton = (Button) view.findViewById(R.id.add_button);

        questionsList.clear();
        //call method to display all questions
        displayQuestions(getContext());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewQuestion frag = new NewQuestion();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    /**
     * metode to display question as list
     * @param context
     */
    private void displayQuestions(Context context){
        //get all the question from file
        ArrayList <String> data = readQuestionsFromFile(context);

        for(int p=0; p<data.size(); p++){
            //for every element format the output string and add it to the questionlist object
            questionsList.add(filterDataFromFile(data.get(p)));
        }
        //create new adapter to display
        QuestionAdapter questionAdapter = new QuestionAdapter(context, questionsList);
        questionButtonDisplay.setAdapter(questionAdapter);


    }

    /**
     * get all the data from the file where the questions are stored
     * @param context
     * @return
     */
    private ArrayList<String> readQuestionsFromFile(Context context){
        ArrayList<String> output = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput("questions.txt");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = "";
                while ((s = bufferedReader.readLine())!=null){
                    output.add(s);
                }
                inputStream.close();
            }
        }catch (IOException e){
            Log.e("Error_file", "Can not read file "+e.toString());
        }
        return output;
    }

    /**
     * filter every row
     * remove '[]' and split on ','
     * @param dataLine
     * @return
     */
    private Questions filterDataFromFile(String dataLine){
        dataLine = dataLine.replace("[", "");
        dataLine = dataLine.replace("]", "");
        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(dataLine.split(",")));
        Questions q = new Questions(dataList.get(0), dataList.get(1));
        for(int i=2; i<dataList.size(); i++){
            q.addOption(dataList.get(i));
        }
        //return data as new Question object
        return q;
    }

}
