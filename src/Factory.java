// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Factory {
    private int M, N, Qmax;
    private Random r = new Random();

    private double dCurrentTime = 0;
    private double dMaxTime = 1000000;

    private StorageUnit Q01, Q12, Q23, Q34, Q45, Q56;
    private Stage S0a, S0b, S1, S2, S3a, S3b, S4, S5a, S5b, S6;
    private ItemPrinter printerA, printerB;
    private StorageWarehouse warehouse;

    private Comparator<Event> eventComparator = new EventComparator();
    private PriorityQueue<Event> eventQueue = new PriorityQueue<>(1, eventComparator);

    Factory(int _M, int _N, int _Qmax)
    {
        this.M = _M;
        this.N = _N;
        this.Qmax = _Qmax;

        printerA = new ItemPrinter("a");
        printerB = new ItemPrinter("b");
        warehouse = new StorageWarehouse();

        Q01 = new StorageUnit(this, Qmax, "Q01");
        Q12 = new StorageUnit(this, Qmax, "Q12");
        Q23 = new StorageUnit(this, Qmax, "Q23");
        Q34 = new StorageUnit(this, Qmax, "Q34");
        Q45 = new StorageUnit(this, Qmax, "Q45");
        Q56 = new StorageUnit(this, Qmax, "Q56");

        S0a = new Stage(printerA, Q01, M * 2, N * 2, this,  "S0a");
        // specs say s0b is normal M, N
        S0b = new Stage(printerB, Q01, M, N, this,  "S0b");
        S1 = new Stage(Q01, Q12, M, N, this,  "S1");
        S2 = new Stage(Q12, Q23, M, N, this,  "S2");
        S3a = new Stage(Q23, Q34, M * 2, N * 2, this,  "S3a");
        S3b = new Stage(Q23, Q34, M * 2, N * 2, this,  "S3b");
        S4 = new Stage(Q34, Q45, M, N, this,  "S4");
        S5a = new Stage(Q45, Q56, M * 2, N * 2, this,  "S5a");
        S5b = new Stage(Q45, Q56, M * 2, N * 2, this,  "S5b");
        S6 = new Stage(Q56, warehouse, M, N, this,  "S6");

        printerA.addFcNext(S0a);
        printerB.addFcNext(S0b);
        warehouse.addFcPrev(S6);

        Q01.addFcPrev(S0a);
        Q01.addFcPrev(S0b);
        Q01.addFcNext(S1);

        Q12.addFcPrev(S1);
        Q12.addFcNext(S2);

        Q23.addFcPrev(S2);
        Q23.addFcNext(S3a);
        Q23.addFcNext(S3b);

        Q34.addFcPrev(S3a);
        Q34.addFcPrev(S3b);
        Q34.addFcNext(S4);

        Q45.addFcPrev(S4);
        Q45.addFcNext(S5a);
        Q45.addFcNext(S5b);

        Q56.addFcPrev(S5a);
        Q56.addFcPrev(S5b);
        Q56.addFcNext(S6);
    }

    public void createEvent(double time, Stage stage)
    {
        Event event = new Event(time, stage);
        eventQueue.add(event);
    }

    public void run()
    {
        printerA.push();    // begin production by pushing items to first stages
        printerB.push();
        while (dCurrentTime <= dMaxTime)    // production loop, events are created when items are created
        {
            // grab next event
            Event e = eventQueue.poll();
            // set current time to event's projected time to complete an item
            dCurrentTime = e.dTriggerTime;
            // if project current time is within max time, run the factory
            if (dCurrentTime <= dMaxTime)
                e.triggerStage();
        }
        printStatistics();
    }

    public double dGetCurrentTime()
    {
        return dCurrentTime;
    }

    public double dGetMaxTime() { return dMaxTime; }

    public Random rGetFactoryRandom()
    {
        return r;
    }

    private void printStatistics()
    {
        System.out.println("Production Stations: ");
        System.out.println("--------------------------------------------");
        System.out.println("Stage:  Work[%]     Starve[t]       Block[t]");
        System.out.printf("S0a      %2.2f%s         %.2f           %.2f %n", S0a.getWorkPercentage(), "%", S0a.getTimeStarved(), S0a.getdTimeBlocked());
        System.out.printf("S0b      %2.2f%s         %.2f           %.2f %n", S0b.getWorkPercentage(), "%", S0b.getTimeStarved(), S0b.getdTimeBlocked());
        System.out.printf("S1       %2.2f%s         %.2f           %.2f %n", S1.getWorkPercentage(), "%", S1.getTimeStarved(), S1.getdTimeBlocked());
        System.out.printf("S2       %2.2f%s         %.2f           %.2f %n", S2.getWorkPercentage(), "%", S2.getTimeStarved(), S2.getdTimeBlocked());
        System.out.printf("S3a      %2.2f%s         %.2f           %.2f %n", S3a.getWorkPercentage(), "%", S3a.getTimeStarved(), S3a.getdTimeBlocked());
        System.out.printf("S3b      %2.2f%s         %.2f           %.2f %n", S3b.getWorkPercentage(), "%", S3b.getTimeStarved(), S3b.getdTimeBlocked());
        System.out.printf("S4       %2.2f%s         %.2f           %.2f %n", S4.getWorkPercentage(), "%", S4.getTimeStarved(), S4.getdTimeBlocked());
        System.out.printf("S5a      %2.2f%s         %.2f           %.2f %n", S5a.getWorkPercentage(), "%", S5a.getTimeStarved(), S5a.getdTimeBlocked());
        System.out.printf("S5b      %2.2f%s         %.2f           %.2f %n", S5b.getWorkPercentage(), "%", S5b.getTimeStarved(), S5b.getdTimeBlocked());
        System.out.printf("S6       %2.2f%s         %.2f           %.2f %n", S6.getWorkPercentage(), "%", S6.getTimeStarved(), S6.getdTimeBlocked());
        System.out.println("");
        System.out.println("Storage Queues: ");
        System.out.println("--------------------------------------------");
        System.out.println("Store   AvgTime[t]  AvgItems");
        System.out.printf("Q01           %.2f      %.2f %n", Q01.dGetAverageTime(), Q01.dGetAverageItems());
        System.out.printf("Q12           %.2f      %.2f %n", Q12.dGetAverageTime(), Q12.dGetAverageItems());
        System.out.printf("Q23           %.2f      %.2f %n", Q23.dGetAverageTime(), Q23.dGetAverageItems());
        System.out.printf("Q34           %.2f      %.2f %n", Q34.dGetAverageTime(), Q34.dGetAverageItems());
        System.out.printf("Q45           %.2f      %.2f %n", Q45.dGetAverageTime(), Q45.dGetAverageItems());
        System.out.printf("Q56           %.2f      %.2f %n", Q56.dGetAverageTime(), Q56.dGetAverageItems());
        System.out.println("");
        System.out.println("Production Paths:");
        System.out.println("-------------------");
        System.out.printf("S3a -> S5a:   %d %n", warehouse.getiTotal3a5aItems());
        System.out.printf("S3a -> S5b:   %d %n", warehouse.getiTotal3a5bItems());
        System.out.printf("S3b -> S5a:   %d %n", warehouse.getiTotal3b5aItems());
        System.out.printf("S3b -> S5b:   %d %n", warehouse.getiTotal3b5bItems());
        System.out.println("");
        System.out.println("Production Items:");
        System.out.println("------------------");
        System.out.printf("S0a:          %d %n", warehouse.iGetTotalAItems());
        System.out.printf("S0b:          %d %n", warehouse.iGetTotalBItems());
        //System.out.printf("%n Total Items: %d %n", warehouse.iTotalItemsProcessed);
    }
}
