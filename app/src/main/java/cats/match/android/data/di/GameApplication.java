package cats.match.android.data.di;

import android.app.Application;

/**
 * Class extended from Application to configure Dagger settings and declare components.
 *
 * @author Mateus Andrade
 * @since 16/07/2018
 */
public class GameApplication extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = createComponent();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    private AppComponent createComponent() {
        return DaggerAppComponent
                .builder()
                .preferenceModule(new PreferenceModule(this))
                .build();
    }
}


