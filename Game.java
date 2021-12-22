import java.util.LinkedList;
import java.util.Scanner;

public class Game {

    private Deck deck;
    private LinkedList<Player> players;
    private Scanner scanner = new Scanner(System.in);
    private Player dealer;

    public Game(Deck deck, LinkedList<Player> players) {
        this.deck = deck;
        this.players = players;

        for (Player player : players) {
            if (player.isDealer()) {
                this.dealer = player;
            }
        }

        players.remove(dealer); // Players list does not contain a dealer

        // TODO: leave the game, add a new player to the game, contive the game while
        // there is at least one player

        placeBets(); // All players except the dealer to place a bet
        drawInitialCards(); // All players draw cards
        playRound(); // Play a round

    }

    /**
     * Every player except the dealer places a bet
     */
    private void placeBets() {

        System.out.println("\n************************************ PLACE BETS ***********************************\n");

        for (Player player : players) {
            System.out.println(player.getName() + " has " + player.getChips() + " chips.");
            if (player.getChips() > 0) {
                System.out.println(player.getName() + ", please place a bet:");
                int bet = scanner.nextInt();

                while (player.getChips() < bet) {
                    System.out.println("Place a lower bet. You currently have " + player.getChips() + " chips. "
                            + player.getName() + ", please place a bet:");
                    bet = scanner.nextInt();
                }
                System.out.println(); // Print a new line
                player.setCurrentBet(bet);
            } else { // If the player doesn't have any chips to play, the are removed from the game
                System.out.println(
                        player.getName() + " doesn't have enough chips to play. The player is removed from the game.");
                players.remove(player);
            }

        }
    }

    /**
     * Every player gets 2 cards
     */
    private void drawInitialCards() {
        // First card
        System.out.println("\n******************************* DRAW THE FIRST CARD *******************************\n");
        for (Player player : players) {

            playerHit(player);

        }

        playerHit(dealer); // Dealer draws the card last

        // Second card
        System.out.println("\n******************************* DRAW THE SECOND CARD ******************************\n");

        for (Player player : players) {
            playerHit(player);
        }

        dealer.hit(addCard());
        System.out.println(dealer.dealerHandToString()); // Dealer keeps the second card hidden
    }

    private void playRound() {

        System.out.println("\n*********************************** PLAY A ROUND **********************************\n");

        for (Player player : players) {
            if (!player.isBust()) { // Check if the player is still in the round
                askInput(player); // Allow every player to perform action
            }
        }
        
        System.out.println("\n********************************* CHECK THE DEALER ********************************\n");
        
        // Check if there are still players in the game
        Boolean playerInTheGame = false;
        for (Player player : players) {
            if (!player.isBust()){
                playerInTheGame = true;
                break;
            }
        }
        
        if (playerInTheGame) {
            // When all players stand, check dealer's cards
            System.out.println("Dealers hand:\n\n" + dealer.handToString());
    
            while (dealer.getTotalPoints() < dealer.getMinDealerPoints()) { // If dealer has less than 16 points, they draw
                                                                            // a card
                System.out.println("The dealer " + dealer.getName() + " draws an additional card.");
                playerHit(dealer);
            }
    
            System.out.println("\n********************************* CALCULATE RESULTS ********************************\n");
    
            if (dealer.getTotalPoints() > Player.maxPoints) { // Dealer bust
                System.out.println("The dealer is busted. All players in the round get double their bet.");
                for (Player player : players) {
                    if (!player.isBust()) {
                        int wonChips = player.getCurrentBet() * 2;
                        player.updateChips(wonChips); // Every player that's still in the round wins double their bet
                        System.out.println(player.getName() + " won " + wonChips + " chips. The current chip total is: "
                                + player.getChips());
                    }
                }
            } else { // Checks who won the round
                System.out.println("The dealer got " + dealer.getTotalPoints()
                        + " points. All players that scored more than a dealer win double their bet.");
                Boolean anyoneWon = false;
                for (Player player : players) {
                    if (!player.isBust() && player.getTotalPoints() > dealer.getTotalPoints()) { // Only players who's hands
                                                                                                 // are higher than dealer's
                                                                                                 // win twice their bet
                        anyoneWon = true;
                        int wonChips = player.getCurrentBet() * 2;
                        player.updateChips(player.getCurrentBet() * 2); // Every player that's still in the round wins
                                                                        // double their bet
                        System.out.println(player.getName() + " won " + wonChips + " chips. The current chip total is: "
                                + player.getChips());
                    } else {
                        // The bet was lower then the dealer's bet. The player looses the bet to the dealer.
                        player.updateChips(-player.getCurrentBet());
                        dealer.updateChips(player.getCurrentBet());
                    }
                }
                if (!anyoneWon) {
                    System.out.println(
                            "The dealer won the round. None of the players left in the round won any additional chips.");
                }
            }
        } else {
            System.out.println("All players in the round are busted.");
        }


        System.out.println("\n********************************** END OF THE ROUND ********************************\n");

        System.out.println("After the round:");
        for (Player player : players) {
            if (!player.isDealer()) {
                System.out.println(player.getName() + " has " + player.getChips() + " chips.");
            }
        }

        // Reset the current bet
        for (Player player : players) {
            player.setCurrentBet(0);
        }
    }

    /**
     * Aks user input to 'hit' or 'stand'
     * Continuously asks user input until the 'stand' action is selected
     * 
     * @param player performing an action
     */
    public void askInput(Player player) {

        while (true) {

            System.out.println(player.getName() + ", please enter an action: (hit/stand/hand) ");
            String action = scanner.nextLine();

            if (action.toLowerCase().equals("hit")) {
                playerHit(player);

                if (player.getTotalPoints() == Player.maxPoints) {

                    // Win 1.5 times the current bet from the dealer. Chips are rounded to the
                    // player's advantage.
                    int wonChips = (int) Math.round(player.getCurrentBet() + player.getCurrentBet() * 1.5);
                    player.updateChips(wonChips);

                    // The player is automatically done for the round
                    player.setBust(true);
                    System.out.println(player.getName() + " scored 21 points and won " + wonChips
                            + " chips (1.5 times their bet rounded to the player's advantage). The current chip total is: "
                            + player.getChips() + "\n");
                    return;
                }

                // Check if bust
                if (checkIfPlayerBust(player)) {
                    return;
                }
            } else if (action.toLowerCase().equals("stand")) {
                System.out.println(); // Print a new line
                return;
            } else if (action.toLowerCase().equals("hand")) {
                System.out.println("\n" + player.handToString());
            } else {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    public void playerHit(Player player) {
        player.hit(addCard());
        System.out.println("\n" + player.handToString());
    }

    public Boolean checkIfPlayerBust(Player player) {
        if (player.getTotalPoints() > Player.maxPoints) {
            player.setBust(true);
            player.updateChips(-player.getCurrentBet()); // Deduct the current bet from the total of player's chips
            dealer.updateChips(player.getCurrentBet()); // Dealer gets the current player's bet
            System.out.println(player.getName() + " is busted. They lost their bet to the dealer.\n");
            return true;
        }
        return false;
    }

    public Card addCard() {
        return this.deck.removeCard();
    }

}