package cats.match.android.data.entities;

import android.view.View;

public class ActionAfterFlip {

    View firstCard;
    View secondCard;
    Boolean isMatch;

    public ActionAfterFlip(View firstCard, View secondCard, Boolean isMatch) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
        this.isMatch = isMatch;
    }

    public View getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(View firstCard) {
        this.firstCard = firstCard;
    }

    public View getSecondCard() {
        return secondCard;
    }

    public void setSecondCard(View secondCard) {
        this.secondCard = secondCard;
    }

    public Boolean getMatch() {
        return isMatch;
    }

    public void setMatch(Boolean match) {
        isMatch = match;
    }
}
