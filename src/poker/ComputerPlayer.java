package poker;
import java.util.Random;


public class ComputerPlayer extends PokerPlayer {

    public ComputerPlayer(DeckOfCards deck)
    {
        super(deck);
        this.name = this.getRandomName();
    }

    private String getRandomName()
    {
        Random rand = new Random();
        int n = rand.nextInt(20);
        String[] names = {"Brittanie", "Jim", "Lilli", "Daysi", "Elouise", "Horace", "Corazon", "Agustin", "Magan", "Vivan", "Retta", "Normand", "Rory", "Erick", "Antonio", "Gisele", "Parker", "Marla", "Cassie", "Aurelia"};

        return names[n];
    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        ComputerPlayer p1 = new ComputerPlayer(deck);
        System.out.print(p1.getName());
    }
}
