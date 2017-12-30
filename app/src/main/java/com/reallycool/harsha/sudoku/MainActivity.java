package com.reallycool.harsha.sudoku;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Surface(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if(Surface.running==false){Surface.running=true;
        setContentView(new Surface(this));
        }

    }


}
