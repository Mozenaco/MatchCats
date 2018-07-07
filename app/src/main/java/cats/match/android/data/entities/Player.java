package cats.match.android.data.entities;

/*
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 *
 */

public class Player {

    String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*Others properties about Player could come here for example:
     * How many times this player won...
     * How many time this person have spent on the game...
     * How many correct combinations this player did.
     */
}