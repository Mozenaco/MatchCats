package cats.match.android.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.utils.Utils;
import cats.match.android.data.di.DaggerAppComponent;
import cats.match.android.data.di.PreferenceModule;
import cats.match.android.data.entities.ActionAfterFlip;
import cats.match.android.data.entities.Game;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
import cats.match.android.matchcats.R;
import cats.match.android.viewmodel.GameViewModel;
import tyrantgit.explosionfield.ExplosionField;

/**
 * GameActivity class is the view where the game happens. It is defined here views behaviors and
 * callbacks to the ViewModel
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class GameActivity extends AppCompatActivity {

    @BindView(R.id.tvPlayerName) TextView tvPlayerName;
    @BindView(R.id.tvPlayerName2) TextView tvPlayerName2;
    @BindView(R.id.tvPlayerScore) TextView tvPlayerScore;
    @BindView(R.id.tvPlayerScore2) TextView tvPlayerScore2;
    @BindView(R.id.tvChronometer) TextView tvChronometer;
    @BindView(R.id.endGameLayout) ConstraintLayout endGameLayout;
    @BindView(R.id.btMainMenu) Button btMainMenu;
    @BindView(R.id.tvEndGameScore) TextView tvEndGameScore;
    @BindView(R.id.tvEndGameScore2) TextView tvEndGameScore2;
    @BindView(R.id.tvMessage) TextView tvMessage;
    @BindView(R.id.tvGameOver) TextView tvGameOver;
    @BindView(R.id.btNextLevelButton) Button btNextLevelButton;


    @Inject
    PreferenceHelper mPreferenceHelper;
    DaggerAppComponent app;
    int numPlayers;

    private ExplosionField mExplosionField;

    GameViewModel gameViewModel;

    static String EXTRA_NUM_PLAYERS = "numberOfPlayers";
    static String EXTRA_IS_TO_RESET = "isToReset";

    public static Intent buildIntentForGameActivity(Context context, int numberOfPlayers, boolean isToReset) {

        Intent game = new Intent(context, GameActivity.class);
        game.putExtra(EXTRA_NUM_PLAYERS, numberOfPlayers);
        game.putExtra(EXTRA_IS_TO_RESET, isToReset);
        return game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        ButterKnife.bind(this);

        //[D]ependency Inversion Principle - High-level modules should not depend on low-level modules.
        //Both should depend on abstractions.
        app.builder().preferenceModule(new PreferenceModule(this)).build().inject(this);

        gameViewModel = new GameViewModel(mPreferenceHelper);
        mExplosionField = ExplosionField.attach2Window(this);
        numPlayers = getIntent().getIntExtra(EXTRA_NUM_PLAYERS, 1);

        mPreferenceHelper.setNamePlayerOne(Game.getInstance().currentPlayerOneName);
        mPreferenceHelper.setNamePlayerTwo(Game.getInstance().currentPlayerTwoName);

        if(getIntent().getBooleanExtra(EXTRA_IS_TO_RESET, true))
            Game.getInstance().resetGame();

        Game.getInstance().resetCurrentImagesMatched();

        setupViews();
        setupObservers();

        startGame();
    }

    public void setupViews() {

        tvPlayerName.setText(mPreferenceHelper.getNamePlayerOne());
        tvPlayerScore.setText("0 pts");
        tvChronometer.setText("0:00");

        btNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameActivity.buildIntentForGameActivity(getApplicationContext(),
                        Game.getInstance().currentNumOfPlayers, false);

                finish();
                startActivity(intent);

            }
        });

        if (Game.getInstance().currentNumOfPlayers > 1) {

            tvPlayerName2.setText(mPreferenceHelper.getNamePlayerTwo());
            tvPlayerScore2.setText("0 pts");
        }

        if (numPlayers == 1) {
            hidePlayer2Stuff();
        }

        new CountDownTimer(Game.getInstance().getFullTimeValue(), 1000) {

            public void onTick(long millisUntilFinished) {
                tvChronometer.setText(millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
                gameViewModel.forceEndGame();
            }

        }.start();
    }

    public void setupObservers() {

        gameViewModel.getObservableActionAfterFlip().observe(this, new Observer<ActionAfterFlip>() {
            @Override
            public void onChanged(final ActionAfterFlip actionAfterFlip) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!actionAfterFlip.getMatch()) {
                            ((EasyFlipView) actionAfterFlip.getFirstCard()).flipTheView();
                            ((EasyFlipView) actionAfterFlip.getSecondCard()).flipTheView();

                        } else {
                            try {
                                mExplosionField.explode(actionAfterFlip.getFirstCard());
                                actionAfterFlip.getFirstCard().setOnClickListener(null);

                                mExplosionField.explode(actionAfterFlip.getSecondCard());
                                actionAfterFlip.getSecondCard().setOnClickListener(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            gameViewModel.addScoreBecauseMatch();
                        }

                        if (Game.getInstance().currentNumOfPlayers > 1)
                            changeTurn();

                        gameViewModel.resetOpenedCardsValue();
                        gameViewModel.checkEndGameLevel();
                    }
                }, 300);
            }
        });

        gameViewModel.getObservablePlayerOneScore().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(final Integer score) {
                        tvPlayerScore.setText(getString(R.string.double_string, String.valueOf(score), "pts"));
                    }
                }
        );

        gameViewModel.getObservablePlayerTwoScore().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(final Integer score) {
                        tvPlayerScore2.setText(getString(R.string.double_string, String.valueOf(score), "pts"));
                    }
                }
        );

        gameViewModel.getObservableEndGameLevel().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(final Boolean gameEnded) {
                        if (gameEnded) {
                            endGameLayout.setVisibility(View.VISIBLE);

                            if (Game.getInstance().currentNumOfPlayers == 1)
                                tvEndGameScore.setText(getString(R.string.double_string,
                                        String.valueOf(gameViewModel.getCurrentPlayerOneScore()), "pts"));
                            else {
                                tvMessage.setVisibility(View.INVISIBLE);
                                tvEndGameScore.setText(getString(R.string.triple_string, "Player 1:",
                                        String.valueOf(gameViewModel.getCurrentPlayerOneScore()), "pts"));
                                tvEndGameScore2.setVisibility(View.VISIBLE);
                                tvEndGameScore2.setText(getString(R.string.triple_string, "Player 2:",
                                        String.valueOf(gameViewModel.getCurrentPlayerTwoScore()), "pts"));
                            }
                            Game.getInstance().currentPlayerOneName = mPreferenceHelper.getNamePlayerOne();
                            Game.getInstance().currentPlayerTwoName = mPreferenceHelper.getNamePlayerTwo();

                            gameViewModel.saveHighScores();
                            btMainMenu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                        }
                    }
                }
        );

        gameViewModel.getObservableShowNextLevelButton().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(final Boolean visibility) {
                        if(visibility) {
                            btNextLevelButton.setVisibility(View.VISIBLE);
                            tvGameOver.setVisibility(View.GONE);
                        }
                        else {
                            btNextLevelButton.setVisibility(View.GONE);
                            tvGameOver.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    public void startGame() {

        int gameNumOfCards = Game.getInstance().gameMode.toIntValue();

        LayoutInflater inflater = LayoutInflater.from(this);

        int idLayoutByGameMode = Game.getInstance().getIdLayoutBase();

        ConstraintLayout gameMode = (ConstraintLayout) inflater.inflate(idLayoutByGameMode, null, false);
        ((FrameLayout) findViewById(R.id.gameRegion)).addView(gameMode);

        List<EasyFlipView> listEasyFlipView = new ArrayList<>();

        for (int i = 0; i < gameNumOfCards; i++) {

            final int index = i;

            EasyFlipView layout = (EasyFlipView) inflater.inflate(R.layout.game_card_view, null, false);
            layout.setId(Utils.generateViewId());

            layout.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
                @Override
                public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                    gameViewModel.open(index, easyFlipView);
                }
            });

            listEasyFlipView.add(layout);
        }

        List<Integer> viewsIds = Game.getInstance().getViewIds();

        if (viewsIds != null) {

            List<View> gameImageViews = new ArrayList<>();

            for (int i = 0; i < gameNumOfCards; i++) {
                ((FrameLayout) findViewById(viewsIds.get(i))).addView(listEasyFlipView.get(i));
                gameImageViews.add((findViewById(viewsIds.get(i)).findViewById(R.id.ivImageCardViewBack)));
            }

            Game.getInstance().gameImageViews = gameImageViews;

            if (numPlayers > 1) {
                setFirstPlayerTurn();
            }

            Game.getInstance().initGame();
        } else {
            Toast.makeText(getApplicationContext(), R.string.not_possible, Toast.LENGTH_SHORT).show();
        }
    }

    public void hidePlayer2Stuff() {
        tvPlayerName2.setVisibility(View.GONE);
        tvPlayerScore2.setVisibility(View.GONE);
    }

    public void setFirstPlayerTurn() {
        tvPlayerName.setTypeface(null, Typeface.BOLD);
        tvPlayerName2.setTypeface(null, Typeface.NORMAL);
        tvPlayerScore.setTypeface(null, Typeface.BOLD);
        tvPlayerScore2.setTypeface(null, Typeface.NORMAL);
        Toast.makeText(this, R.string.player_1_turn, Toast.LENGTH_SHORT).show();
        gameViewModel.setTurnToPlayer(1);
    }

    public void setSecondPlayerTurn() {
        tvPlayerName.setTypeface(null, Typeface.NORMAL);
        tvPlayerName2.setTypeface(null, Typeface.BOLD);
        tvPlayerScore.setTypeface(null, Typeface.NORMAL);
        tvPlayerScore2.setTypeface(null, Typeface.BOLD);
        Toast.makeText(this, R.string.player_2_turn, Toast.LENGTH_SHORT).show();
        gameViewModel.setTurnToPlayer(2);
    }

    public void changeTurn() {
        if (Game.getInstance().currentPlayerTurn == 1)
            setSecondPlayerTurn();
        else
            setFirstPlayerTurn();
    }
}
