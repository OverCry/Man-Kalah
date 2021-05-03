package kalah.Singleton;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Printer{
    public static Printer _instance = null;
    final private int PLAYER_1 = 0;
    final private int PLAYER_2 = 1;

    public static Printer getInstance(){
        if (_instance==null) {
            _instance = new Printer();
        }
        return _instance;
    }

    /**
     * Helper function to printing out the output
     * Current formatting expects only two players
     */
    public void printState(IO printer, List<ITeam> teams, int stalls) {

        printer.print("+----");
        for (int i=0;i<stalls;i++){
            printer.print("+-------");
        }
        printer.println("+----+");

        // p2 | stalls for p2 | score of p1
        printer.print("| P2 ");
        List<IStore> stores2 = teams.get(PLAYER_2).getStores();
        Collections.reverse(stores2);
        for (IStore store : stores2) {
            printer.print("|" + printNumber(store.getNumber()) + "["  + printNumber(store.getAmount()) + "] ");
        }
        Collections.reverse(stores2);
        printer.println("| " + printNumber(teams.get(PLAYER_1).getHouse().getAmount()) + " |");

        printer.print("|    |");
        for (int i=0;i<stalls-1;i++){
            printer.print("-------+");
        }
        printer.println("-------|    |");

        // score of p2 | stalls for p1 | p2
        List<IStore> stores1 = teams.get(PLAYER_1).getStores();
        printer.print("| " + printNumber(teams.get(PLAYER_2).getHouse().getAmount()) + " ");
        for (IStore store : stores1){
            printer.print("|" + printNumber(store.getNumber()) + "[" +  printNumber(store.getAmount())  + "] ");
        }
        printer.println("| P1 |");

        printer.print("+----");
        for (int i=0;i<stalls;i++){
            printer.print("+-------");
        }
        printer.println("+----+");
    }

    /**
     * formats a number to the correct string format
     * ONLY intended for double digits
     * @param number
     * @return
     */
    public String printNumber(int number){
        if (number>9){
            return Integer.toString(number);
        }
        return " "+number;
    }

    /**
     * Print the result of the game when it has naturally finished
     */
    public void printResult(IO printer, List<ITeam> teams){
        List<String> winNumbers = new ArrayList<>();
        int highestScore = 0;
        for (ITeam team : teams){
            int score = team.getScore();
            printer.println("\tplayer "+team.getTeamNumber()+":"+score);

            if (score>highestScore){
                winNumbers = new ArrayList<>();
                winNumbers.add(team.getTeamNumber()+"");
                highestScore=score;
            } else if (score == highestScore){
                winNumbers.add(team.getTeamNumber()+"");
            }
        }

        if (winNumbers.size()>1){
            printer.println("A tie!");
        } else if (winNumbers.size()==1) {
            printer.println("Player " + winNumbers.get(0) + " wins!");
        }
    }

}
