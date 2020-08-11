// c3183623 Mitchell Hedges
// SENG2200 assignment three

public class PA3 {
    public static void main(String[] args)
    {
        Factory factory = new Factory(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));

        factory.run();
    }
}