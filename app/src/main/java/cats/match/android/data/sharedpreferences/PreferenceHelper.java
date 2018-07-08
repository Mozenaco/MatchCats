package cats.match.android.data.sharedpreferences;

import android.content.Context;
import android.support.annotation.NonNull;

public class PreferenceHelper {
    private static PreferenceHelper instance;
    private static final String PREFS_NAME = "default_preferences";

    public synchronized static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    private PreferenceHelper() {
    }

    public int getNumOfPlayers(@NonNull Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt("NUM_OF_PLAYERS", 1);
    }

    public void setNumOfPlayers(@NonNull Context context, int value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putInt("NUM_OF_PLAYERS", value).apply();
    }
}