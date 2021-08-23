package com.smartherd.ggsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import java.util.Timer;
import java.util.TimerTask;

import com.airbnb.lottie.LottieAnimationView;

public class Splash_Screen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        lottieAnimationView=findViewById(R.id.animation);
        lottieAnimationView.animate().translationY(-1600).setDuration(1000).setStartDelay(2000);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent= new Intent(Splash_Screen.this, MainActivity3.class );
                startActivity(intent);
                finish();

            }
        }, 3000);
    }


}