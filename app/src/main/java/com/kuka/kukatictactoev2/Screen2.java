package com.kuka.kukatictactoev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Screen2 extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.kuka.kukatictactoev2.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.kuka.kukatictactoev2.EXTRA_TEXT2";

    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameScreen();
            }
        });
    }

    public void openGameScreen(){
        EditText edit_player1 = (EditText) findViewById(R.id.edit_player1);
        String text = edit_player1.getText().toString();

        EditText edit_player2 = (EditText) findViewById(R.id.edit_player2);
        String text2 = edit_player2.getText().toString();

        Intent intent = new Intent(this, GameScreen.class);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_TEXT2, text2);
        startActivity(intent);
    }
}
