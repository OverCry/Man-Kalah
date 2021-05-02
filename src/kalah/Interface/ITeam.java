package kalah.Interface;

import java.util.List;

public interface ITeam {

    IHouse getHouse();

    IStore getStore(int storeNumber);

    List<IStore> getStores();

    int getScore();

}
