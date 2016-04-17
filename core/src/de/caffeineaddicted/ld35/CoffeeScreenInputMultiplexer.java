package de.caffeineaddicted.ld35;

import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by malte on 4/17/16.
 */
    public class CoffeeScreenInputMultiplexer implements InputProcessor {
        private Map<CoffeeScreen, InputProcessor> processors;

        public CoffeeScreenInputMultiplexer () {
            processors = new HashMap<CoffeeScreen, InputProcessor>();
        }

        public void addProcessor (CoffeeScreen screen, InputProcessor processor) {
            if (screen == null) throw new NullPointerException("screen cannot be null");
            if (processor == null) throw new NullPointerException("processor cannot be null");
            processors.put(screen, processor);
        }

        public void removeProcessor (CoffeeScreen screen) {
            if (screen == null) throw new NullPointerException("screen cannot be null");
            processors.remove(screen);
        }

        /** @return the number of processors in this multiplexer */
        public int size () {
            return processors.size();
        }

        public void clear () {
            processors.clear();
        }

        public Map<CoffeeScreen, InputProcessor> getProcessors () {
            return processors;
        }

        public void setProcessors (Map<CoffeeScreen, InputProcessor> processors) {
            this.processors = processors;
        }

        public boolean keyDown (int keycode) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().keyDown(keycode)) return true;
            return false;
        }

        public boolean keyUp (int keycode) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().keyUp(keycode)) return true;
            return false;
        }

        public boolean keyTyped (char character) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().keyTyped(character)) return true;
            return false;
        }

        public boolean touchDown (int screenX, int screenY, int pointer, int button) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().touchDown(screenX, screenY, pointer, button)) return true;
            return false;
        }

        public boolean touchUp (int screenX, int screenY, int pointer, int button) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().touchUp(screenX, screenY, pointer, button)) return true;
            return false;
        }

        public boolean touchDragged (int screenX, int screenY, int pointer) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().touchDragged(screenX, screenY, pointer)) return true;
            return false;
        }

        @Override
        public boolean mouseMoved (int screenX, int screenY) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().mouseMoved(screenX, screenY)) return true;
            return false;
        }

        @Override
        public boolean scrolled (int amount) {
            for (Map.Entry<CoffeeScreen, InputProcessor> pair: processors.entrySet())
                if (pair.getKey().isVisible() && pair.getValue().scrolled(amount)) return true;
            return false;
        }
    }
