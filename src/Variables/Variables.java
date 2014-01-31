package Variables;

/**
 *
 * @author mars
 */

public class Variables {

    private static int SIZE_OF_D     = 11;
        //horizontal size of D matrix
    private static double STEP_OF_D  = (double)1/(SIZE_OF_D-1);
        //step between elements of D matrix

    public static int SIZE_OF_D(){
        return SIZE_OF_D;
    }

    public static double STEP_OF_D(){
        return STEP_OF_D;
    }
}