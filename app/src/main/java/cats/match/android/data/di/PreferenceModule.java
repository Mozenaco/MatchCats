package cats.match.android.data.di;

import android.content.Context;

import javax.inject.Singleton;

import cats.match.android.data.sharedpreferences.PreferenceHelper;
import dagger.Module;
import dagger.Provides;

/**
 * Module for provide instance for preferences.
 *
 * @author Mateus Andrade
 * @since 16/07/2018
 */
@Module
public class PreferenceModule {

    Context context;

    public PreferenceModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    PreferenceHelper providesPreferenceHelper() {
        return PreferenceHelper.getInstance(context);
    }
}
