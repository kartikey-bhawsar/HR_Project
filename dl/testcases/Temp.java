import java.util.HashMap;
import java.util.Map;

interface FInterface
{
    public void accept(int e, String s);
}

class FunctionalSeries
{
    private Map<Integer,String> mp=new HashMap<>();
    public void add(int a,String s)
    {
        mp.put(a,s);
    }
    public void forEach1(FInterface lambda)
    {
        mp.forEach(lambda::accept);
    }

}


public class Temp {
    public static void main(String args[])
    {
        FunctionalSeries fs=new FunctionalSeries();
        for(int i=5;i<11;i++)
        {
            int a=i;
            String s="Name"+i;
            fs.add(a,s);
        }
        fs.forEach1((k,v)->{
            System.out.println(k);
            System.out.println(v);
        });
    }
}
