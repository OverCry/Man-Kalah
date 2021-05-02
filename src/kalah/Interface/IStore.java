package kalah.Interface;

public interface IStore {
    /**
     * get the store number
     * @return the store number
     */
    int getNumber();

    /**
     * get the number of seeds in the store
     * @return amount of seeds in the store
     */
    int getAmount();

    /**
     * add this amount of seeds
     * almost definitely 1 seed
     * @param amount of seeds added to this store
     */
    void addAmount(int amount);

    /**
     * takes all the seeds from this store
     * @return the amount of seeds original in the store
     */
    int takeAll();

}
