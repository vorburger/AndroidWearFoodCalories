package calories.fit.vorburger.ch.foodcalories;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PersistentNamedValuePair<T> extends NamedValuePair<T> {

    private final Activity activity;

    public PersistentNamedValuePair(Activity context, String name, T defaultValue) {
        super(name, null);
        if (defaultValue == null)
            throw new IllegalArgumentException("defaultValue == null");
        this.activity = context;
        setValue(getPersistent(defaultValue));
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        put();
    }

    private <T> T getPersistent(T defaultValue) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if (defaultValue instanceof Integer) {
            Integer i = sharedPref.getInt(getName(), (Integer) defaultValue);
            return (T) i;
        // TODO handle Boolean, Flot, Long, Set<String>
        } else {
            throw new IllegalArgumentException(getValue().toString());
        }
    }

    private <T> void put() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (getValue() instanceof Integer) {
            editor.putInt(getName(), (Integer) getValue());
        // TODO handle Boolean, Flot, Long, Set<String>
        } else {
            throw new IllegalArgumentException(getValue().toString());
        }
        editor.commit();
    }

}
