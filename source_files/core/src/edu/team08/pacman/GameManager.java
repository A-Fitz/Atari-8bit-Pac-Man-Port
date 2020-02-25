package edu.team08.pacman;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class GameManager implements Disposable {

    public static final GameManager instance = new GameManager();
    public AssetManager assetManager;

    GameManager() {
        assetManager = new AssetManager();
        assetManager.load("sprites/actors.atlas", TextureAtlas.class);

        assetManager.finishLoading();
    }


    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
