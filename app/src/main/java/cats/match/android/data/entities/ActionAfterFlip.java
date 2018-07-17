package cats.match.android.data.entities;

import android.view.View;

/**
 * This class is a helper to see if one card is a match with another. An object of this class
 * keeps in memory references of the views for turn down(in case of don't match) or
 * destroy(in case of match) these views.
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
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
