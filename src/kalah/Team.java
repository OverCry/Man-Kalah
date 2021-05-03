package kalah;

import kalah.Interface.IHouse;
import kalah.Interface.IStore;
import kalah.Interface.ITeam;

import java.util.ArrayList;
import java.util.List;

public class Team implements ITeam {
    private List<IStore> _stores = new ArrayList<>();
    private IHouse _house = new House();
    private int _teamNum;
    private boolean _again=false;
    private ITeam _nextTeam;
    private int _numTeams;

    public Team(int stalls,int startingSeeds, int number, int teams){
        for (int i = 1; i <= stalls; i++) {
            _stores.add(new Store(i,startingSeeds));
        }
        _teamNum = number;
        _numTeams = teams;
    }

    @Override
    public IHouse getHouse() {
        return _house;
    }

    @Override
    public IStore getStore(int storeNumber) {
        return _stores.get(storeNumber-1);
    }

    @Override
    public List<IStore> getStores() {
        return _stores;
    }

    @Override
    public int getScore() {
        int res = _house.getAmount();
        for (IStore s: _stores){
            res+=s.getAmount();
        }
        return res;
    }

    @Override
    public int getTeamNumber() {
        return _teamNum;
    }

    @Override
    public boolean inputStart(Integer starting,int seeds, int player) {
        for (IStore store: _stores){
            if (store.getNumber()>starting){
                store.addAmount(1);
                seeds--;
                if (seeds == 0){
                    //input logic for checking if 'stealling
                    if (player == _teamNum){
                        //check if 'next' neighbour has any seeds
//                        int oppositeAmount = _nextTeam.getStore(_stores.size()-store.getNumber()+1).takeAll();
//                        if (oppositeAmount != 0){
//                            _house.addAmount(oppositeAmount+store.takeAll());
//                        }
                    }
                    return false;
                }
            }
        }
        //add one to yourself if there is still any
        _house.addAmount(1);
        seeds--;
        if (seeds == 0){
            return true;
        }
        //otherwise
        return _nextTeam.input(seeds,player);
    }

    @Override
    public boolean input(int seeds, int player) {
        for (IStore store: _stores){
            store.addAmount(1);
            seeds--;
            if (seeds == 0){
                //input logic for checking if 'stealling
                if (player == _teamNum){
                    //check if 'next' neighbour has any seeds
//                    int oppositeAmount = _nextTeam.getStore(_stores.size()-store.getNumber()+1).takeAll();
//                    if (oppositeAmount != 0){
//                        _house.addAmount(oppositeAmount+store.takeAll());
//                    }
                }
                break;
            }
        }
        if (player==_teamNum){
            _house.addAmount(1);
            seeds--;
            if (seeds == 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean again() {
        if (_again){
            _again=false;
            return true;
        }
        return false;
    }

    @Override
    public void addNext(ITeam nextTeam) {
        _nextTeam=nextTeam;
    }
}
