package cats.match.android.data.sharedpreferences;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cats.match.android.data.entities.HighScore;

public class PreferenceHelper {

    private static PreferenceHelper instance;

    private static final String PREFS_NAME = "default_preferences";
    private static final String NUM_OF_PLAYERS = "num_of_players";
    private static final String NAME_PLAYER_ONE = "name_player_one";
    private static final String NAME_PLAYER_TWO = "name_player_two";
    private static final String HIGHSCORES = "highscores";

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
                .getInt(NUM_OF_PLAYERS, 1);
    }

    public void setNumOfPlayers(@NonNull Context context, int value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putInt(NUM_OF_PLAYERS, value).apply();
    }

    public String getNamePlayerOne(@NonNull Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(NAME_PLAYER_ONE, "");
    }

    public void setNamePlayerOne(@NonNull Context context, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(NAME_PLAYER_ONE, value).apply();
    }

    public String getNamePlayerTwo(@NonNull Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(NAME_PLAYER_TWO, "");
    }

    public void setNamePlayerTwo(@NonNull Context context, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(NAME_PLAYER_TWO, value).apply();
    }

    public List<HighScore> getHighScores(@NonNull Context context) {

        //Gson gson = new Gson();
        //String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(HIGHSCORES, "");
        //return highScore = gson.fromJson(json, HighScore.class);

        String connectionsJSONString = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(HIGHSCORES, null);
        Type type = new TypeToken< List < HighScore >>() {}.getType();
        List <HighScore> highScores = new Gson().fromJson(connectionsJSONString, type);
        return highScores;
    }

    public void setHighScores(@NonNull Context context, List<HighScore> highScores) {

        Gson gson = new Gson();
        String json = gson.toJson(highScores);
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(HIGHSCORES, json)
                .apply();
    }
}