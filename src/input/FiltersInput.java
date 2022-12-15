package input;

import java.util.ArrayList;

/**
 * This class is used for getting the inputs from files
 */
public final class FiltersInput {
    private Sort sort;
    private Contains contains;

    public static final class Sort {
        private String rating;
        private String duration;

        public Sort() {
        }

        public String getRating() {
            return rating;
        }

        public String getDuration() {
            return duration;
        }
    }

    public static final class Contains {
        private ArrayList<String> actors;
        private ArrayList<String> genre;

        public Contains() {
        }

        public ArrayList<String> getActors() {
            return actors;
        }

        public ArrayList<String> getGenre() {
            return genre;
        }
    }

    public FiltersInput() {
    }

    public Sort getSort() {
        return sort;
    }

    public Contains getContains() {
        return contains;
    }
}
