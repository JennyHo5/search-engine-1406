public class CrawlerStart {
    public static void main(String[] args) {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here
        tester.initialize();
        long startTime = System.nanoTime();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html");
        System.out.println("Finished crawling.");
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }
}
