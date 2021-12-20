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

            String firstValueRank = Integer.toString(card.getRank().getValue()[0]);
            int secondValue = card.getRank().getValue()[1];
            String secondValueRank =  secondValue == -1 ? "" : " or " + secondValue;
            printDeck += card.getSuit().toString() + "  " + card.getRank().toString() + "  " + firstValueRank + secondValueRank + "\n";

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

        int cardIndex = (int)(Math.random() * (deck.size() + 1));
        Card card = deck.get(cardIndex);
        deck.remove(cardIndex);

        return card;
    }
}
