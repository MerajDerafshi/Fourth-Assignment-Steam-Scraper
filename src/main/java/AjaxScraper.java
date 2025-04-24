import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AjaxScraper {
    // Method to scrape and display movies for a given year
    private List<Movie> scrapeMovies(int year) throws Exception {
        String url = "https://www.scrapethissite.com/pages/ajax-javascript/?ajax=true&year=" + year;
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();

        String jsonResponse = response.body();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        List<Movie> movies = new ArrayList<>();

        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject movie = jsonArray.getJSONObject(j);
            String title = movie.optString("title", "Unknown Title");
            int nominations = movie.optInt("nominations", 0);
            int awards = movie.optInt("awards", 0);
            movies.add(new Movie(title, nominations, awards));
        }
        return movies;
    }

    // Method to display movies in a formatted table
    private void printMovies(String title, List<Movie> movies) {
        System.out.println("\n" + title);
        System.out.printf("%-50s %-12s %-10s%n", "Title", "Nominations", "Awards");
        System.out.println("-".repeat(70));
        for (Movie movie : movies) {
            System.out.printf("%-50s %-12d %-10d%n",
                    truncate(movie.getTitle(), 50), movie.getNominations(), movie.getAwards());
        }
    }

    // Helper method to truncate long titles for display
    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    public static void main(String[] args) {
        try {
            AjaxScraper scraper = new AjaxScraper();
            for (int i = 0; i < 6; i++) {
                int year = 2010 + i;
                List<Movie> movies = scraper.scrapeMovies(year);
                scraper.printMovies("Movies for " + year + ":", movies);
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}