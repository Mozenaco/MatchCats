package cats.match.android.data.di;

import javax.inject.Singleton;

import cats.match.android.ui.GameActivity;
import cats.match.android.ui.HighScoresActivity;
import cats.match.android.ui.MenuActivity;
import dagger.Component;

/**
 * Component that create the bridge between the module and injected classes
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
@Singleton
@Component(modules = PreferenceModule.class)
public interface AppComponent {

    void inject(HighScoresActivity highScoresActivity);

    void inject(GameActivity gameActivity);
}