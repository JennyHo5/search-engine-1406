public class PageRankCalculationTester {
    public static void main(String[] args) {
        String url1 = "https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-7.html";
        String url2 = "https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-9.html";
        System.out.println(PageRankCalculation.isConnected(url1, url2));

        System.out.println(PageRankCalculation.getURLsConnect());
    }
}
