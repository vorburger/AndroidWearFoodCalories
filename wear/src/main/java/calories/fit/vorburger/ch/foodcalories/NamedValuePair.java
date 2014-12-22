package calories.fit.vorburger.ch.foodcalories;

public class NamedValuePair<T> {

    private final String name;
    private T value;

    public NamedValuePair(String name, T initialValue) {
        if (name == null)
            throw new IllegalArgumentException("name == null");

        this.name = name;
        this.value = initialValue;
    }

    public final String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return getName() + "=(" + getValue().getClass().toString() + ")" + getValue().toString();
    }

}
