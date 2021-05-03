package kalah.Interface;

import java.util.List;

public interface ITeam {

    IHouse getHouse();

    IStore getStore(int storeNumber);

    List<IStore> getStores();

    int getScore();

    int getTeamNumber();

    /**
     * true means they take another turn
     * @param starting
     * @param seeds
     * @param player
     * @return
     */
    boolean inputStart(Integer starting,int seeds, int player);

    boolean input(int seeds, int player);

    boolean again();

    void addNext(ITeam nextTeam);
}
