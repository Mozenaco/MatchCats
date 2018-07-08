package cats.match.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cats.match.android.matchcats.R;

public class GameActivity extends AppCompatActivity {

    int numberOfPlayers;


    static String EXTRA_NUM_PLAYERS = "numberOfPlayers";

    public static Intent buildIntentForGameActivity(Context context, int numberOfPlayers) {

        Intent game = new Intent(context, GameActivity.class);
        game.putExtra(EXTRA_NUM_PLAYERS, numberOfPlayers);
        return game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
    }
}
