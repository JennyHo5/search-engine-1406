public class MatMultKit {
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
    public static double[][] multMatrix(double[][] a, double[][] b) {
        if (a.length == 0 || b[0].length == 0)
            return null;

        double[][] newMatrix = new double[a.length][b[0].length];
        for (int rowA = 0; rowA < a.length;rowA ++)
            for (int colB = 0; colB < b[0].length; colB ++)
                for (int rowB = 0; rowB < b.length; rowB ++)
                    newMatrix[rowA][colB] += a[rowA][rowB] * b[rowB][colB];
        return newMatrix;
    }

    //This function accepts two single-row matrices (i.e., vectors) using the list representation for matrices used so far in this problem (e.g., a = [ [9, 3, 1] ]). Your function must calculate the Euclidean distance between these two vectors.
    public static double euclideanDist (double[][] a, double[][] b) {
        double sum = 0;
        for (int i = 0; i < a[0].length; i ++)
            sum += Math.pow(a[0][i] - b[0][i], 2);
        return Math.pow(sum, 0.5);
    }


}
