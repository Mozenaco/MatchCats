package cats.match.android.data.entities;

/*
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 *
 */

public class Game {

    int currentScore;
    int currentTime;

    public Game(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}