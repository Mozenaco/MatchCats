package cats.match.android.data.entities;

import android.support.annotation.NonNull;

public class HighScore implements Comparable{

    String Name;
    int Score;

    public HighScore(String name, int score) {
        Name = name;
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    @Override
    public int compareTo(@NonNull Object object) {

        return(((HighScore) object).Score - Score);
    }
}