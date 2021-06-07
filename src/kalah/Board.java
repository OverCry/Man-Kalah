package kalah;

import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<ITeam> _teams = new ArrayList<>();

    public Board (int players,int stalls,int startingSeeds){
        for (int i =1;i<=players;i++){
            _teams.add(new Team(stalls,startingSeeds,i,players));
        }
        //link each team to the next person
        for (ITeam team: _teams){
            team.addNext(_teams.get((team.getTeamNumber()%players)));
        }
    }

    public List<ITeam> getTeams(){
        return _teams;
    }
}
