import java.util.*;

public class PageRankCalculation {

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
        //read crawled URLs
        HashSet<String> crawledURLs = FileInputAndOutputKit.readCrawledURLs();
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

    public static void calculatePageranks() {

    }
}
