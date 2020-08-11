// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.Comparator;

public class Event {
    public static double dTime;
    protected double dTriggerTime;
    private Stage stage;

    Event(double _dTriggerTime, Stage _stage)
    {
        this.dTriggerTime = _dTriggerTime;
        this.stage = _stage;
    }

    void triggerStage()
    {
        stage.itemFinished();
    }
}

class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event x, Event y)
    {
        return Double.compare(x.dTriggerTime, y.dTriggerTime);
    }
}