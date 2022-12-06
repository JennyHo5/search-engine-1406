public class MatMultKit {
    public static double[][] identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 0;
        return a;
    }

    //return a new 2D list containing the result when the given matrix is multiplied by the given scalar (i.e., a given integer/float value). The original matrix passed as an argument to the function must not be modified.
    public static double[][] multScalar (double[][] matrix, double scale) {
        int N = matrix.length;
        double[][] newMatrix = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double temp = matrix[i][j] * scale;
                newMatrix[i][j] = temp;
            }
        }
        return newMatrix;
    }

    //return a new matrix that is the result of multiplying the matrix a by the matrix b. The original matrix passed as an argument to the function must not be modified.
    //rank = number of col in a = number of row in b
    // return c = a * b
    public static double[][] multiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    // matrix-vector multiplication (y = A * x)
    public static double[] multiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }


    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] x, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }



    //This function accepts two single-row matrices (i.e., vectors) using the list representation for matrices used so far in this problem (e.g., a = [ [9, 3, 1] ]). Your function must calculate the Euclidean distance between these two vectors.
    public static double euclideanDist (double[][] a, double[][] b) {
        double sum = 0;
        for (int i = 0; i < a[0].length; i ++)
            sum += Math.pow(a[0][i] - b[0][i], 2);
        return Math.pow(sum, 0.5);
    }


}
