import java.util.LinkedList;

// Class generating a deck

public class Deck {

    private LinkedList<Card> deck;

    /**
     * Generates a deck
     */
    public Deck() {
        this.deck = new LinkedList<Card>();;

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card c = new Card(s, r);
                deck.add(c);
            }
        }
    }

    /**
     * Function to display all cards in the deck
     * @return deck as a string
     */
    public String deckToString() {

        String deckToString = "";

        for (Card card : deck) {
            deckToString += card.cardToString();
        }
        return deckToString;
    }

    /**
     * @return LinkedList<Card> return the deck
     */
    public LinkedList<Card> getDeck() {
        return deck;
    }

    /**
     * Removes a random card from the deck
     * @return the removed card
     */
    public Card removeCard() {

        int cardIndex = (int)(Math.random() * (deck.size()));
        Card card = deck.get(cardIndex);
        deck.remove(cardIndex);

        return card;
    }
}
