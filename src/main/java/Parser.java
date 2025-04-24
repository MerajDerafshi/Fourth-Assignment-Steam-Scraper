import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Parser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        Collections.sort(sortedByName, Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        Collections.sort(sortedByRating, Comparator.comparingDouble(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        Collections.sort(sortedByPrice, Comparator.comparingInt(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        // Load HTML file from Resources
        InputStream is = Parser.class.getResourceAsStream("/Video_Games.html");
        if (is == null) {
            throw new IOException("Video_Games.html not found in Resources");
        }
        Document doc = Jsoup.parse(is, "UTF-8", "");
        is.close();

        // Extract data from HTML
        Elements gameElements = doc.select(".game");

        // Iterate through each Game div to extract Game data
        for (Element gameElement : gameElements) {
            String name = gameElement.selectFirst(".game-name").text();
            String ratingText = gameElement.selectFirst(".game-rating").text();
            String priceText = gameElement.selectFirst(".game-price").text();

            // Parse rating (e.g., "4.8/5" -> 4.8)
            double rating;
            try {
                rating = Double.parseDouble(ratingText.split("/")[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid rating format for " + name + ": " + ratingText);
                continue;
            }

            // Parse price (e.g., "91 €" -> 91)
            int price;
            try {
                price = Integer.parseInt(priceText.replace(" €", ""));
            } catch (NumberFormatException e) {
                System.err.println("Invalid price format for " + name + ": " + priceText);
                continue;
            }

            games.add(new Game(name, rating, price));
        }
    }

    // Method to display games
    public void printGames(String title, List<Game> games) {
        System.out.println("\n" + title);
        System.out.printf("%-50s %-10s %-10s%n", "Name", "Rating", "Price (€)");
        System.out.println("-".repeat(70));
        for (Game game : games) {
            System.out.printf("%-50s %-10.2f %-10d%n",
                    truncate(game.getName(), 50), game.getRating(), game.getPrice());
        }
    }

    // Helper method to truncate long names for display
    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            parser.setUp();

            // Sort and display
            parser.printGames("Sorted by Name:", parser.sortByName());
            parser.printGames("Sorted by Rating:", parser.sortByRating());
            parser.printGames("Sorted by Price:", parser.sortByPrice());

        } catch (IOException e) {
            System.err.println("Error in setup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}