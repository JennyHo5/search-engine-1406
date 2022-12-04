import java.io.IOException;

public class ProjectTesterImpTester {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ProjectTesterImp imp = new ProjectTesterImp();
        String url = "https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-1.html";
        imp.crawl("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-3.html");
        System.out.println(imp.getPageRank(url));

    }
}
