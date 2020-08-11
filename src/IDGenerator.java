public class IDGenerator {
    private static long _id = 0;

    IDGenerator()
    {
    }

    public static String ID()
    {
        return String.valueOf(_id++);
    }
}