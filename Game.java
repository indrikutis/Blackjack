import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.DelayQueue;

public class Game {

    
    private Deck deck;
    private LinkedList<Player> players;
    private Scanner scanner = new Scanner(System.in);

    public Game( Deck deck, LinkedList<Player> players) {
        this.deck = deck;
        this.players = players;

        //leave the game, add a new player to the game

        placeBets();
        drawInitialCards();
        playRound();

    }

    /**
     * Every player except the dealer places a bet
     */
    private void placeBets() {
        for (Player player : players) {
            if (!player.isDealer()) {

                System.out.println(player.getName() + ", please place a bet:");
                int bet = scanner.nextInt();

                while (player.getChips() < bet) {
                    System.out.println("Place a lower bet. You currently have " + player.getChips() + " chips. " + player.getName() + ", please place a bet:");
                    bet = scanner.nextInt();
                }
                player.setCurrentBet(bet);
            }
        }
    }

    /**
     * Every player gets 2 cards
     */
    private void drawInitialCards() {
        // First card
        System.out.println("\n******************* DRAW THE FIRST CARD *******************\n");
        Player dealer = null;
        for (Player player : players) {
            if (player.isDealer()) {
                player.hit(addCard());
                dealer = player;
            } else {
                playerHit(player);
            }
        }
        if (dealer!=null) {                                 // Dealer draws the card last
            playerHit(dealer);
        }

        // Second card
        System.out.println("\n******************* DRAW THE SECOND CARD ******************\n");

        for (Player player : players) {
            if (player.isDealer()) {                                // Dealer keeps the second card hidden
                player.hit(addCard());
                System.out.println(player.dealerHandToString());     
            } else {
                playerHit(player);
            }
        }
    }

    private void playRound() {

        Player dealer = null;
        for (Player player : players) {
            if (player.isDealer()) {                        // Find the dealer  
                dealer = player;                            // It is assumed that only one player can be a dealer           
            } else {
                askInput(player);                           // Allow every player to perform action
            }
        }

        if (dealer!=null && dealer.getTotalPoints() < dealer.getMinDealerPoints()) { // If dealer has less than 16 points, they draw a card
            askInput(dealer);
        }

        if (dealer.getTotalPoints() < 17) {                 // If the dealer's hand is 16 or lower, the have to draw a card
            playerHit(dealer);
        }

        
    }

    /**
     * Aks user input to 'hit' or 'stand'
     * Continuously asks user input until the 'stand' action is selected
     * @param player performing an action
     */
    public void askInput(Player player) {

        while(true) {

            System.out.println(player.getName() + ", please enter an action: (hit/stand) ");
            String action = scanner.nextLine(); 
                
            if (action.toLowerCase().equals("hit")) {
                playerHit(player);
            } else if (action.toLowerCase().equals("stand")){
                System.out.println();                                       // Print a new line
                return;
            } else {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    public void playerHit(Player player) {
        player.hit(addCard());
        System.out.println("\n" + player.handToString());
        //chekckIfLost(player);                                     // TODO

    }


    public Card addCard() {
        return this.deck.removeCard();
    }

}