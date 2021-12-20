import java.util.LinkedList;

public class Player {
    
    private static final int maxPoints = 21;
    private int totalPoints;
    private LinkedList<Card> hand;
    private String name;
    private Boolean stand;
    private Boolean dealer;

    public Player(String name) {
        this.hand = new LinkedList<Card>();
        this.name = name;
        this.totalPoints = 0;
        this.stand = false;
        this.dealer = false;
    }

    public Player(String name, Boolean dealer) {
        hand = new LinkedList<Card>();
        this.name = name;
        totalPoints = 0;
        stand = false;
        this.dealer = true;
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

    public void recalculateTotalPoints(){
        System.out.println("here");
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
            if (this.totalPoints + Rank.ACE.getAceValue2() + (aceCount - 1) <= maxPoints) {
                this.totalPoints += Rank.ACE.getAceValue2() + (aceCount - 1); 
            } else {
                this.totalPoints += aceCount;
            }
        }
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

}
