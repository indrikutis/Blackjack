import java.util.LinkedList;

public class Player {
    
    private static final int maxPoints = 21;
    private static final int minDealerPoints = 17;
    private int totalPoints;
    private LinkedList<Card> hand;
    private String name;
    private Boolean stand;
    private Boolean dealer;
    private int chips;
    private int currentBet;
    private Boolean bust;

    public Player(String name) {
        this.hand = new LinkedList<Card>();
        this.name = name;
        this.totalPoints = 0;
        this.stand = false;
        this.dealer = false;
        this.chips = 5;
        this.bust = false;
        this.currentBet = 0;
    }

    // Constructor for a dealer
    public Player(String name, Boolean dealer) {
        this.hand = new LinkedList<Card>();
        this.name = name;
        this.totalPoints = 0;
        this.stand = false;
        this.dealer = true;
        this.chips = 10000;
        this.bust = false;
        this.currentBet = 0;
    }

    public void hit(Card card) {
        this.hand.add(card);
        recalculateTotalPoints();
    }

    public String handToString() {
        String printHand = "";
        for (Card card : hand) {
            printHand += card.printCard();
        }
        String dealer = this.dealer ? " (dealer)" : "";
        String points = this.dealer ? "" : "--------------------\nTotal points: " + Integer.toString(this.totalPoints) + "\n";
        return this.name + dealer + ":\n" + printHand + points;
    }

    public String dealerHandToString() {
        String printDealer = "";

        for (int i = 0; i < hand.size() - 1; ++i) {
            printDealer += hand.get(i).printCard();
        }
        return this.name + " (dealer):\n" + printDealer + "CARD HIDDEN\n";
    }

    /**
     * Recalculates total points of a hand, correctly choosing the ACE value
     * Ace value is maximized if it's possible without a bust.
     */
    public void recalculateTotalPoints(){

        this.totalPoints = 0;
        int aceCount = 0;

        for (Card card : hand) {
            if (card.getRank() != Rank.ACE) {
                this.totalPoints += card.getRank().getValue();
            } else {
                aceCount++;
            }
        }

        System.out.println("ACE count " + aceCount);

        if (aceCount > 0) {
            System.out.println(maxPoints + " " + this.totalPoints + " " + Rank.ACE.getAceValue2() +" " + (aceCount - 1));

            // Maximizes the ACE value if it doesn't bust a player.
            if (this.totalPoints + Rank.ACE.getAceValue2() + (aceCount - 1) <= maxPoints) {
                this.totalPoints += Rank.ACE.getAceValue2() + (aceCount - 1); 
            } else {
                this.totalPoints += aceCount;
            }
        }

        // Check if bust
        if( totalPoints > maxPoints) {
            this.bust = true;
        }
    }

    public String currentBetToString() {
        return "The current bet is: " + this.currentBet;
    }

    public int getMinDealerPoints() {
        return minDealerPoints;
    }

    /**
     * @return int return the totalPoints
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * @param totalPoints the totalPoints to set
     */
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * @return LinkedList<Card> return the hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return Boolean return the stand
     */
    public Boolean isStand() {
        return stand;
    }

    /**
     * @param stand the stand to set
     */
    public void setStand(Boolean stand) {
        this.stand = stand;
    }


    /**
     * @return Boolean return the dealer
     */
    public Boolean isDealer() {
        return dealer;
    }

    /**
     * @param dealer the dealer to set
     */
    public void setDealer(Boolean dealer) {
        this.dealer = dealer;
    }


    /**
     * @return int return the chips
     */
    public int getChips() {
        return chips;
    }

    /**
     * @param chips the chips to set
     */
    public void setChips(int chips) {
        this.chips = chips;
    }


    /**
     * @return Boolean return the bust
     */
    public Boolean isBust() {
        return bust;
    }

    /**
     * @param bust the bust to set
     */
    public void setBust(Boolean bust) {
        this.bust = bust;
    }


    /**
     * @return int return the currentBet
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * @param currentBet the currentBet to set
     */
    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
        this.chips -= this.currentBet;                  // Reduce the total of chips by the current bet
    }

}
