package poker;
import java.util.Random;


public class ComputerPlayer extends PokerPlayer {

	private String behaviourType;
	private static final int NO_OF_NAMES = 20, NO_OF_BEHAVIOURS = 3;
	
	public ComputerPlayer(DeckOfCards deck) {
        super(deck);
        this.name = this.getRandomName();
        this.behaviourType = this.getRandomBehaviour();
        this.isHuman = false;
    }

    private String getRandomName() {
        int position = this.generateRandomNumber(ComputerPlayer.NO_OF_NAMES);
        String[] names = {"Brittanie", "Jim", "Lilli", "Daysi", "Elouise", "Horace", "Corazon", "Agustin", "Magan", "Vivan", "Retta", "Normand", "Rory", "Erick", "Antonio", "Gisele", "Parker", "Marla", "Cassie", "Aurelia"};
        return names[position];
    }
    
    private String getRandomBehaviour() {
    	int position = this.generateRandomNumber(ComputerPlayer.NO_OF_BEHAVIOURS);
    	String [] behaviours = {"risky", "normal", "no risk"};
    	return behaviours[position];
    }
    
    private int generateRandomNumber(int range) {
    	Random rand = new Random();
    	int number = rand.nextInt(range);
    	return number;
    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        ComputerPlayer p1 = new ComputerPlayer(deck);
        System.out.println(p1.getName());
        if(p1.behaviourType == "risky") 
        	System.out.println("risky");
        else if(p1.behaviourType == "normal")
        	System.out.println("normal");
        else if(p1.behaviourType == "no risk") {
        	System.out.println("not risky");
        }
        
    }
}
