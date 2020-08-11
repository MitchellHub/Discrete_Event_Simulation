// c3183623 Mitchell Hedges
// SENG2200 assignment three

public class StorageWarehouse extends StorageUnit {
    // end point for factory, infinite storage
    private int iTotalAItems, iTotalBItems;
    private int iTotal3a5aItems, iTotal3a5bItems, iTotal3b5aItems, iTotal3b5bItems;

    @Override
    public void grab(Item item)
    {
        qInventory.add(item);   // add item to infinite storage

        // count items produced from S0a or S0b
        if (item.sGetUID().contains("a"))
            iTotalAItems++;
        else
            iTotalBItems++;

        // grab production path
        String sStageNames = "";
        for(String s : item.getListOfStageNames())
            sStageNames += s;

        // count production paths
        if (sStageNames.equals("S0aS1S2S3aS4S5aS6") | sStageNames.equals("S0bS1S2S3aS4S5aS6"))
            iTotal3a5aItems++;
        else if(sStageNames.equals("S0aS1S2S3aS4S5bS6") |sStageNames.equals("S0bS1S2S3aS4S5bS6"))
            iTotal3a5bItems++;
        else if(sStageNames.equals("S0aS1S2S3bS4S5aS6") |sStageNames.equals("S0bS1S2S3bS4S5aS6"))
            iTotal3b5aItems++;
        else if(sStageNames.equals("S0aS1S2S3bS4S5bS6") |sStageNames.equals("S0bS1S2S3bS4S5bS6"))
            iTotal3b5bItems++;
    }

    public int iGetTotalAItems() {
        return iTotalAItems;
    }
    public int iGetTotalBItems() {
        return iTotalBItems;
    }

    public void push()
    {
    }

    @Override
    public boolean bCheckIsFull() {
        return false;
    }

    public int getiTotal3a5aItems()
    {
        return iTotal3a5aItems;
    }

    public int getiTotal3a5bItems()
    {
        return iTotal3a5bItems;
    }

    public int getiTotal3b5aItems()
    {
        return iTotal3b5aItems;
    }

    public int getiTotal3b5bItems()
    {
        return iTotal3b5bItems;
    }
}