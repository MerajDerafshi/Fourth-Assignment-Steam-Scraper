import java.util.Objects;

public class Movie {
    private String title;
    private int nominations;
    private int awards;

    public Movie(String title, int nominations, int awards) {
        this.title = title;
        this.nominations = nominations;
        this.awards = awards;
    }

    public String getTitle() {
        return title;
    }

    public int getNominations() {
        return nominations;
    }

    public int getAwards() {
        return awards;
    }

    @Override
    public String toString() {
        return "Movie{title='" + title + "', nominations=" + nominations + ", awards=" + awards + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return nominations == movie.nominations &&
                awards == movie.awards &&
                Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, nominations, awards);
    }
}