import java.util.LinkedList;
import java.util.Scanner;

public class Game {

    private Deck deck;
    private LinkedList<Player> players; // Keeps a list of players without a dealer)
    private Player dealer;
    private int playerID;

    public Game() throws InterruptedException {

        this.players = new LinkedList<Player>();
        this.deck = new Deck();

        this.playerID = 1;
        addADealer();
        addPlayers();

        // if no players have been added, end the game instantly
        if (players.size() < 1) {
            endTheGame();
        }

        Boolean continuePlaying = true;

        // Continue playing the game while the user wants, and while there are still
        // players left
        do {
            placeBets(); // All players except the dealer to place a bet
            drawInitialCards(); // All players draw cards
            playRound(); // Play a round

            System.out.println("Do you want to play another round? (y/n)");
            continuePlaying = System.console().readLine().toLowerCase().equals("y") ? true : false;

            if (continuePlaying) {

                System.out.println(
                        "Do you want to remove any existing players from the next round (the dealer cannot be removed)? (y/n)");
                while (System.console().readLine().toLowerCase().equals("y")) {
                    System.out.println("Please enter the Player ID: ");

                    String input = System.console().readLine();
                    while (!checkIfDigit(input)) {
                        System.out.println("Please enter a valid Player ID (integer format): ");
                        input = System.console().readLine();
                    }

                    int playerIDToRemove = Integer.parseInt(input);
                    Player playerToRemove = players.stream().filter(player -> playerIDToRemove == player.getId())
                            .findFirst().orElse(null); // Finds the player to remove by Player ID
                    players.remove(playerToRemove);
                    System.out.println(
                            "Do you want to remove any existing players from the next round (the dealer cannot be removed)? (y/n)");
                }

                System.out.println(
                        "Do you want to add any new players for the next round (the dealer cannot change)? (y/n)");
                if (System.console().readLine().toLowerCase().equals("y")) {
                    addPlayers();
                }

                // Reset values
                this.deck = new Deck();
                for (Player player : players) {

                    // Resets values for the next game
                    player.setBust(false);
                    player.setHand(new LinkedList<Card>());
                    dealer.setBust(false);
                    dealer.setHand(new LinkedList<Card>());
                }
            } else {
                endTheGame();
            }

            // Checks if the player has enough chips to continue the playing the game
            for (Player player : players) {
                if (player.getChips() < 1) {
                    players.remove(player);
                }
            }

        } while (continuePlaying && players.size() > 0); // Continue the loop while there are still players left

        endTheGame();
    }

    private void endTheGame() {
        System.out.println("Thank you for the game!");
        System.exit(1);
    }

    // todo: ADD UNIQUE PLAYERS
    private void addPlayers() {
        String message = "Enter the name of a player. If all players have been entered, type 'stop':";
        System.out.println(message);
        String input = System.console().readLine();

        while (!input.equals("stop")) {
            this.players.add(new Player(input, playerID));
            System.out.println("Player ID: " + playerID);
            this.playerID++; // Create unique IDs
            System.out.println(message);
            input = System.console().readLine();
        }
    }

    private void addADealer() {
        System.out.println("Enter the name of the Dealer:");
        this.dealer = new Player(System.console().readLine(), true);
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

                String input = System.console().readLine();
                while (!checkIfDigit(input)) {
                    System.out.println(player.getName() + ", please place a valid bet (integer format):");
                    input = System.console().readLine();
                }

                int bet = Integer.parseInt(input);

                while (player.getChips() < bet) {
                    System.out.println("Place a lower bet. You currently have " + player.getChips() + " chips. "
                            + player.getName() + ", please place a bet:");

                    input = System.console().readLine();
                    while (!checkIfDigit(input)) {
                        System.out.println(player.getName() + ", please place a valid bet (integer format):");
                        input = System.console().readLine();
                    }

                    bet = Integer.parseInt(input);

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

    private Boolean checkIfDigit(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Every player gets 2 cards
     * 
     * @throws InterruptedException
     */
    private void drawInitialCards() throws InterruptedException {
        // First card
        System.out.println("\n******************************* DRAW THE FIRST CARD *******************************\n");
        for (Player player : players) {
            playerHit(player);
            consoleWait();
        }

        playerHit(dealer); // Dealer draws the card last
        consoleWait();

        // Second card
        System.out.println("\n******************************* DRAW THE SECOND CARD ******************************\n");

        for (Player player : players) {
            playerHit(player);
            consoleWait();
        }

        dealer.hit(addCard());
        System.out.println(dealer.dealerHandToString()); // Dealer keeps the second card hidden
        consoleWait();

        for (Player player : players) {
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
            }
        }
    }

    private void playRound() throws InterruptedException {

        System.out.println("\n*********************************** PLAY A ROUND **********************************\n");

        for (Player player : players) {
            if (!player.isBust()) { // Check if the player is still in the round
                askInput(player); // Allow every player to perform action
            }
        }

        consoleWait();
        System.out.println("\n********************************* CHECK THE DEALER ********************************\n");

        // Check if there are still players in the game
        Boolean playerInTheGame = false;
        for (Player player : players) {
            if (!player.isBust()) {
                playerInTheGame = true;
                break;
            }
        }

        if (playerInTheGame) {
            // When all players stand, check dealer's cards
            System.out.println("Dealers hand:\n\n" + dealer.handToString());

            while (dealer.getTotalPoints() < dealer.getMinDealerPoints()) { // If dealer has less than 16 points, they
                                                                            // draw
                                                                            // a card
                System.out.println("The dealer " + dealer.getName() + " draws an additional card.");
                playerHit(dealer);
            }

            System.out.println(
                    "\n********************************* CALCULATE RESULTS ********************************\n");

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
                    if (!player.isBust() && player.getTotalPoints() > dealer.getTotalPoints()) { // Only players who's
                                                                                                 // hands
                                                                                                 // are higher than
                                                                                                 // dealer's
                                                                                                 // win twice their bet
                        anyoneWon = true;
                        int wonChips = player.getCurrentBet() * 2;
                        player.updateChips(player.getCurrentBet() * 2); // Every player that's still in the round wins
                                                                        // double their bet
                        System.out.println(player.getName() + " won " + wonChips + " chips. The current chip total is: "
                                + player.getChips());
                    } else {
                        // The bet was lower then the dealer's bet. The player looses the bet to the
                        // dealer.
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
            String action = System.console().readLine();

            if (action.toLowerCase().equals("hit")) {
                playerHit(player);

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

    public void consoleWait() throws InterruptedException {
        Thread.sleep(0);
    }
}