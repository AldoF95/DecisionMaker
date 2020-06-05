package com.example.decisionapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * fragment
 * random option selector with animation
 */
public class SelectRandomOptionFragment extends Fragment {

    private Questions question;
    private TextView randomOptionText;
    private Button startRandomButton;
    private ViewFlipper optionFlipper;

    //initial variables
    private int speedsIndex = 0;
    private int loops = 5;
    private int grow_rate = 0;
    private int max_value_index = 0;
    private ArrayList<Integer> speeds =  new ArrayList<>();

    private final int MIN_SPEED = 600;
    private final int MAX_SPEED = 200;

    public SelectRandomOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get object
            question = (Questions) getArguments().getSerializable("question_key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_select_random_option, container, false);
        optionFlipper = (ViewFlipper) view.findViewById(R.id.option_flipper);
        startRandomButton = (Button) view.findViewById(R.id.start_button);
        final ArrayList<String> options = question.getOption();

        Random rnd = new Random();
        //slow at beginning - fast in middle - slow at the end
        //get middle of list where max speed
        //(n_of_options*const_loops)/2 + rnd_number_between_0_and_n_of_options
        max_value_index = (int) ((options.size()*loops)/2)+rnd.nextInt(options.size()+1);
        //acceleration rate
        //delta(max_speed, min_speed)/steps to middle
        grow_rate = (int) 400/max_value_index;

        //generate speeds list
        generateSpeeds();


        //add to flipper all the options
        for (int i =0; i<options.size(); i++){
            randomOptionText = new TextView(getActivity());
            randomOptionText.setText(options.get(i));
            randomOptionText.setTextColor(Color.WHITE);
            randomOptionText.setTextSize(40);
            randomOptionText.setGravity(Gravity.CENTER_HORIZONTAL);

            optionFlipper.addView(randomOptionText);
        }

        /**
         * start flipping
         */
        startRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionFlipper.setAutoStart(true);
                optionFlipper.setFlipInterval(800);
                optionFlipper.startFlipping();
            }
        });

        /**
         * animation listener
         * when animation start change speed of transaction based on on the speed list
         */
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i("animation", "animation started");
                //if no more speeds, stop flipping
                //the chosen option
                if(speedsIndex>=speeds.size()){
                    optionFlipper.stopFlipping();
                }
                //else change speed
                else {
                    optionFlipper.setFlipInterval(speeds.get(speedsIndex));
                    speedsIndex++;
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        optionFlipper.getOutAnimation().setAnimationListener(animationListener);
        return view;
    }

    /**
     * generate speeds list
     */
    private void generateSpeeds(){
        //up to the middle of list
        for (int i = 0; i<= max_value_index; i++){
            int new_speed = (int) MIN_SPEED-(i*grow_rate);
            if(new_speed >= MAX_SPEED) {
                speeds.add(new_speed);
            }
            else {
                speeds.add(MAX_SPEED);
            }
        }
        //down from the middle to end of list
        for (int i = 0; i<=max_value_index+1; i++){
            int new_speed = (int) MAX_SPEED+(i*grow_rate);
            if(new_speed <= MIN_SPEED){
                speeds.add(new_speed);
            }
            else{
                speeds.add(MAX_SPEED);
            }
        }
    }
}
