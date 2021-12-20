import java.util.LinkedList;
import java.util.Scanner;

public class Game {

    
    private Deck deck;
    private LinkedList<Player> players;
    private Scanner scanner = new Scanner(System.in);

    public Game( Deck deck, LinkedList<Player> players) {
        this.deck = deck;
        this.players = players;

        initialSetup();
        play();

    }

    private void initialSetup() {
        // First card
        System.out.println("\n******************* DRAW THE FIRST CARD *******************\n");
        for (Player player : players) {
            player.hit(this.addCard());
            System.out.println(player.handToString());
        }

        // Second card
        System.out.println("\n******************* DRAW THE SECOND CARD ******************\n");

        for (Player player : players) {
            player.hit(this.addCard());
            if (player.isDealer()) {
                System.out.println(player.dealerHandToString());     // Print player's hand
            } else {
                System.out.println(player.handToString());       // Dealer keeps the second card hidden
            }
        }
    }

    private void play() {

            Player dealer = null;
            for (Player player : players) {
                if (player.isDealer()) {                        // It is assumed that only one player can be a dealer
                    dealer = player;                
                } else {
                    askInput(player);
                }
            }

            if (dealer!=null) {                                 // Dealer draws the card last
                askInput(dealer);
            }

            if (dealer.getTotalPoints() < 16) {                 // NOTE: on of the rules
                dealer.hit(this.addCard());
                System.out.println(dealer.handToString());
            }

        
    }

    public void askInput(Player player) {

        while(true) {

            System.out.println(player.getName() + ", please enter an action: (hit/stand) ");
            String action = scanner.nextLine(); 
                
            if (action.toLowerCase().equals("hit")) {
                player.hit(this.addCard());
                System.out.println("\n" + player.handToString());
                //chekckIfLost(player);                                     // TODO
                return;
            } else if (action.toLowerCase().equals("stand")){
                System.out.println();                                       // Print a new line
                return;
            } else {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    public Card addCard() {
        return this.deck.removeCard();
    }

}