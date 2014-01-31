package Support;

/**
 *
 * @author mars
 */
public class Matrix {

    private double matrix[][];
    public int ROWS;
    public int COLUMNS;

    public Matrix(int rows, int columns)
    {
        ROWS = rows;
        COLUMNS = columns;
        matrix = new double[ROWS][COLUMNS];
    }

    public Matrix(double[][] m)
    {
        if ((m==null) || (m[0]==null))
            return;
        ROWS = m.length;
        COLUMNS = m[0].length;
        matrix = m;
    }

    public double[][] getMatrix()
    {
        return (double[][]) matrix.clone();
    }

    public Matrix multiply(Matrix M) throws Exception
    {
        if (this.COLUMNS != M.ROWS)
            throw new Exception("Incorrect matrix");

        double[][] A=this.getMatrix();
        double[][] B=M.getMatrix();
        double[][] C=new double[this.ROWS][M.COLUMNS];

        int thisRows = this.ROWS;
        int thisCol = this.COLUMNS;
        int mRows = M.ROWS;
        int mCol = M.COLUMNS;
        if (thisCol != mRows)
            throw new Exception("matrices don't match: " + thisCol + " != " + mRows);

        for (int i=0; i<thisRows; i++)
            for (int j=0; j<mCol; j++)
                for (int k=0; k<thisCol; k++)
                    C[i][j] += A[i][k] * B[k][j];

        return new Matrix(C);
    }

    public static Matrix findMin(Matrix[] Arr) throws Exception
    {
        int rows = Arr[0].getMatrix().length;
        int cols = Arr[0].getMatrix()[0].length;

        //for (int i=0;i<Arr.length;i++) { System.out.println("D["+(i+1)+"]:"); Arr[i].output(); System.out.print("\n"); }

        for (int i=0;i<Arr.length;i++)
            if ((Arr[i].getMatrix().length != rows) || (Arr[i].getMatrix()[0].length != cols))
                throw new Exception("Incorrect size of matrix");

        double[][] min = new double[rows][cols];
        for (int i=0;i<rows;i++)
            for (int j=0;j<cols;j++)
                min[i][j]=Double.MAX_VALUE;

        for (int i=0;i<rows;i++)
            for (int j=0;j<cols;j++)
                for (int k=0;k<Arr.length;k++)
                    if (Arr[k].getMatrix()[i][j]<min[i][j])
                        min[i][j]=Arr[k].getMatrix()[i][j];

        return new Matrix(min);
    }

    public double[] getRow(int row){
        double[] temp = new double[this.COLUMNS];
        for (int i=0;i<this.COLUMNS;i++)
            temp[i]=this.matrix[row][i];
        return temp;
    }

    public void output()
    {
        for (int i=0;i<this.ROWS;i++){
            for (int j=0; j<this.COLUMNS;j++)
                System.out.print(matrix[i][j]+"\t");
            System.out.println();
        }
    }
}