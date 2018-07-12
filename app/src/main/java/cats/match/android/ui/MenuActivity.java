package cats.match.android.ui;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.di.DaggerAppComponent;
import cats.match.android.data.di.PreferenceModule;
import cats.match.android.data.entities.Game;
import cats.match.android.data.entities.Photo;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
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

    @Inject
    PreferenceHelper mPreferenceHelper;

    DaggerAppComponent app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        ButterKnife.bind(this);

        setupView();
        setupObservers();

        app.builder().preferenceModule(new PreferenceModule(this)).build().inject(this);

        getData();
    }

    public void setupView() {

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        new ContextThemeWrapper(MenuActivity.this, R.style.myDialog));
                builder.setTitle(getString(R.string.what_name));

                final EditText input = new EditText(MenuActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton(getString(R.string.play), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPreferenceHelper.setNamePlayerOne(input.getText().toString());
                        play();
                    }
                });

                builder.show();
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

    public void play() {

        int numPlayers;
        int radioChecked = rgNumberOfPlayers.getCheckedRadioButtonId();
        if(radioChecked == R.id.rbOnePlayer){
            numPlayers = 1;
        }else
            numPlayers = 2;

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
                //Toast.makeText(getApplicationContext(), photos.size() + " photos Loaded", Toast.LENGTH_SHORT).show();
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
