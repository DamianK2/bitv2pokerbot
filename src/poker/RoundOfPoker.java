package poker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RoundOfPoker {

    private int currentBet;
    private ArrayList<PokerPlayer> players;

    public RoundOfPoker(ArrayList<PokerPlayer> players, DeckOfCards deck)
    {
        this.players = players;
        this.currentBet = 0;
    }

    public void play()
    {

        // START ROUND
        System.out.println("New Deal:");

        for (PokerPlayer player : this.players)
            System.out.println("> " + player.getName() + " has " + player.getCoinsBalance() + " coins in the bank");

        // CHECK IF ANY PLAYER CAN OPEN
        boolean canOpen = false;
        for (PokerPlayer player : this.players)
            if (player.canOpenBet()) {
                System.out.println("> " + player.getName() + " says: I can open");
                canOpen = true;
            }
            else
                System.out.println("> " + player.getName() + " says: I cannot open");

        if (!canOpen) {
            System.out.println("Sorry, we cannot open the game.");
            return;
        }

        System.out.println("You have been dealt the following hand:");
        // PRINT THE TYPE OF HAND THAT HUMAN PLAYER OWNS
        for (PokerPlayer player : this.players)
            if (player.isHuman())
                System.out.println(player.getHand());

        // DISCARD
        for (PokerPlayer player : this.players) {
            if (player.isHuman()) {
                System.out.println(">> Which card(s) would you like to discard (e.g., 1,3): ");
                player.askDiscard();
            }
        }

        // PRINT THE TYPE OF HAND THAT HUMAN PLAYER OWNS
        for (PokerPlayer player : this.players)
            if (player.isHuman()) {
                System.out.println("Your hand now looks like:");
                System.out.println(player.getHand());
            }

        // ASK TO FOLD
        boolean[] fold = new boolean[this.players.size()];
        boolean allFold = false;
        for (int i = 0; i < players.size(); i++) {

            if (players.get(i).isHuman()) {
                System.out.println(">> Would you like to fold (y/n)? ");
            }
            fold[i] = players.get(i).askFold(this.currentBet);
            if(!fold[i])
                allFold = true;
        }
        // SHOW DISCARDING STATS
        for (PokerPlayer player : this.players)
            if (!player.isHuman())
                System.out.println(player.getName() + " discards " + player.discard() + " card(s)");

        System.out.println("");

        // IF EVERY PLAYER FOLD THEN EXIT ROUND OF POKER
        if (!allFold) {
            System.out.println("sorry, all players fold in the round.");
            return;
        }


        // BETTING
        int checkOpen = 0, bettingRound = 0, checkActivePlayer = 0, roundCounter = 0, size = 0, checkRound = 0;
        boolean round = true, openingBetting = true, human = true, firstOpen = false;

        while (round) {
            for (int i = 0; i < this.players.size(); i++) {
                // CHECK FOR PLAYERS THAT DIDN'T FOLD
                if (!fold[i]) {
                    // IF THIS IS THE FIRST TIME OPENING THE BET
                    if(openingBetting) {
                        // CHECK IF THE PLAYER IS A HUMAN AND OPEN THE BET
                        if(players.get(i).isHuman()){
                            // CHECK IF THE HUMAN PLAYER IS THE FIRST PLAYER TO OPEN
                            if (players.get(i).canOpenBet() && !fold[i] && human && checkOpen == 0) {
                                System.out.println("Would you like to open bet (y/n)? ");
                               firstOpen = players.get(i).askOpenBet(this.currentBet);
                               if(firstOpen){
                                   this.currentBet = 1;
                                   players.get(i).updateCoinsBalance(-this.currentBet);
                                   players.get(i).updateTableCoins(this.currentBet);
                                   human = false;
                               }
                            }
                        }
                        // CHECK IF THE COMPUTER PLAYER IS THE FIRST PLAYER TO OPEN
                        else if(players.get(i).canOpenBet() && !players.get(i).isHuman()){
                            firstOpen = players.get(i).askOpenBet(this.currentBet);
                            if(firstOpen){
                                this.currentBet = 1;
                                players.get(i).updateCoinsBalance(-this.currentBet);
                                players.get(i).updateTableCoins(this.currentBet);
                            }
                        }
                    }

                    // CHECK IF THIS IS THE FIRST TIME OF OPENING AND PRINT THE OPENING STATEMENT
                    if (checkOpen == 0 && firstOpen) {
                        System.out.println(players.get(i).getName() + " says: I open with " + this.currentBet + " chip!");
                        size = i;
                        checkOpen = 1;
                    }
                    // CHECK IF THE PLAYERS HAS ALREADY OPEN
                    else if (checkOpen > 0 ) {

                        // CHECK IF THE PLAYER IS A HUMAN AND ASK THE PLAYER TO RAISE THE BET
                        if (players.get(i).isHuman() && !fold[i]) {
                            if(checkActive(fold) == 1)
                                break;

                            printSeenStatement(size, i);

                            System.out.println("Would you like to raise (y/n)? ");
                            boolean checkHuman = players.get(i).askRaiseBet(this.currentBet);

                            // IF THE PLAYER SAID YES THEN RAISE BET
                            if (checkHuman) {
                                players.get(i).updateCoinsBalance(-this.currentBet);
                                players.get(i).updateTableCoins(this.currentBet);
                                printRaiseStatement(i);
                                size = i;
                            }
                            // CHECK IF THE PLAYER DIDN'T RAISE THE BET AND THE BETTING ISN'T THE OPENING BET THEN FOLD
                            else if (!checkHuman ) {
                               //System.out.println("Would you like to fold (y/n)? ");
                                fold[i] = true;
                                System.out.println(players.get(i).getName() + " says: I fold ");
                            }
                        }
                        // CHECK IF THE PLAYER IS A COMPUTER PLAYER AND ASK THE PLAYER TO RAISE THE BET
                        else {
                            boolean checkComputer = players.get(i).askRaiseBet(this.currentBet);
                            // IF THE PLAYER COIN BALANCE IS ZERO REMOVE THE PLAYER FROM THE GAME
                            if(checkActive(fold) == 1)
                                break;;

                            // IF THE PLAYER SAID YES THEN RAISE BET
                            if (checkComputer ) {
                                players.get(i).updateCoinsBalance(-this.currentBet);
                                players.get(i).updateTableCoins(this.currentBet);
                                if(checkActive(fold) == 1)
                                    break;

                                printSeenStatement(size, i);
                                printRaiseStatement(i);
                                size = i;
                            }
                            // CHECK IF THE PLAYER DIDN'T RAISE THE BET AND THE BETTING ISN'T THE OPENING BET THEN FOLD
                            else if (!checkComputer) {
                                fold[i] = true;
                                System.out.println(players.get(i).getName() + " says: I  fold ");
                            }
                        }
                    }
                }
            }
            System.out.println("");
            // UPDATE VARIABLES VALUES
            openingBetting = false;
            bettingRound = 1;
            checkRound = 1;
            roundCounter++;


            // CHECK IF THE GAME ROUND OF POKER IS FINNISH
            if(roundCounter == 2){
                round = false;
            }
            //CHECK IF THER'S ONLY ONE PLAYER LEFT IN THE GAME
            else if(checkActive(fold) == 1){
                round = false;
            }

        }
        // DISPLAY PLAYERS HAND AND ANNOUNCE WINNER OF THE POKER ROUND
        winner(fold);

    }

    // A METHOD THAT CHECK THE VALUE OF THE POT IN THE CURRENT ROUND OF POKER
    public  int currentTableBetAmount(){
        int pot = 0;
        for (PokerPlayer player : this.players)
            pot += player.updatePlayerPot();

        return pot;
    }

    // A METHOD THAT CHECKS WHICH PLAYER IS THE WINNER AND DISPLAY PLAYERS HAND
    public void winner(boolean fold[]){
        // CHECK FOR WINNER
        int winnings = currentTableBetAmount();
        int winnerPos = 0, cardGameValue = 0;
        for(int i = 0; i < players.size(); i++){
            if(i ==  0 ) {
                players.get(i).updateTableCoins(-this.currentBet);
                System.out.println(players.get(i).getName() + " goes first");
                System.out.println(players.get(i).getHand());
                if(players.get(i).getHandValue() > cardGameValue && !fold[i]) {
                    cardGameValue = players.get(i).getHandValue();
                    winnerPos = i;
                }
            }
            else{
                if(players.get(i).getHandValue() > cardGameValue && !fold[i]){
                    players.get(i).updateTableCoins(-this.currentBet);
                    System.out.println(players.get(i).getName() + " says 'read them and weep'");
                    System.out.println(players.get(i).getHand());
                    cardGameValue = players.get(i).getHandValue();
                    winnerPos = i;
                }
                else{
                    players.get(i).updateTableCoins(-this.currentBet);
                    System.out.println(players.get(i).getName() + " says 'read them and weep'");
                    System.out.println(players.get(i).getHand());
                }

            }
        }

        // PRINT WINNER
        if(winnings > 0) {
            System.out.println(players.get(winnerPos).getName() + " say: I WIN  " + winnings + " chip");
            System.out.println(players.get(winnerPos).getHand());
            players.get(winnerPos).updateCoinsBalance(winnings);
            System.out.println(players.get(winnerPos).getName() + " has " +
                    players.get(winnerPos).getCoinsBalance() + " chip(s) in the bank");
        }
        else
            System.out.println("No winner because none of the players can open the bet");
    }

    // A METHOD THAT PRINT SEE STATEMENT IN THE GAME
    public void printSeenStatement(int size, int i){
        if(players.get(size).updatePlayerPot() == 0)
            System.out.println(players.get(i).getName() + " says: I see that " + players.get(size -1).updatePlayerPot() + " chip!");
        else
            System.out.println(players.get(i).getName() + " says: I see that " + players.get(size).updatePlayerPot() + " chip!");
    }

    // A METHOD THAT PRINT THE RAISE STATEMENT IN THE GAME
    public void printRaiseStatement(int i){
        System.out.println(players.get(i).getName() + " says: I raise " + players.get(i).updatePlayerPot() + " chip!");
    }

    // CHECK THE NUMBER OF PLAYER STILL IN THE GAME
    public int checkActive(boolean fold[]){
        int checkActivePlayer = 0;
        for(int k = 0; k < players.size(); k++){
            if(!fold[k]){
                checkActivePlayer++;
            }
        }
        return  checkActivePlayer;
    }


    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Automated Poker Machine ...");
        System.out.print("What is your name? ");
        String name = input.nextLine();
        System.out.println("Let's play POKER ...");

        // MAKE HUMAN PLAYER, PASS A NAME
        HumanPlayer humanPlayer = new HumanPlayer(deck, name);
        ComputerPlayer p1 = new ComputerPlayer(deck);
        ComputerPlayer p2 = new ComputerPlayer(deck);
        ComputerPlayer p3 = new ComputerPlayer(deck);
        ComputerPlayer p4 = new ComputerPlayer(deck);
        ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
        players.add(humanPlayer);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
       // Collections.shuffle(players);
        RoundOfPoker round = new RoundOfPoker(players, deck);

        boolean poker = true;
        while(poker && players.contains(humanPlayer)){
            round.play();
            System.out.println("Would like to play another round of poker (y/n)");
            name = input.nextLine();
            if(name == "y")
                poker = false;

            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getCoinsBalance() == 0)
                    players.remove(i);
            }
            deck.reset();
            for(PokerPlayer player : players)
              player.resetHand();
        }

        //System.out.println(round.players.get(0).name);



    }
}

