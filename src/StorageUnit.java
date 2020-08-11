// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StorageUnit extends FactoryComponent {
    private int iTotalItemsChecked, iTotalTimesCounted;

    private List<Double> timeAdded = new ArrayList<>();
    private List<Double> timeRemoved = new ArrayList<>();

    public StorageUnit()
    {
    }

    public StorageUnit(Factory _factory, int _i, String _sName)
    {
        this.factory = _factory;
        this.iStorageCapacity = _i;
        qInventory = new LinkedList<>();
        this.sComponentName = _sName;
    }

    public void grab(Item item)
    {
        qInventory.add(item);

        timeAdded.add(factory.dGetCurrentTime());   // add time to time average list

        iTotalItemsChecked += qInventory.size();
        iTotalTimesCounted++;

        // storage units can have multiple next components
        for (FactoryComponent fcNext : fcNextComponents)
        {
            if (!fcNext.bCheckIsFull())
            {
                push();
            }
        }
    }

    public void push()
    {
        for (FactoryComponent fcNext : fcNextComponents)
        {
            if (bCheckHasItem() & !fcNext.bCheckIsFull())   // if an item exists in inventory, and next component has space
            {
                timeRemoved.add(factory.dGetCurrentTime());

                fcNext.grab(qInventory.remove());

                for (FactoryComponent fcPrev : fcPrevComponents)
                    fcPrev.push();
            }
        }
    }

    public double dGetAverageTime()
    {
        double dReturnValue = 0;

        for (int i = 0; i < timeRemoved.size(); i++)
            dReturnValue += timeRemoved.get(i) - timeAdded.get(i);
        dReturnValue /= iTotalTimesCounted;

        return dReturnValue;
    }

    public double dGetAverageItems()
    {
        return (double) iTotalItemsChecked / iTotalTimesCounted;
    }
}