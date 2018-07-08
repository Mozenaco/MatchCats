package cats.match.android.ui;

import android.arch.lifecycle.Observer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cats.match.android.data.entities.Photo;
import cats.match.android.matchcats.R;
import cats.match.android.viewmodel.LoadingViewModel;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.btPlay)
    Button btPlay;
    @BindView(R.id.btHighscores)
    Button btHighscores;
    @BindView(R.id.btExit)
    Button btExit;
    @BindView(R.id.rgNumberOfPlayers)
    RadioGroup rgNumberOfPlayers;

    LoadingViewModel loadingViewModel = new LoadingViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ButterKnife.bind(this);

        setupView();
        setupObservers();
        //getData();

    }


    public void setupView() {

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
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

    public void play(){

        startActivity(GameActivity.buildIntentForGameActivity(this, 1));
    }

    public void highScores(){

        startActivity(HighScoresActivity.buildIntentForHighScoresActivity(this));
    }

    public void setupObservers() {

        // Observe photos data
        loadingViewModel.getObservablePhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                Toast.makeText(getApplicationContext(), photos.size() + " photos Loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {

        loadingViewModel.getPhotos();
    }

}
