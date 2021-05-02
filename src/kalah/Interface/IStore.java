package kalah.Interface;

public interface IStore {
    /**
     * get the store number
     * @return
     */
    public int getNumber();

    /**
     * get the number of seeds in the store
     * @return
     */
    public int getAmount();

    /**
     * add this amount of seeds
     * almost definitely 1 seed
     * @param amount
     */
    public void addAmount(int amount);

    /**
     * takes all the seeds from this store
     * @return
     */
    public int takeAll();

}
