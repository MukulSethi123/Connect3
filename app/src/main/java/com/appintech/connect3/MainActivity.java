package com.appintech.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // 0:yellow, 1:red, 2:empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    GridLayout grid;
    String winner;
    boolean gameOver = false;
    boolean gameActive = true;
    int activePlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.bord_grid);
    }

    public void setCoin(View v) {
        if (gameActive) {
            ImageView coin = (ImageView) v;
            coin.setTranslationY(-1500);

            //see what position is tapped by the user
            int tappedCounter = grid.indexOfChild(coin);

            //for the position tapped change log which player has tapped in gameState
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 1) {
                //player 1 is red
                coin.setImageResource(R.drawable.red);
                activePlayer = 0;
            } else {
                //player 0 is yellow
                coin.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }

            //animate the coin falling into place
            coin.animate().translationYBy(1500).setDuration(1000);

            //loop through all winning positions
            //if the value of game state indexed at winning position is the same as the next one
            //and if the player is not 2 which is null
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {
                    // Somone has won!
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " has won!");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                }

            }

        }
    }

    public void playAgain (View v){
        //reset all values
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.bord_grid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        activePlayer = 0;
        gameActive = true;
    }
}