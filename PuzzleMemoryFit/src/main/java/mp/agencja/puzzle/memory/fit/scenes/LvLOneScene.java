package mp.agencja.puzzle.memory.fit.scenes;

import android.util.Log;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;

import mp.agencja.puzzle.memory.fit.managers.SceneManager;

public class LvLOneScene extends BaseScene {
    private static final String TAG_LOG = "LvLOneScene";

    @Override
    public void createScene() {
        setBackground(new Background(Color.RED));
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.LVL_ONE_SCENE;
    }

    @Override
    public void onBackKeyPressed() {
        Log.w(TAG_LOG, "onBackKeyPressed click");
        disposeScene();
        SceneManager.getInstance().backToMenuScene();
    }

    @Override
    public void disposeScene() {
    }
}
