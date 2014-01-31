package Support;

/**
 *
 * @author mars
 */

import Variables.Variables;

public class SubsetD {

    private final boolean DEBUG = false;

    public double[][] d_arr;

    public SubsetD(Rule rule) throws Exception{
        d_arr = makeD(rule);
    }

    public double[][] makeD(Rule rule) throws Exception{
        d_arr = new double[rule.m.length][Variables.SIZE_OF_D()];
        
        for (int i=0;i<rule.m.length;i++)
            for (int j=0;j<Variables.SIZE_OF_D();j++){
                d_arr[i][j]=findMin(1-rule.m[i]+rule.getF(j*Variables.STEP_OF_D()));
                if (DEBUG) System.out.print("m["+i+"]="+rule.m[i]+"\tj*Variables.STEP_OF_D()="+j*Variables.STEP_OF_D()+"\tY="+rule.getF(j*Variables.STEP_OF_D())+"\n");
            }

        return d_arr;
    }

    private double findMin(double x){
        if (x>1) return 1;
        return x;
    }

}