import java.util.LinkedList;

public class Game {

    final int maxPoints = 21;
    private Deck deck;

    public Game( Deck deck, LinkedList<Player> players) {
        this.deck = deck;

        // First card
        for (Player player : players) {
            player.hit(this.addCard());
            System.out.println(player.printHand());
        }

        // Second card
        for (Player player : players) {
            player.hit(this.addCard());
            if (!player.isDealer()) {
                System.out.println(player.printHand());     // Only print the hand of the player. Dealer keeps the second card hidden.
            }
        }
    }

    public Card addCard() {
        return this.deck.removeCard();
    }

}