package kalah.Interface;

public interface IHouse{
    /**
     * get the amount of seeds already in the house
     * @return the amount of seeds
     */
    int getAmount();

    /**
     * add an amount to the player's house
     * @param amount amount of seeds to be added
     */
    void addAmount(int amount);
}
