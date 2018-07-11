package cats.match.android.data.di;

import android.app.Application;

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


