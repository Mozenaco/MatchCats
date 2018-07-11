package cats.match.android.data.di;

import javax.inject.Singleton;

import cats.match.android.data.sharedpreferences.PreferenceHelper;
import dagger.Module;
import dagger.Provides;


@Module
public class PreferenceModule {

    @Provides
    @Singleton
    PreferenceHelper providesPreferenceHelper() {
        return PreferenceHelper.getInstance();
    }
}
