package poker;

import java.util.Scanner;

/**
 * Created by Adam Freeman on 05/04/2017.
 */
public class Parser {
	
    Parser(){

    }

    public int convertResponse(String response){
        String checkYes = "Yes";
        String checkY = "Y";
        String checkNo =  "No";
        String checkN = "N";

        if(checkYes.equalsIgnoreCase(response) || checkY.equalsIgnoreCase(response) ){
            return  1;
        }
        else if(checkNo.equalsIgnoreCase(response) || checkN.equalsIgnoreCase(response)){
            return 0;
        }
        else
        	return -1;
    }
    
    public boolean checkAmountDiscards(String response) {
    	String [] strArray = response.split("\\s*(\\s|=>|,)\\s*");

        return strArray.length <= 3;
    }
    
    public boolean checkDiscardNumbers(String response) {
    	String [] strArray = response.split("\\s*(\\s|=>|,)\\s*");

        for (String aStrArray : strArray) {
            if (aStrArray.length() > 1)
                return false;
            else if (aStrArray.charAt(0) < 48 || aStrArray.charAt(0) > 52)
                return false;
        }
    	
    	return true;
    }
    
    public int[] convertDiscards(String response){
        String [] strArray = response.split("\\s*(\\s|=>|,)\\s*");
        int discard[] = new int[PokerPlayer.DISCARD_MAX];

        for (int i = 0; i < discard.length; i++){

            if(i < strArray.length)
                discard[i] = Integer.parseInt(strArray[i]);
            else
                discard[i] = -1;

        }
        return discard;
    }

    public static void main(String[] args){
        Parser parser = new Parser();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter discard answer Y/N");
        String response = input.nextLine();
        System.out.println(parser.convertResponse(response));

        System.out.println("Enter discard position");
        String discard = input.nextLine();
        int discards[] = parser.convertDiscards(discard);
        for (int discard1 : discards) System.out.println(discard1);





    }
}
