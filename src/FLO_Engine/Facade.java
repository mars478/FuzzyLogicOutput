package FLO_Engine;

import Variables.Variables;
import Support.Matrix;
import Support.Rule;
import Support.SubsetD;
import Support.Num;

/**
 *
 * @author mars
 */

public class Facade {

    boolean DEBUG_D  = false;
    boolean DEBUG_C  = false;
    boolean DEBUG_LM = false;

    Facade(boolean debug_d, boolean debug_c, boolean debug_lm){
        DEBUG_D  = debug_d;
        DEBUG_C  = debug_c;
        DEBUG_LM = debug_lm;
    }

    public Matrix calcD(Rule[] rules) throws Exception{

        //page 97
        
        Rule[] M = rules;

            //page 98
            SubsetD[] sD = new SubsetD[M.length];
            for (int i=0;i<M.length;i++)
                sD[i] = new SubsetD(M[i]);

            Matrix[] matr = new Matrix[sD.length];
            for (int i=0;i<sD.length;i++){
                matr[i]=new Matrix(sD[i].d_arr);
                if (DEBUG_D)
                {
                    System.out.println("D["+i+"]:");
                    matr[i].output();
                }
            }
            Matrix min = Matrix.findMin(matr);
            if (DEBUG_D)
            {
                System.out.println("D:");
                min.output();
            }
            return min;
    }

    public double[] calcC(Matrix D){
        int len = D.COLUMNS;
        double[] c = new double[D.ROWS];
        double[] x = getX();

        for (int i=0;i<D.ROWS;i++){
            double[] u = D.getRow(i);
            
            Num[] arr = new Num[len];

            for (int j=0;j<len;j++)
                arr[j]= new Num(x[j],u[j]);

            //sorting arr[] by u
            for (int j=0;j<len;j++)
                for (int k=j;k<len;k++)
                    if (arr[k].u<arr[j].u){
                        Num temp = arr[k];
                        arr[k] = arr[j];
                        arr[j] = temp;
                    }

            if (DEBUG_C) System.out.println("Object #"+i+":");

            c[i] = calcS(arr);

            if (DEBUG_C)  System.out.println("\nPoint estimate :" + c[i]);

        }

        return c;
    }

    public int getBest(double[] C){
        double max = Double.MIN_VALUE;
        int max_ind = -1;

        for (int i=0;i<C.length;i++)
            if (C[i]>max){
                max = C[i];
                max_ind = i;
            }
        return max_ind+1;
    }

    private double[] getX(){
        double[] temp = new double[Variables.SIZE_OF_D()];
        for (int i=0;i<temp.length;i++)
            temp[i]=Variables.STEP_OF_D()*i;
        return temp;
    }

    private double calcS(Num[] arr){
        int size = arr.length;
        double[] x = new double[size];
        double[] u = new double[size];
        for (int i=0;i<size;i++){
            x[i]=arr[i].x;
            u[i]=arr[i].u;
        }

        if (DEBUG_C)
        {
            System.out.println("E:");
            for (int i=0;i<u.length;i++)
                System.out.print("\t"+u[i]);
            
            System.out.println("\nX:");
            for (int i=0;i<x.length;i++)
                System.out.print("\t"+x[i]);
        }

        double max = findMax(u);
        double s = 0.0;
        double[] m = new double[size];
        double[] lbd = new double[size];

        for (int i=0;i<size;i++){
            if (i==0){
                lbd[i]=u[0];
                m[i]=calcM(x,0);
            }
            else{
                lbd[i] = u[i]-u[i-1];
                m[i] = calcM(x,i);
            }
        }

        for (int i=0;i<size;i++)
            s+=(double)lbd[i]*m[i];

        return (double)s/max;
    }

    private double findMax(double[] arr){
        double max = arr[0];
        for (int i=1;i<arr.length;i++)
            if (arr[i]>max)
                max = arr[i];
        return max;
    }

    private double calcM(double[] x, int first){
        double s = 0.0;
        for (int i=first;i<x.length;i++)
            s+=x[i];
        return (double)s/(x.length-first);
    }

}