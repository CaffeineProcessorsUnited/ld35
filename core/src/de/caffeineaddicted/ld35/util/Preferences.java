package de.caffeineaddicted.ld35.util;

import com.badlogic.gdx.Gdx;

import java.util.Map;

/**
 * Created by malte on 4/16/16.
 */
public class Preferences implements com.badlogic.gdx.Preferences {

    private final String filename;
    com.badlogic.gdx.Preferences backup, working;

    public Preferences(String filename) {
        this.filename = filename;
        reload();
    }

    public void reload() {
        backup = Gdx.app.getPreferences(filename);
        working = Gdx.app.getPreferences(filename);
    }

    @Override
    public com.badlogic.gdx.Preferences putBoolean(String key, boolean val) {
        return working.putBoolean(key, val);
    }

    @Override
    public com.badlogic.gdx.Preferences putInteger(String key, int val) {
        return working.putInteger(key, val);
    }

    @Override
    public com.badlogic.gdx.Preferences putLong(String key, long val) {
        return working.putLong(key, val);
    }

    @Override
    public com.badlogic.gdx.Preferences putFloat(String key, float val) {
        return working.putFloat(key, val);
    }

    @Override
    public com.badlogic.gdx.Preferences putString(String key, String val) {
        return working.putString(key, val);
    }

    @Override
    public com.badlogic.gdx.Preferences put(Map<String, ?> vals) {
        return working.put(vals);
    }

    @Override
    public boolean getBoolean(String key) {
        return working.getBoolean(key);
    }

    @Override
    public int getInteger(String key) {
        return working.getInteger(key);
    }

    @Override
    public long getLong(String key) {
        return working.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return working.getFloat(key);
    }

    @Override
    public String getString(String key) {
        return working.getString(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return working.getBoolean(key, defValue);
    }

    @Override
    public int getInteger(String key, int defValue) {
        return working.getInteger(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return working.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return working.getFloat(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return working.getString(key, defValue);
    }

    @Override
    public Map<String, ?> get() {
        return working.get();
    }

    @Override
    public boolean contains(String key) {
        return working.contains(key);
    }

    @Override
    public void clear() {
        working.clear();
    }

    @Override
    public void remove(String key) {
        working.remove(key);
    }

    @Override
    public void flush() {
        working.flush();
        reload();
    }

    public void abort() {
        backup.flush();
        reload();
    }
}
