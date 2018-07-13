package cats.match.android.data.di;

import javax.inject.Singleton;

import cats.match.android.ui.GameActivity;
import cats.match.android.ui.HighScoresActivity;
import cats.match.android.ui.MenuActivity;
import dagger.Component;

@Singleton
@Component(modules = PreferenceModule.class)
public interface AppComponent {

    void inject(HighScoresActivity highScoresActivity);

    void inject(GameActivity gameActivity);
}