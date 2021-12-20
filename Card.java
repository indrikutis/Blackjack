public class Card {
    
    private Rank rank;
    private Suit suit;

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

    public String printCard() {
        String printCard = "";

        String firstValueRank = Integer.toString(this.rank.getValue());
        int secondValue = this.rank.getAceValue2();
        String secondValueRank =  secondValue == -1 ? "" : " or " + secondValue;
        printCard += this.suit.toString() + "  " + this.rank.toString() + "  " + firstValueRank + secondValueRank + "\n";

        return printCard;
    }

}
