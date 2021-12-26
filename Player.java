import java.util.LinkedList;

public class Player {

    public static final int maxPoints = 21;
    public static final int minDealerPoints = 17;

    private int totalPoints;
    private LinkedList<Card> hand; // List for player's cards
    private String name;
    private Boolean stand;
    private Boolean dealer;
    private int chips;
    private int currentBet;
    private Boolean bust;
    private int id;

    /**
     * Constructor for a player
     * 
     * @param name   of the player
     * @param unique identifier
     */
    public Player(String name, int id) {
        this.hand = new LinkedList<Card>();
        this.name = name;
        this.totalPoints = 0;
        this.stand = false;
        this.dealer = false;
        this.chips = 5; // Player by default starts with 5 chips
        this.bust = false;
        this.currentBet = 0;
        this.id = id;
    }

    // Constructor for a dealer
    public Player(String name, Boolean dealer) {
        this.hand = new LinkedList<Card>();
        this.name = name;
        this.totalPoints = 0;
        this.stand = false;
        this.dealer = true;
        this.chips = 10000; // Dealer by default starts with 5 chips
        this.bust = false;
        this.currentBet = 0;
    }

    /**
     * Player draws a card
     * 
     * @param card to be added to the hand
     */
    public void hit(Card card) {
        this.hand.add(card); // A card is added to te hand
        recalculateTotalPoints(); // Recalculates total points of the player
    }

    /**
     * Function to display player's information
     * 
     * @return string of player's hand and points information
     */
    public String handToString() {
        String printHand = "";

        // Add all cards to the string
        for (Card card : hand) {
            printHand += card.cardToString();
        }

        // Indicate if the player is a dealer
        String dealer = (this.dealer ? " (dealer)" : "");

        // Add points information
        String points = "--------------------\nTotal points: " + Integer.toString(this.totalPoints) + "\n";

        return "####################\n" + this.name + dealer + ":\n####################\nHand:\n" + printHand + points
                + "--------------------\n" + (dealer.equals("") ? chipsInfoToString() + "\n" : "");
    }

    /**
     * Function to display dealer's information
     * @return string of dealers's hand and points information
     */
    public String dealerHandToString() {
        String printDealer = "";

        // Add all cards to the string
        for (int i = 0; i < hand.size() - 1; ++i) {
            printDealer += hand.get(i).cardToString();
        }
        return "####################\n" + this.name + " (dealer):\n####################\nHand:\n" + printDealer
                + "THE SECOND CARD IS CARD HIDDEN\n--------------------\n";
    }

    /**
     * Get the message of a current bet and total chips information
     * @return the current betting, chips count information
     */
    public String chipsInfoToString() {
        return "The current bet: " + this.currentBet + "\nTotal number of chips (including the current bet): "
                + this.chips;
    }

    /**
     * Recalculates total points of a hand, correctly choosing the ACE value
     * Ace value is maximized if it's possible without a bust
     */
    public void recalculateTotalPoints() {

        this.totalPoints = 0;
        int aceCount = 0;

        // Calculates points of cards without ACE(s) and counts ACEs
        for (Card card : hand) {
            if (card.getRank() != Rank.ACE) {
                this.totalPoints += card.getRank().getValue();
            } else {
                aceCount++;
            }
        }

        if (aceCount > 0) {
            // Maximizes the ACE value if it doesn't bust a player.
            if (this.totalPoints + Rank.ACE.getAceValue2() + (aceCount - 1) <= maxPoints) {
                this.totalPoints += Rank.ACE.getAceValue2() + (aceCount - 1);
            } else {
                this.totalPoints += aceCount;
            }
        }
    }

    /**
     * 
     * @return message of a current bet
     */
    public String currentBetToString() {
        return "The current bet is: " + this.currentBet;
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
    }

    /**
     * Updates teh current chip value
     * 
     * @param chips to add/deduct
     */
    public void updateChips(int chips) {
        this.chips += chips;
    }

    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
