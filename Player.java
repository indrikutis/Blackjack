import java.util.LinkedList;

public class Player {
    
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
    }

    public String printHand() {
        String printHand = "";
        for (Card card : hand) {

            String firstValueRank = Integer.toString(card.getRank().getValue()[0]);
            int secondValue = card.getRank().getValue()[1];
            String secondValueRank =  secondValue == -1 ? "" : " or " + secondValue;
            printHand += card.getSuit().toString() + "  " + card.getRank().toString() + "  " + firstValueRank + secondValueRank + "\n";

        }
        return this.name + ":\n" + printHand;
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
