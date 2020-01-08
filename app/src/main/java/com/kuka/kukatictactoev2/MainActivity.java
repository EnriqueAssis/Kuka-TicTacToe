package com.kuka.kukatictactoev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button twoPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPlayers = (Button) findViewById(R.id.twoPlayers);
        twoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openActivity2();
            }
        });
    }

    public void openActivity2(){
        Intent intent = new Intent(this, Screen2.class);
        startActivity(intent);
    }
}
