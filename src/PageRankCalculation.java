import java.io.*;
import java.util.*;

public class PageRankCalculation {
    //read crawled URLs
    private static ArrayList<String> crawledURLsArray = FileInputAndOutputKit.readCrawledURLsArray();
    private static Integer N = crawledURLsArray.size();
    private static HashMap<Integer, String> map = readIntURLMap();

    private static HashMap<String, HashMap<String, Boolean>> connect = readConnect();





    public static HashMap<Integer, String> readIntURLMap() {
        //read map-url from file
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("int-url.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<Integer, String> map = null;
        try {
            map = (HashMap<Integer, String>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static HashMap<String, HashMap<String, Boolean>> readConnect() {
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("connect.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, HashMap<String, Boolean>> connect = null;
        try {
            connect = (HashMap<String, HashMap<String, Boolean>>)reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connect;
    }


    //turn integer into the original URL
    public static String getURLFromInt(Integer i){
        if (map.containsKey(i)) //O(1)
            return map.get(i);
        else return null;
    }

    public static HashMap<String, Double> calculatePageranks(){
        double a = 0.1;
        double threshold = 0.0001;

        //create an N*N matrix
            //N = number of pages(URLs) crawled
        double[][] matrix = MatMultKit.identity(N);

        //for the number at [i, j], if node i links to node j, then [i, j] = 1; otherwise = 0
        for (int i=0; i < N; i++) { //O(n)
            String iURL = getURLFromInt(i); //O(1)
            for (int j=0; j < N; j++) { //O(n)
                String jURL = getURLFromInt(j); //O(1)
                if (Objects.equals(iURL, jURL))
                    continue;
                if (connect.get(iURL).get(jURL) || connect.get(jURL).get(iURL))
                    matrix[i][j] = 1;
            }
        }

        //sort the rows: has no 1s / has 1s
        ArrayList<Integer> rowsHaveNo1 = new ArrayList<>();
        ArrayList<Integer> rowsHave1 = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 1) {
                    rowsHave1.add(i);
                    break;
                }
                if (j == N - 1) //reach the end of the row and there is no 1
                    rowsHaveNo1.add(i);
            }
        }

        //if a row has no 1, replace each element in that row by 1/N
        for (int r = 0; r < N; r ++){
            if (!rowsHaveNo1.contains(r))
                continue;
            for (int c = 0; c < N; c++)
                matrix[r][c] = (double)1/N;
        }


        //in other rows, divide each 1 by number of 1s in that row
        for (int r = 0; r < N; r++) {
            double sum = 0;
            if (!rowsHave1.contains(r))
                continue;
            for (int c = 0; c < N; c++){
                if (matrix[r][c] == 1)
                    sum ++;
            }
            for (int c = 0; c < N; c++){
                if (matrix[r][c] == 1)
                    matrix[r][c] = 1/sum;
            }
        }

        //multiply the resulting matrix by (1 - a)
        matrix = MatMultKit.multScalar(matrix, 1-a);

        //add a/N to each entry of the resulting matrix
        for (int i=0; i< N; i++)
            for (int j=0; j<N; j++)
                matrix[i][j] += a/N;


            //create a vector
        double[][] v0 = new double[1][N];

        for (int i=0; i < N; i++) {
            v0[0][i] = (double)1/N;
        }

            //keep multiplying the matrix by vector v0 until difference in π between iterations is below a threshold
        double e = 1;
        while (e > threshold) {
            double[][] v1 = MatMultKit.multiply(v0, matrix);
            e = MatMultKit.euclideanDist(v0, v1);
            v0 = v1;
        }

        //return pageranks for all pages
        HashMap<String, Double> pageranks = new HashMap<>();
        for (int i = 0; i < N; i++)
            pageranks.put(getURLFromInt(i), v0[0][i]);

        return pageranks;
    }
}
