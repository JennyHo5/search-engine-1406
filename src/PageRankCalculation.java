import java.util.*;

public class PageRankCalculation {
    //read crawled URLs
    static ArrayList<String> crawledURLsArray = FileInputAndOutputKit.readCrawledURLsArray();
    static Integer N = crawledURLsArray.size();
    static HashMap<Integer, String> intWithURL = mapIntWithURL();

    //if two URL are connected
    public static boolean isConnected(String url1, String url2) {
        //get url1's all outgoing links
        HashSet<String> url1OutgoingLinksHash = FindElementsKit.getOutgoingLinksHash(url1);
        //get url2's incoming links
        HashSet<String> url2IncomingLinksHash = FindElementsKit.getIncomingLinksHash(url2);
        return url1OutgoingLinksHash.contains(url2) || url2IncomingLinksHash.contains(url1); //O(1)
    }

    //map each URL to a specific int, and create a HashMap to contain those ints and URLs
    public static HashMap<Integer, String> mapIntWithURL() {
        HashMap<Integer, String> intWithURL = new HashMap<>();
        for (int i = 0; i < N; i++) //O(n)
            intWithURL.put(i, crawledURLsArray.get(i));
        return intWithURL;
    }

    //turn integer into the original URL
    public static String getURLFromInt(Integer i) {
        HashMap<Integer, String> map = mapIntWithURL(); //O(n)
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if (Objects.equals(key, i))
                return value;
        }
        return null;
    }

    public static HashMap<String, Double> calculatePageranks() {
        double a = 0.1;
        double threshold = 0.0001;

        //create an N*N matrix
            //N = number of pages(URLs) crawled
        double[][] matrix = new double[N][N];
        for (int i=0; i < N; i++)
            for (int j=0; j < N; j++)
                matrix[i][j] = 0; //O(n^2)

        //for the number at [i, j], if node i links to node j, then [i, j] = 1; otherwise = 0
        for (int i=0; i < N; i++) {
            String iURL = getURLFromInt(i);
            for (int j=0; j < N; j++) {
                String jURL = getURLFromInt(j);
                if (Objects.equals(iURL, jURL))
                    continue;
                if (isConnected(iURL, jURL) || isConnected(jURL, iURL))
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


        //keep multiplying the matrix by a vector π (1, 0, 0, ...) until difference in π between iterations is below a threshold
            //create a vector
        double[][] v0 = new double[1][N];

        for (int i=0; i < N; i++) {
            v0[0][i] = (double)1/N;
        }


            //keep multiplying the matrix by vector v0 until difference in π between iterations is below a threshold
        double e = 1;
        while (e > threshold) {
            double[][] v1 = MatMultKit.multMatrix(v0, matrix);
            e = MatMultKit.euclideanDist(v0, v1);
            v0 = v1;
        }

        //return pageranks for all pages
        HashMap<String, Double> pageranks = new HashMap<>();
        for (int i = 0; i < N; i++)
            pageranks.put(intWithURL.get(i), v0[0][i]);

        return pageranks;
    }
}
