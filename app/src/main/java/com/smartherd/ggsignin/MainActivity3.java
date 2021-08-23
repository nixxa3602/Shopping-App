package com.smartherd.ggsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity3 extends AppCompatActivity {
private Button buttona;
private Button buttonb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttona=findViewById(R.id.button2);
        buttonb=findViewById(R.id.button3);
        buttona.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Shopping.class);
                startActivity(intent);
            }


        });

        buttonb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
            }


        });

    }
}