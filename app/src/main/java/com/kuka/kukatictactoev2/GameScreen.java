package com.kuka.kukatictactoev2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    //private TextView textViewPlayer1; static way of showing score
    //private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
        String text = intent.getStringExtra(Screen2.EXTRA_TEXT);
        String text2 = intent.getStringExtra(Screen2.EXTRA_TEXT2);

        TextView textView1 = (TextView) findViewById(R.id.text_view_p1);
        TextView textView2 = (TextView) findViewById(R.id.text_view_p2);

        textView1.setText(text + ": " + player1Points + " wins");
        textView2.setText(text2 + ": " + player2Points + " wins");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) { // check if there is already an X or O in the button
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
             waitRobotMove();
        } else {
            ((Button) v).setText("O");
            waitRobotMove();
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();

                player1Points++;
                updatePointsText();
                resetBoard();

            } else {
                player2Wins();

                player2Points++;
                updatePointsText();
                resetBoard();

            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void waitRobotMove(){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.wait_robot_move, (ViewGroup) findViewById(R.id.waitRobot_root));
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

    }

    private void player1Wins() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.player1_wins, (ViewGroup) findViewById(R.id.player1wins_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }

    private void player2Wins() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.player2_wins, (ViewGroup) findViewById(R.id.player2wins_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        //textViewPlayer1.setText("Player 1:" + player1Points);  static way of showing score
        //textViewPlayer2.setText("Player 2:" + player2Points);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Screen2.EXTRA_TEXT);
        String text2 = intent.getStringExtra(Screen2.EXTRA_TEXT2);

        TextView textView1 = (TextView) findViewById(R.id.text_view_p1);
        TextView textView2 = (TextView) findViewById(R.id.text_view_p2);

        textView1.setText(text + ": " + player1Points + " wins");
        textView2.setText(text2 + ": " + player2Points + " wins");


    }
    private void resetBoard(){
        for (int i = 0; i <3; i++){
            for (int j =0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1points", player1Points);
        outState.putInt("player2points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1points");
        player2Points = savedInstanceState.getInt("player2points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}
