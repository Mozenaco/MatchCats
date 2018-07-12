package cats.match.android.data.entities.enums;

public enum GameMode {
    EASY("Easy", 0),
    MEDIUM("Medium", 1),
    HARD("Hard", 2);

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
}