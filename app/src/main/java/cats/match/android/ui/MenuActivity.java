package cats.match.android.ui;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.entities.Game;
import cats.match.android.data.entities.Photo;
import cats.match.android.data.entities.enums.GameMode;
import cats.match.android.matchcats.R;
import cats.match.android.viewmodel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    //Binding Views
    @BindView(R.id.btPlay)
    Button btPlay;
    @BindView(R.id.btHighscores)
    Button btHighscores;
    @BindView(R.id.btExit)
    Button btExit;
    @BindView(R.id.rgNumberOfPlayers)
    RadioGroup rgNumberOfPlayers;
    @BindView(R.id.progressBar)
    FrameLayout progressBar;

    MenuViewModel loadingViewModel = new MenuViewModel();

    int numPlayers = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ButterKnife.bind(this);

        setupView();
        setupObservers();
        getData();
    }

    public void setupView() {

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        new ContextThemeWrapper(MenuActivity.this, R.style.myDialog));

                builder.setTitle(getString(R.string.what_name));

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_players_names, null);
                builder.setView(dialogView);

                builder.setPositiveButton(getString(R.string.play), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Game.getInstance().currentPlayerOneName =
                                ((EditText) dialogView.findViewById(R.id.etNamePlayerOne))
                                        .getText()
                                        .toString();

                        if (numPlayers > 1) {
                            Game.getInstance().currentPlayerTwoName =
                                    ((EditText) dialogView.findViewById(R.id.etNamePlayerTwo))
                                            .getText()
                                            .toString();
                        }

                        play();
                    }
                });

                builder.show();

                checkNumPlayers();
                if(numPlayers > 1)
                    dialogView.findViewById(R.id.etNamePlayerTwo).setVisibility(View.VISIBLE);
                else
                    dialogView.findViewById(R.id.etNamePlayerTwo).setVisibility(View.GONE);

            }
        });

        btHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highScores();
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void checkNumPlayers(){
        int radioChecked = rgNumberOfPlayers.getCheckedRadioButtonId();
        if(radioChecked == R.id.rbOnePlayer){
            numPlayers = 1;
        }else
            numPlayers = 2;
    }

    public void play() {
        Game.getInstance().gameMode = GameMode.EASY;
        checkNumPlayers();
        Game.getInstance().currentNumOfPlayers = numPlayers;
        startActivity(GameActivity.buildIntentForGameActivity(this, numPlayers));
    }

    public void highScores() {

        startActivity(HighScoresActivity.buildIntentForHighScoresActivity(this));
    }

    public void setupObservers() {

        // Observe photos data
        loadingViewModel.getObservablePhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                Game.getInstance().photos = photos;
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        loadingViewModel.getPhotos();
    }
}
