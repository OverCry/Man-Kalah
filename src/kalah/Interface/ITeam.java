package kalah.Interface;

import java.util.List;

public interface ITeam {

    /**
     * get the house of the team
     * @return
     */
    IHouse getHouse();

    /**
     * get the specified store
     * @param storeNumber
     * @return the store wanted
     */
    IStore getStore(int storeNumber);

    /**
     * get all stores from this team
     * @return
     */
    List<IStore> getStores();

    /**
     * get the score of this team
     * @return
     */
    int getScore();

    /**
     * get the team number
     * @return
     */
    int getTeamNumber();

    /**
     * move seeds starting from this position
     * @param starting
     * @param seeds
     * @param player
     * @return
     */
    boolean moveAtMid(Integer starting, int seeds, int player);

    /**
     * move seeds starting at the beginning
     * @param seeds
     * @param player
     * @return
     */
    boolean move(int seeds, int player);

    /**
     * link to the team after this team
     * @param nextTeam
     */
    void addNext(ITeam nextTeam);
}
