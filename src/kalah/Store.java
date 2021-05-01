package kalah;

import kalah.Interface.IStore;

public class Store implements IStore {
    int _amount = 4;

    public Store(){

    }

    public int getAmount() {
        return _amount;
    }

//    public String getSAmount() {
//        return Integer.toString(_amount);
//    }

    public void addOne() {
        _amount++;
    }

}
