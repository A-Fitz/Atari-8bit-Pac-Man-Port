/**
 * Class: InputManager
 * <p>
 * Purpose: This class will manage keyboard and mouse input form the user.
 * <p>
 * Resource used: https://www.sitepoint.com/handling-player-input-in-cross-platform-games-with-libgdx/
 */
package edu.team08.pacman.managers;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;


public class InputManager implements InputProcessor {

    private static final InputManager instance = new InputManager();
    public ArrayList<KeyState> keyStates = new ArrayList<>();

    public InputManager() {
        for (int i = 0; i < 256; i++) keyStates.add(new KeyState(i));
    }

    public static InputManager getInstance() {
        return instance;
    }

    /**
     * keyDown: Gets called once when an event is fired. Store the key being held down as well
     *          as being pressed.
     * @param keycode int
     * @return boolean
     */
    @Override
    public boolean keyDown(int keycode) {
        keyStates.get(keycode).pressed = true;
        keyStates.get(keycode).down = true;

        return false;
    }

    /**
     * keyUp: When the key is released, need to set the key's down state and released state.
     * @param keycode int
     * @return boolean
     */
    @Override
    public boolean keyUp(int keycode) {
        keyStates.get(keycode).down = false;
        keyStates.get(keycode).released = true;
        keyStates.get(keycode).pressed = false;

        return false;
    }

    /**
     * isKeyPressed: Check state of supplied keys
     * @param key int
     * @return boolean
     */
    public boolean isKeyPressed(int key) {
        return keyStates.get(key).pressed;
    }

    /**
     * isKeyDown: Check state of supplied keys.
     * @param key int
     * @return boolean
     */
    public boolean isKeyDown(int key) {
        return keyStates.get(key).down;
    }

    /**
     * isKeyDown: Check state of supplied keys.
     * @param key int
     * @return boolean
     */
    public boolean isKeyReleased(int key) {
        return keyStates.get(key).released;
    }

    /**
     * update: for every keyState, set pressed and released state to false.
     */
    public void update() {
        for (int i = 0; i < 256; i++) {
            KeyState keyState = keyStates.get(i);
            keyState.pressed = false;
            keyState.released = false;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    // ----------------------------------------------------------------------------------
    //Most likely will not need to use these functions.
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * Class: InputState
     *
     * Purpose: This class is the state for all input the user uses.
     */
    public static class InputState {
        public boolean pressed = false;
        public boolean down = false;
        public boolean released = false;
    }

    /**
     * Class: KeyState
     *
     * Purpose: Keyboard key states for the user.
     */
    public static class KeyState extends InputState {
        public int key;

        public KeyState(int key) {
            this.key = key;
        }
    }
    // ----------------------------------------------------------------------------------

}

