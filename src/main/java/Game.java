import java.util.Objects;

public class Game {
    private String name;
    private double rating;
    private int price;

    public Game(String name, double rating, int price) {
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Game{name='" + name + "', rating=" + rating + ", price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(game.rating, rating) == 0 && price == game.price && Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rating, price);
    }
}