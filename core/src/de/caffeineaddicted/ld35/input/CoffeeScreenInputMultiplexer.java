package de.caffeineaddicted.ld35.input;

import com.badlogic.gdx.InputProcessor;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by malte on 4/17/16.
 */
public class CoffeeScreenInputMultiplexer implements InputProcessor {
    private Map<CoffeeScreen, InputProcessor> processors;

    public CoffeeScreenInputMultiplexer() {
        processors = new HashMap<CoffeeScreen, InputProcessor>();
    }

    public void addProcessor(CoffeeScreen screen, InputProcessor processor) {
        screen.game.error(screen.getClass().getSimpleName() + " added Processor " + processor.getClass().getSimpleName());
        if (screen == null) throw new NullPointerException("screen cannot be null");
        if (processor == null) throw new NullPointerException("processor cannot be null");
        processors.put(screen, processor);
    }

    public void removeProcessor(CoffeeScreen screen) {
        if (screen == null) throw new NullPointerException("screen cannot be null");
        processors.remove(screen);
    }

    /**
     * @return the number of processors in this multiplexer
     */
    public int size() {
        return processors.size();
    }

    public void clear() {
        processors.clear();
    }

    public Map<CoffeeScreen, InputProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(Map<CoffeeScreen, InputProcessor> processors) {
        this.processors = processors;
    }

    public boolean keyDown(int keycode) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).keyDown(keycode);
        return false;
    }

    public boolean keyUp(int keycode) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).keyUp(keycode);
        return false;
    }

    public boolean keyTyped(char character) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).keyTyped(character);
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null)
                processors.get(s).touchDown(screenX, screenY, pointer, button);
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null)
                processors.get(s).touchUp(screenX, screenY, pointer, button);
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Set<CoffeeScreen> k = new HashSet(processors.keySet());
        for (CoffeeScreen s : k)
            if (s.isVisible() && processors.get(s) != null) processors.get(s).scrolled(amount);
        return false;
    }
}
