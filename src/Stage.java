// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.LinkedList;

public class Stage extends FactoryComponent {
    private int M, N;
    private double dTimeStarved, dTimeBlocked;
    private double dTimeLastStarved, dTimeLastBlocked;
    private double dWork;

    public Stage(FactoryComponent _fcPrev, FactoryComponent _fcNext,
                 int _M, int _N, Factory _factory, String _sName)
    {
        this.fcPrevComponents.add(_fcPrev);
        this.fcNextComponents.add(_fcNext);
        this.M = _M;
        this.N = _N;
        this.factory = _factory;
        this.iStorageCapacity = 1;
        qInventory = new LinkedList<>();
        this.sComponentName = _sName;
        dTimeLastStarved = 0;
    }

    public void itemFinished()
    {
        for (FactoryComponent fcPrev : fcPrevComponents)
        {
            push(); // try to push the new item
        }
    }

    // attempts to push an item to next stage
    public void push()
    {
        boolean bTemp = this.bIsBlocked;
        if (bCheckHasItem())    // if an item exists
        {
            if (qInventory.peek().getT2() <= factory.dGetCurrentTime())  // if the item has finished processing
            {
                for (FactoryComponent fcNext : fcNextComponents)    // for each next component (will be just one)
                {
                    if (!fcNext.bCheckIsFull()) // if next is not full, trigger their grab
                    {
                        fcNext.grab(qInventory.remove());

                        this.bIsBlocked = false;

                        if(bTemp)
                            dTimeBlocked += factory.dGetCurrentTime() - dTimeLastBlocked;

                        for (FactoryComponent fcPrev : fcPrevComponents) // trigger previous component's push
                            fcPrev.push();
                    }
                    else // else they are full, so current component is blocked
                    {
                        this.bIsBlocked = true;

                        if(!bTemp)
                            dTimeLastBlocked = factory.dGetCurrentTime();
                    }
                }
            }
        }
    }

    protected void grab(Item item)
    {
        item.addStage(this.sComponentName);     // add stage name for production path

        item.setT1(factory.dGetCurrentTime());  // set T1
        double P = M + N * (factory.rGetFactoryRandom().nextDouble() - .5); // set P
        item.setT2(item.getT1() + P);                                       // set T2

        this.dWork += P;    // add work time

        factory.createEvent(item.getT2(), this);    // create event

        qInventory.add(item);   // add item to inventory

        // if currently starved, add starve time. End starved status
        if (bIsStarved)
            dTimeStarved += (factory.dGetCurrentTime() - dTimeLastStarved);
        bIsStarved = false;
    }

    public double getWorkPercentage()
    {
        return dWork / factory.dGetMaxTime() * 100;
    }

    public double getTimeStarved()
    {
        return dTimeStarved;
    }

    public double getdTimeBlocked()
    {
        return dTimeBlocked;
    }
}