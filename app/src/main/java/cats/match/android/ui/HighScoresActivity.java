package cats.match.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cats.match.android.data.di.DaggerAppComponent;
import cats.match.android.data.di.PreferenceModule;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
import cats.match.android.matchcats.R;
import cats.match.android.ui.adapter.HighScoresAdapter;

/**
 * HighScoresActivity is a view to show a list of highscores
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class HighScoresActivity extends AppCompatActivity {


    @BindView(R.id.rvHighScores)
    RecyclerView rvHighScores;

    @Inject
    PreferenceHelper mPreferenceHelper;

    DaggerAppComponent app;

    public static Intent buildIntentForHighScoresActivity(Context context) {

        return new Intent(context, HighScoresActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        ButterKnife.bind(this);

        app.builder().preferenceModule(new PreferenceModule(this)).build().inject(this);

        rvHighScores.setLayoutManager(new LinearLayoutManager(this));

        HighScoresAdapter adapter = new HighScoresAdapter(mPreferenceHelper.getHighScores());

        rvHighScores.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
