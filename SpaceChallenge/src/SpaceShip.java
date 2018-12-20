public interface SpaceShip {
    abstract boolean launch();
    abstract boolean land();

    default boolean canCarry(String Item) {
//        return true if can carry the item, otherwise false.
        return false;
    }

    default int carry(String Item) {
//        update the current weight of the rocket.
        return 0;
    }


}
