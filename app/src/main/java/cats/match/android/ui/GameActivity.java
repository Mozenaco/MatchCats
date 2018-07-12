package cats.match.android.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
    @BindView(R.id.tvChronometer) TextView tvChronometer;
    @BindView(R.id.cardA1) EasyFlipView cardA1;
    @BindView(R.id.cardA2) EasyFlipView cardA2;
    @BindView(R.id.cardB1) EasyFlipView cardB1;
    @BindView(R.id.cardB2) EasyFlipView cardB2;
    @BindView(R.id.cardC1) EasyFlipView cardC1;
    @BindView(R.id.cardC2) EasyFlipView cardC2;

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

        setupViews();
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

    public void setupViews() {

        tvPlayerName.setText(mPreferenceHelper.getNamePlayerOne());
        tvPlayerScore.setText("0");
        tvChronometer.setText("0:00");

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
                            mExplosionField.explode(actionAfterFlip.getFirstCard());
                            actionAfterFlip.getFirstCard().setOnClickListener(null);

                            mExplosionField.explode(actionAfterFlip.getSecondCard());
                            actionAfterFlip.getSecondCard().setOnClickListener(null);

                            gameViewModel.addScoreBecauseMatch();
                        }
                        gameViewModel.resetOpenedCardsValue();
                    }
                }, 1000);

                Toast.makeText(getApplicationContext(), ""+actionAfterFlip.getMatch(), Toast.LENGTH_SHORT).show();

            }
        });

        gameViewModel.getObservablePlayerOneScore().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(final Integer score) {
                        tvPlayerScore.setText(String.valueOf(score));
                    }
                }
        );

    }

    public void startOnePlayerGame(){

        Game.getInstance().gameImageViews = new ArrayList<>(Arrays.asList(
                cardA1.findViewById(R.id.ivImageCardViewBack),
                cardA2.findViewById(R.id.ivImageCardViewBack),
                cardB1.findViewById(R.id.ivImageCardViewBack),
                cardB2.findViewById(R.id.ivImageCardViewBack),
                cardC1.findViewById(R.id.ivImageCardViewBack),
                cardC2.findViewById(R.id.ivImageCardViewBack)));

        Game.getInstance().initGame();
    }

    public void startTwoPlayerGame(){


    }
}
