package kalah.Singleton;

import com.qualitascorpus.testsupport.IO;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Printer{
    public static Printer _instance = null;

    /**
     * final values for players
     * very temporary as this only works for two players
     */
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

        printBars(printer,stalls);

        Collections.reverse(teams);
        for (ITeam team : teams){
            if (team.getTeamNumber()%2==0){
                printer.print("| P"+team.getTeamNumber()+" ");
                Collections.reverse(team.getStores());
                for (IStore store : team.getStores()) {
                    printer.print("|" + printNumber(store.getNumber()) + "["  + printNumber(store.getAmount()) + "] ");
                }
                printer.println("| " + printNumber(team.getNextTeam().getHouse().getAmount()) + " |");

                Collections.reverse(team.getStores());
                printSeparater(printer, stalls);
            } else {
                printer.print("| " + printNumber(team.getNextTeam().getHouse().getAmount()) + " ");
                for (IStore store : team.getStores()){
                    printer.print("|" + printNumber(store.getNumber()) + "[" +  printNumber(store.getAmount())  + "] ");
                }
                printer.println("| P"+team.getTeamNumber()+" |");
                printBars(printer,stalls);

            }
        }
        Collections.reverse(teams);
    }

    private void printSeparater(IO printer, int stalls){
        printer.print("|    |");
        for (int i=0;i<stalls-1;i++){
            printer.print("-------+");
        }
        printer.println("-------|    |");
    }

    private void printBars(IO printer, int stalls){
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
