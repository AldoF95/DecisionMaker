package com.example.decisionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionFragment frag = new QuestionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, frag).commit();
    }
}
