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
    private ITeam _nextTeam;
    private int _numTeams;
    private int _addSead = 1;

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
    public boolean moveAtMid(Integer starting, int seeds, int player) {
        for (IStore store: _stores){
            if (store.getNumber()>starting){
                seeds = remainingSeeds(seeds, player, store);
                if (seeds==0){
                    return false;
                }
            }
        }
        //add one to yourself as there are still seed(s)
        _house.addAmount(_addSead);
        seeds-=_addSead;
        if (seeds == 0){
            return true;
        }
        //otherwise
        return _nextTeam.move(seeds,player);
    }

    @Override
    public boolean move(int seeds, int player) {
        for (IStore store: _stores){
            seeds = remainingSeeds(seeds, player, store);
            if (seeds==0){
                return false;
            }
        }
        if (player==_teamNum){
            _house.addAmount(_addSead);
            seeds-=_addSead;
            if (seeds == 0){
                return true;
            }
        }
        return _nextTeam.move(seeds,player);
    }

    /**
     * add a certain amount of seeds
     * returns the remaining amount of seeds
     * @param seeds
     * @param player
     * @param store
     * @return
     */
    private int remainingSeeds(int seeds, int player, IStore store) {
        store.addAmount(_addSead);
        seeds -=_addSead;
        if (seeds == 0){
            //input logic for checking if 'stealling
            if (player == _teamNum && store.getAmount()==_addSead){
                //check if 'next' neighbour has any seeds
                int oppositeAmount = _nextTeam.getStore(_stores.size()-store.getNumber()+1).takeAll();
                if (oppositeAmount != 0){
                    _house.addAmount(oppositeAmount+store.takeAll());
                }
            }
            return 0;
        }
        return seeds;
    }

    @Override
    public void addNext(ITeam nextTeam) {
        _nextTeam=nextTeam;
    }

    @Override
    public ITeam getNextTeam() {
        return _nextTeam;
    }
}
