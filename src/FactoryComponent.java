// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.LinkedList;
import java.util.Queue;

abstract public class FactoryComponent {
    Factory factory;
    // protected FactoryComponent fcPrev;
    protected LinkedList<FactoryComponent> fcPrevComponents = new LinkedList<>(), fcNextComponents = new LinkedList<>();
    protected Queue<Item> qInventory = new LinkedList<>();   // inventories
    protected int iStorageCapacity;
    protected boolean bIsBlocked = false, bIsStarved = true;
    protected String sComponentName;

    // grab should only add an item sent from the previous component
    // it should only be triggered when being sent an item
    abstract protected void grab(Item item);

    // push should only push an item if it can
    // push will trigger the next item's grab if it is possible to push
    abstract protected void push();

    protected boolean bCheckBlocked() {
        return this.bIsBlocked;
    }

    protected boolean bCheckStarved() {
        return this.bIsStarved;
    }

    protected boolean bCheckIsFull() {
        return this.qInventory.size() >= iStorageCapacity;
    }
    protected boolean bCheckHasItem() {
        return this.qInventory.size() > 0;
    }

    protected void addFcPrev(FactoryComponent _prev) {
        this.fcPrevComponents.add(_prev);
    }

    public void addFcNext(FactoryComponent _next) {
        this.fcNextComponents.add(_next);
    }
}
