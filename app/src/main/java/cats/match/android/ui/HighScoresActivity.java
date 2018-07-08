package cats.match.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cats.match.android.matchcats.R;

public class HighScoresActivity extends AppCompatActivity {

    public static Intent buildIntentForHighScoresActivity(Context context) {

        Intent game = new Intent(context, HighScoresActivity.class);
        return game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
    }
}
