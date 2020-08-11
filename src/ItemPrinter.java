// c3183623 Mitchell Hedges
// SENG2200 assignment three

public class ItemPrinter extends StorageUnit {
    private String sUIDSuffix;

    public ItemPrinter(String _sUIDSuffix)
    {
        this.sUIDSuffix = _sUIDSuffix;
    }

    // creates and item and sends to target component
    private void printItem()
    {
        Item item = new Item(IDGenerator.ID() + sUIDSuffix);    // add a or b
        for (FactoryComponent fcNext : fcNextComponents)
            fcNext.grab(item);
    }

    public void push()
    {
        printItem();
    }

    @Override
    public void grab(Item item)
    {
    }

    @Override
    public boolean bCheckHasItem()
    {
        return true;
    }

    @Override
    public boolean bCheckIsFull()
    {
        return true;
    }
}