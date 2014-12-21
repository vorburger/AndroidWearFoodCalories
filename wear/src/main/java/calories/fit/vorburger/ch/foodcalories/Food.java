package calories.fit.vorburger.ch.foodcalories;

public class Food {

    /**
     * Name, e.g. "big Apple"
     */
    public final String name;

    /**
     * Grammatically correct article prefix, e.g. "an" (or "une" etc. in French)
     */
    public final String articlePrefix;

    public final int calories;

    public Food(String articlePrefix, String name, int calories) {
        this.articlePrefix = articlePrefix;
        this.name = name;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return articlePrefix + " " + name +  " (" + calories + ")";
    }
}
