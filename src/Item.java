// c3183623 Mitchell Hedges
// SENG2200 assignment three

import java.util.LinkedList;

public class Item {
    private String sUID;
    private double T1, T2;
    private LinkedList<String> listOfStageNames = new LinkedList<>();   // production path

    public Item(String _strUID)
    {
        this.sUID = _strUID;
    }

    public String sGetUID() {
        return sUID;
    }

    public void addStage(String _stage) {
        this.listOfStageNames.add(_stage);
    }

    public LinkedList<String> getListOfStageNames() {
        return listOfStageNames;
    }

    public double getT1(){
        return this.T1;
    }

    public double getT2(){
        return this.T2;
    }

    public void setT1(double _T1) {
        this.T1 = _T1;
    }

    public void setT2(double _T2) {
        this.T2 = _T2;
    }
}