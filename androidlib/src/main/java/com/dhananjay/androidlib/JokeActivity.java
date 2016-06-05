package com.dhananjay.androidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView tvJoke = (TextView) findViewById(R.id.tvjoke);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String joke = bundle.getString("joke");
            tvJoke.setText(joke);
            Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
        }
    }
}
