import java.util.LinkedList;

// Card values: 
// 2-10 -> face value (36 cards)
// Face cards (Jack, Queen, King) -> 10 (12 cards)
// Ace -> 1 or 11 (4 cards)

public class Deck {

    private static final int nrOfCards = 52;
    private LinkedList<Card> deck;

    public Deck() {
        this.deck = new LinkedList<Card>();;

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card c = new Card(s, r);
                deck.add(c);
            }
        }
    }

    public String printToString() {

        String printDeck = "";

        for (Card card : deck) {
            printDeck += card.printCard();
        }
        return printDeck;
    }


    
    

    /**
     * @return LinkedList<Card> return the deck
     */
    public LinkedList<Card> getDeck() {
        return deck;
    }

    public Card removeCard() {

        int cardIndex = (int)(Math.random() * (deck.size()));
        Card card = deck.get(cardIndex);
        deck.remove(cardIndex);

        return card;
    }
}
