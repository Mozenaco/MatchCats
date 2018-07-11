package cats.match.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.di.DaggerAppComponent;
import cats.match.android.data.di.PreferenceModule;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
import cats.match.android.matchcats.R;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.tvPlayerName)
    TextView tvPlayerName;
    @BindView(R.id.tvPlayerName2)
    TextView tvPlayerName2;
    @BindView(R.id.tvPlayerScore)
    TextView tvPlayerScore;
    @BindView(R.id.tvChronometer)
    TextView tvChronometer;

    @Inject
    PreferenceHelper mPreferenceHelper;
    DaggerAppComponent app;
    int numPlayers;

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
        ButterKnife.bind(this);
        app.builder().preferenceModule(new PreferenceModule(this)).build().inject(this);

        setupView();
        setupObservers();

        numPlayers = getIntent().getIntExtra(EXTRA_NUM_PLAYERS, 1);

        switch (numPlayers){
            case 1:
                startOnePlayerGame();
                break;

            case 2:
                startTwoPlayerGame();
                break;
        }
    }

    public void setupView() {

        tvPlayerName.setText(mPreferenceHelper.getNamePlayerOne());
        tvPlayerScore.setText("0");
        tvChronometer.setText("0:00");

    }

    public void setupObservers() {}

    public void startOnePlayerGame(){}

    public void startTwoPlayerGame(){}
}
