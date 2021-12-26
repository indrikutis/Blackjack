public class Card {
    
    private Rank rank;
    private Suit suit;

    /**
     * Constructor for a card
     * @param suit of a card
     * @param rank of a card
     */
    public Card(Suit suit, Rank rank) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return Rank return the rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * @return Suit return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @param suit the suit to set
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
     * Function to display a card
     * @return string of a card
     */
    public String cardToString() {
        String cardString = "";

        String firstValueRank = Integer.toString(this.rank.getValue());
        int secondValue = this.rank.getAceValue2();
        String secondValueRank =  secondValue == -1 ? "" : " or " + secondValue;
        cardString += this.suit.toString() + "  " + this.rank.toString() + "  " + firstValueRank + secondValueRank + "\n";

        return cardString;
    }

}
