package cats.match.android.data.entities.enums;

/**
 * Enum that provides the difficulty level of the game
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public enum GameMode {
    EASY("Easy", 6),
    MEDIUM("Medium", 12),
    HARD("Hard", 16);

    private String stringValue;
    private int intValue;
    private GameMode(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public Integer toIntValue() {
        return intValue;
    }
}