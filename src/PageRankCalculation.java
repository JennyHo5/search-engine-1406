import java.util.*;

public class PageRankCalculation {
    //read crawled URLs
    static ArrayList<String> crawledURLs = FileInputAndOutputKit.readCrawledURLs();
    static Integer N = crawledURLs.size();

    //if two URL are connected
    public static boolean isConnected(String url1, String url2) {
        //get url1's all outgoing links
        List<String> url1OutgoingLinks = new ArrayList<>();
        url1OutgoingLinks = FindElementsKit.getOutgoingLinks(url1);
        //get url2's incoming links
        List<String> url2IncomingLinks = new ArrayList<>();
        url2IncomingLinks = FindElementsKit.getIncomingLinks(url2);
        return url1OutgoingLinks.contains(url2) || url2IncomingLinks.contains(url1);
    }

    //return a HashMap including all the information of whether two URLs are connected
    public static HashMap<String, HashMap<String, Boolean>> getURLsConnect() {
        //create a HashMap
        HashMap<String, HashMap<String, Boolean>> isConnect = new HashMap<>();
        for (String url1 : crawledURLs) {
            isConnect.put(url1, new HashMap<>());
            for (String url2 : crawledURLs) {
                if (Objects.equals(url2, url1))
                    continue;
                if (isConnected(url1, url2))
                    isConnect.get(url1).put(url2, true);
                else
                    isConnect.get(url1).put(url2, false);
            }
        }
        return isConnect;
    }

    //map each URL to a specific int, and create a HashMap to contain those ints and URLs
    public static HashMap<Integer, String> mapIntWithURL() {
        HashMap<Integer, String> intWithURL = new HashMap<>();
        for (int i = 0; i < N; i++)
            intWithURL.put(i, crawledURLs.get(i));
        return intWithURL;
    }

    //turn integer into the original URL
    public static String getURLFromInt(Integer i) {
        HashMap<Integer, String> map = mapIntWithURL();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if (Objects.equals(key, i))
                return value;
        }
        return null;
    }



    public static void calculatePageranks() {
        double a = 0.1;
        double threshold = 0.0001;

        //create an N*N matrix
            //N = number of pages(URLs) crawled
        int[][] matrix = new int[N][N];
        for (int i=0; i < N; i++)
            for (int j=0; j < N; j++)
                matrix[i][j] = 0;

        //for the number at [i, j], if node i links to node j, then [i, j] = 1; otherwise = 0

    }
}
