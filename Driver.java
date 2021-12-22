import java.util.LinkedList;
import java.util.Scanner;

public class Driver {
    
    public static void main(String[] args) {

        LinkedList<Player> players = new LinkedList<Player>();  // Initialize a list of players
        Deck deck = new Deck();                                 // Create a new deck
        
        try (Scanner scanner = new Scanner(System.in)) {
            // System.out.println("Enter the name of the Dealer:");
            // players.add(new Player(scanner.nextLine(), true));      // Create a dealer

            // String message = "Enter the name of a player. If all players have been entered, type 'stop':";
            // System.out.println(message);
            // String input = scanner.nextLine();
            
            // while (!input.equals("stop")) {
            //     players.add(new Player(input));
            //     System.out.println(message);
            //     input = scanner.nextLine();
            // }

            Player player1 = new Player("John");
            Player player2 = new Player("Tom");
            // Player player3 = new Player("Alex");
            Player player4 = new Player("Sue", true);

            players.add(player4);
            players.add(player1);
            players.add(player2);
            // players.add(player3);

            Game play = new Game(deck, players);

        } catch (Exception e) {
            System.out.println("Something went wrong with user input. Exception message: \n" + e);
        }
    }

}
