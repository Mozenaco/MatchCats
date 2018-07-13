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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.di.DaggerAppComponent;
import cats.match.android.data.di.PreferenceModule;
import cats.match.android.data.entities.ActionAfterFlip;
import cats.match.android.data.entities.Game;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
import cats.match.android.matchcats.R;
import cats.match.android.viewmodel.GameViewModel;
import tyrantgit.explosionfield.ExplosionField;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.tvPlayerName) TextView tvPlayerName;
    @BindView(R.id.tvPlayerName2) TextView tvPlayerName2;
    @BindView(R.id.tvPlayerScore) TextView tvPlayerScore;
    @BindView(R.id.tvPlayerScore2) TextView tvPlayerScore2;
    @BindView(R.id.tvChronometer) TextView tvChronometer;
    @BindView(R.id.cardA1) EasyFlipView cardA1;
    @BindView(R.id.cardA2) EasyFlipView cardA2;
    @BindView(R.id.cardB1) EasyFlipView cardB1;
    @BindView(R.id.cardB2) EasyFlipView cardB2;
    @BindView(R.id.cardC1) EasyFlipView cardC1;
    @BindView(R.id.cardC2) EasyFlipView cardC2;
    @BindView(R.id.endGameLayout) ConstraintLayout endGameLayout;
    @BindView(R.id.btMainMenu) Button btMainMenu;
    @BindView(R.id.tvEndGameScore) TextView tvEndGameScore;
    @BindView(R.id.tvEndGameScore2) TextView tvEndGameScore2;
    @BindView(R.id.tvMessage) TextView tvMessage;

    @Inject
    PreferenceHelper mPreferenceHelper;
    DaggerAppComponent app;
    int numPlayers;

    private ExplosionField mExplosionField;

    GameViewModel gameViewModel;

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
        gameViewModel = new GameViewModel(mPreferenceHelper);
        mExplosionField = ExplosionField.attach2Window(this);
        numPlayers = getIntent().getIntExtra(EXTRA_NUM_PLAYERS, 1);

        mPreferenceHelper.setNamePlayerOne(Game.getInstance().currentPlayerOneName);
        mPreferenceHelper.setNamePlayerTwo(Game.getInstance().currentPlayerTwoName);

        Game.getInstance().resetGame();

        setupViews();
        setupObservers();

        startGame();
    }

    public void setupViews() {

        tvPlayerName.setText(mPreferenceHelper.getNamePlayerOne());
        tvPlayerScore.setText("0 pts");
        tvChronometer.setText("0:00");

        if(Game.getInstance().currentNumOfPlayers > 1){

            tvPlayerName2.setText(mPreferenceHelper.getNamePlayerTwo());
            tvPlayerScore2.setText("0 pts");
        }

        cardA1.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(0, easyFlipView);
            }
        });

        cardA2.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(1, easyFlipView);
            }
        });

        cardB1.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(2, easyFlipView);
            }
        });

        cardB2.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(3, easyFlipView);
            }
        });

        cardC1.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(4, easyFlipView);
            }
        });

        cardC2.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                gameViewModel.open(5, easyFlipView);
            }
        });

        if(numPlayers ==1){
            hidePlayer2Stuff();
        }

        new CountDownTimer(20000, 1000) {

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
                        if(!actionAfterFlip.getMatch()) {
                            ((EasyFlipView) actionAfterFlip.getFirstCard()).flipTheView();
                            ((EasyFlipView) actionAfterFlip.getSecondCard()).flipTheView();

                        }else{
                            try {
                                mExplosionField.explode(actionAfterFlip.getFirstCard());
                                actionAfterFlip.getFirstCard().setOnClickListener(null);

                                mExplosionField.explode(actionAfterFlip.getSecondCard());
                                actionAfterFlip.getSecondCard().setOnClickListener(null);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            gameViewModel.addScoreBecauseMatch();
                        }

                        if(Game.getInstance().currentNumOfPlayers > 1)
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
                        tvPlayerScore.setText(String.valueOf(score)+" pts");
                    }
                }
        );

        gameViewModel.getObservablePlayerTwoScore().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(final Integer score) {
                        tvPlayerScore2.setText(String.valueOf(score)+" pts");
                    }
                }
        );

        gameViewModel.getObservableEndGameLevel().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(final Boolean gameEnded) {
                        if(gameEnded) {
                            endGameLayout.setVisibility(View.VISIBLE);

                            if(Game.getInstance().currentNumOfPlayers == 1)
                                tvEndGameScore.setText(String.valueOf(gameViewModel.getCurrentPlayerOneScore())+" pts");
                            else{
                                tvMessage.setVisibility(View.INVISIBLE);
                                tvEndGameScore.setText("Player 1: "+String.valueOf(gameViewModel.getCurrentPlayerOneScore())+" pts");
                                tvEndGameScore2.setVisibility(View.VISIBLE);
                                tvEndGameScore2.setText("Player 2: "+String.valueOf(gameViewModel.getCurrentPlayerTwoScore())+" pts");
                            }

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
    }

    public void startGame(){

        Game.getInstance().gameImageViews = new ArrayList<>(Arrays.asList(
                cardA1.findViewById(R.id.ivImageCardViewBack),
                cardA2.findViewById(R.id.ivImageCardViewBack),
                cardB1.findViewById(R.id.ivImageCardViewBack),
                cardB2.findViewById(R.id.ivImageCardViewBack),
                cardC1.findViewById(R.id.ivImageCardViewBack),
                cardC2.findViewById(R.id.ivImageCardViewBack)));

        if(numPlayers > 1){
            setFirstPlayerTurn();
        }

        Game.getInstance().initGame();

    }

    public void hidePlayer2Stuff(){
        tvPlayerName2.setVisibility(View.GONE);
        tvPlayerScore2.setVisibility(View.GONE);
    }

    public void setFirstPlayerTurn(){
        tvPlayerName.setTypeface(null, Typeface.BOLD);
        tvPlayerName2.setTypeface(null, Typeface.NORMAL);
        tvPlayerScore.setTypeface(null, Typeface.BOLD);
        tvPlayerScore2.setTypeface(null, Typeface.NORMAL);
        Toast.makeText(this, R.string.player_1_turn, Toast.LENGTH_SHORT).show();
        gameViewModel.setTurnToPlayer(1);
    }

    public void setSecondPlayerTurn(){
        tvPlayerName.setTypeface(null, Typeface.NORMAL);
        tvPlayerName2.setTypeface(null, Typeface.BOLD);
        tvPlayerScore.setTypeface(null, Typeface.NORMAL);
        tvPlayerScore2.setTypeface(null, Typeface.BOLD);
        Toast.makeText(this, R.string.player_2_turn, Toast.LENGTH_SHORT).show();
        gameViewModel.setTurnToPlayer(2);
    }
    public void changeTurn(){
        if(Game.getInstance().currentPlayerTurn == 1)
            setSecondPlayerTurn();
        else
            setFirstPlayerTurn();

    }
}
