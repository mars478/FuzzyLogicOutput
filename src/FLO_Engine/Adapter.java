package FLO_Engine;

import Support.Function;
import Support.Input;
import Support.Matrix;
import java.io.File;

/**
 *
 * @author mars
 */

public class Adapter {

    public final int INP_CAST = -3;
    public final int FUNC_CAST = -2;
    public int STATUS = -1;

    private static Adapter instance = null;

    public static Adapter getInstance(){
        return instance;
    }

    private File inpClass;
    private File[] functionClasses;

    Input inp;
    Function[] func;

    public Adapter(File inpClass, File[] functionClasses){

        this.inpClass = inpClass;
        this.functionClasses = functionClasses;
        instance = this;

        try{
            //loading the FL classes
            STATUS = INP_CAST;
            mClassLoader cl = new mClassLoader(inpClass.getParent());
            inp = (Input)cl.loadClass(parse(inpClass.getName())).newInstance();

            STATUS = FUNC_CAST;

            cl = new mClassLoader(functionClasses[0].getParent());
            func = new Function[functionClasses.length];
            for (int i=0;i<func.length;i++)
                func[i] = (Function) cl.loadClass(parse(functionClasses[i].getName())).newInstance();
            //eol

            STATUS = 1;
        }
        catch (Exception e){
        }
    }

    public int Calc(double[][] arr) {

        try{
            Facade f = new Facade(true,true,true);

            inp.initFunc(func);

            Matrix M = f.calcD(inp.makeRules(arr));
            double[] C = f.calcC(M);

            STATUS = f.getBest(C);
            return STATUS;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    private String parse(String inp){
        return inp.substring(0, inp.length()-6);
    }

    public int getFCnt(){
        if (inp != null) return inp.FUNC_CNT;
        return -1;
    }
}