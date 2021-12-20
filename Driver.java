import java.util.LinkedList;

public class Driver {
    
    public static void main(String[] args) {

        LinkedList<Player> players = new LinkedList<Player>();

        Deck deck = new Deck();
        //System.out.println(deck.printToString());

        Player player1 = new Player("John");
        Player player2 = new Player("Tom");
        Player player3 = new Player("Alex");
        Player player4 = new Player("Sue", true);

        players.add(player4);
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Game play = new Game(deck, players);

    }

}
