package de.caffeineaddicted.ld35.logic;

/**
 * Created by Malte on 17.04.2016.
 */

import java.util.HashMap;
import java.util.Map;

public class Bundle {

    private Map<String, Object> context;

    public Bundle() {
        this(null);
    }

    public Bundle(Bundle bundle) {
        this.context = new HashMap<String, Object>();
        if (bundle != null) {
            this.context.putAll(bundle.getAll());
        }
    }

    public Bundle put(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public Object get(String key) {
        return context.get(key);
    }

    public Object get(String key, Object defaultValue) {
        Object value = context.get(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public <T> T get(String key, Class<T> type) {
        return (T) get(key);
    }

    public <T> T get(String key, Class<T> type, T defaultValue) {
        T value = (T) get(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public Map<String, Object> getAll() {
        return context;
    }

    public boolean hasKey(String key) {
        return context.containsKey(key);
    }
}