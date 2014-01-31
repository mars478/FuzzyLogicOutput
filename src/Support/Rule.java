package Support;

/**
 *
 * @author mars
 */
public class Rule{

    public Skill[] u;
    private Function function;
    public double[] m;

    public Rule(Skill[] u, Function function) throws Exception{
        this.u = u;
        this.function = function;
        m = findMin(u);
    }

    public double[] findMin(Skill[] arr) throws Exception{
        int size=arr[0].skill.length;

        for (int i=0;i<arr.length;i++)
            if (arr[i].skill.length != size)
                throw new Exception("Incorrect skill");

        double[] min = new double[size];
        for (int i=0;i<min.length;i++)
            min[i] = Integer.MAX_VALUE;

        for (int i=0;i<arr.length;i++)
            for (int j=0;j<size;j++)
                if (arr[i].skill[j]< min[j])
                    min[j] = arr[i].skill[j];

        m = min;
        
        return min;
    }

    public double getF(double x){
        return function.getY(x);
    }
}