import java.util.LinkedList;

public class Game {

    private Deck deck; // The game deck
    private LinkedList<Player> players; // The list of players
    private Player dealer; // The game dealer
    private int playerID; // ID counter to generate unique IDs for new players

    /**
     * Initializes a new game
     * The game can contain multiple rounds while players want to play
     * and there is at least one player with enough chips
     * 
     * @throws InterruptedException
     */
    public Game() throws InterruptedException {

        this.deck = new Deck(); // Create a new deck

        this.players = new LinkedList<Player>();
        this.playerID = 1; // Initialize the ID count
        addADealer(); // Add a dealer to the game
        addPlayers(); // Add players to the game

        // if no players have been added, end the game instantly
        if (players.size() < 1) {
            endTheGame();
        }

        Boolean continuePlaying = true;

        // Continue playing the game while players want to play and there is at least
        // one player with enough chips
        do {
            placeBets(); // All players place a bet
            drawInitialCards(); // All players and a dealer draw cards
            playRound(); // Play a round

            // Asks the user if they want to play another round
            System.out.println("Do you want to play another round? (y/n)");
            continuePlaying = System.console().readLine().toLowerCase().equals("y") ? true : false;

            if (continuePlaying) {

                // Asks the user if they want to remove any existing players
                System.out.println(
                        "Do you want to remove any existing players from the next round (the dealer cannot be removed)? (y/n)");
                while (System.console().readLine().toLowerCase().equals("y")) {
                    System.out.println("Please enter the Player ID: ");

                    // Asks the user to input a valid player ID
                    String input = System.console().readLine();
                    while (!checkIfDigit(input)) {
                        System.out.println("Please enter a valid Player ID (integer format): ");
                        input = System.console().readLine();
                    }
                    int playerIDToRemove = Integer.parseInt(input);

                    // Removes a player if exists
                    Player playerToRemove = players.stream().filter(player -> playerIDToRemove == player.getId())
                            .findFirst().orElse(null);
                    if (players.contains(playerToRemove)) {
                        players.remove(playerToRemove);
                    } else {
                        System.out.println("This player does not exit.");
                    }
                    System.out.println(
                            "Do you want to remove any existing players from the next round (the dealer cannot be removed)? (y/n)");
                }

                // Asks the user if they want to add new players for the next round
                System.out.println(
                        "Do you want to add any new players for the next round (the dealer cannot change)? (y/n)");

                // Adds new players
                if (System.console().readLine().toLowerCase().equals("y")) {
                    addPlayers();
                }

                // Resets values for the next game
                this.deck = new Deck(); // Generates a new deck
                for (Player player : players) {

                    player.setBust(false);
                    player.setHand(new LinkedList<Card>());
                    dealer.setBust(false);
                    dealer.setHand(new LinkedList<Card>());
                }
            } else {
                endTheGame(); // Ends the game
            }

            // Checks if the player has enough chips to continue the playing the game
            // If not, the player is removed
            LinkedList<Player> playersToRemove = new LinkedList<>();
            for (Player player : players) {
                System.out.println(player.getChips());
                if (player.getChips() < 1) {
                    playersToRemove.add(player);
                }
            }
            players.removeAll(playersToRemove);

        } while (continuePlaying && players.size() > 0); // Continue the loop while there is enough players for the game
        endTheGame();
    }

    /**
     * Ends the game and prints a message
     */
    private void endTheGame() {
        System.out.println("Thank you for the game!");
        System.exit(1);
    }

    /**
     * Add a new player to the game
     * The player starts with 5 chips
     * A unique ID is printed to the screen
     */
    private void addPlayers() {

        String message = "Enter the name of a player. If all players have been entered, type 'stop':";
        System.out.println(message);
        String input = System.console().readLine();

        // Add a new player until the stop is entered
        while (!input.equals("stop")) {
            this.players.add(new Player(input, playerID));
            System.out.println("Player ID: " + playerID); // Print the player ID to the screen
            this.playerID++; // Create unique IDs
            System.out.println(message); // Ask if another player wants to join the game
            input = System.console().readLine();
        }
    }

    /**
     * Add a dealer to teh game
     * The game has only one dealer who cannot be removed/changed
     */
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

            // Players with chips place a bet
            if (player.getChips() > 0) {
                Boolean askInput = true;

                String input = "";
                int bet;

                System.out.println(player.getName() + ", please place a bet:");
                input = System.console().readLine();

                // Ask a user to enter a valid bet
                while (askInput) {

                    if (checkIfDigit(input)) {
                        bet = Integer.parseInt(input);
                        if (bet >= 0) {
                            if (player.getChips() >= bet) {
                                askInput = false;
                            } else {
                                System.out.println(
                                        "Place a lower bet. You currently have " + player.getChips() + " chips. "
                                                + player.getName() + ", please place a bet:");

                                input = System.console().readLine();
                            }
                        } else {
                            System.out
                                    .println(player.getName() + ", please place a valid bet (greater or equal to 0):");
                            input = System.console().readLine();
                        }
                    } else {
                        System.out.println(player.getName() + ", please place a valid bet (integer format):");
                        input = System.console().readLine();
                    }
                }

            } else { // If the player doesn't have any chips to play, the are removed from the game
                System.out.println(
                        player.getName() + " doesn't have enough chips to play. The player is removed from the game.");
                players.remove(player);
            }

        }
    }

    /**
     * Check if a passed string is a number
     * 
     * @param string input
     * @return boolean if integer
     */
    private Boolean checkIfDigit(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * All players draw 2 cards.
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

        // Check if any players got 21 points
        for (Player player : players) {
            if (player.getTotalPoints() == Player.maxPoints) {

                // Win 1.5 times the current bet from the dealer. Chips are rounded to the
                // player's advantage
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

    /**
     * Players play a round
     * 
     * @throws InterruptedException
     */
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

            // If dealer has less than 16 points, they draw a card
            while (dealer.getTotalPoints() < Player.minDealerPoints) {
                System.out.println("The dealer " + dealer.getName() + " draws an additional card.");
                playerHit(dealer);
            }

            // Calculates results
            System.out.println(
                    "\n********************************* CALCULATE RESULTS ********************************\n");

            // Check if the dealer is busted
            if (dealer.getTotalPoints() > Player.maxPoints) {
                System.out.println("The dealer is busted. All players in the round get double their bet.");

                // Every player that's still in the round wins double their bet
                for (Player player : players) {
                    if (!player.isBust()) {
                        int wonChips = player.getCurrentBet() * 2;
                        player.updateChips(wonChips);
                        System.out.println(player.getName() + " won " + wonChips + " chips. The current chip total is: "
                                + player.getChips());
                    }
                }
            } else { // Checks who won the round
                System.out.println("The dealer got " + dealer.getTotalPoints()
                        + " points. All players that scored more than a dealer win double their bet.");
                Boolean anyoneWon = false;
                for (Player player : players) {

                    // Check for players still in the round
                    if (!player.isBust()) {

                        // Players with a hand higher than dealer's win twice their bet
                        if (player.getTotalPoints() > dealer.getTotalPoints()) {

                            anyoneWon = true;
                            int wonChips = player.getCurrentBet() * 2;
                            player.updateChips(player.getCurrentBet() * 2);
                            System.out.println(
                                    player.getName() + " won " + wonChips + " chips. The current chip total is: "
                                            + player.getChips());
                        } else {
                            // The bet was lower then the dealer's bet. The player looses the bet to the
                            // dealer.
                            player.updateChips(-player.getCurrentBet());
                            dealer.updateChips(player.getCurrentBet());
                        }
                    }
                }

                // Check if the dealer won the round
                if (!anyoneWon) {
                    System.out.println(
                            "The dealer won the round. None of the players left in the round won any additional chips.");
                }
            }
        } else {
            System.out.println("All players in the round are busted.");
        }

        // Finish the round
        System.out.println("\n********************************** END OF THE ROUND ********************************\n");

        // Print a message with the count of chips for every player
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

            // Ask the user to enter the action
            System.out.println(player.getName() + ", please enter an action: (hit/stand/hand) ");
            String action = System.console().readLine();

            // Perform an action
            if (action.toLowerCase().equals("hit")) {
                playerHit(player);

                // Check if bust
                if (checkIfPlayerBust(player)) {
                    return;
                }
            } else if (action.toLowerCase().equals("stand")) {
                System.out.println();
                return;
            } else if (action.toLowerCase().equals("hand")) {
                System.out.println("\n" + player.handToString());
            } else {
                System.out.println("Please enter a valid input.\n");
            }
        }
    }

    /**
     * Perform a hit
     * 
     * @param player that hits
     */
    public void playerHit(Player player) {
        player.hit(addCard());
        System.out.println("\n" + player.handToString());
    }

    /**
     * Check if the player is busted
     * 
     * @param player to check
     * @return boolean if busted
     */
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

    /**
     * Add the card to the hand
     * 
     * @return the card
     */
    public Card addCard() {
        return this.deck.removeCard();
    }

    /**
     * Console pause to simulate a real game
     * 
     * @throws InterruptedException
     */
    public void consoleWait() throws InterruptedException {
        Thread.sleep(1500);
    }
}