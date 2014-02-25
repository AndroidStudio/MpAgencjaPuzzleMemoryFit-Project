package mp.agencja.puzzle.memory.fit.managers;

import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import mp.agencja.puzzle.memory.fit.scenes.BaseScene;
import mp.agencja.puzzle.memory.fit.scenes.LvLOneScene;
import mp.agencja.puzzle.memory.fit.scenes.LvLTwoScene;
import mp.agencja.puzzle.memory.fit.scenes.MenuScene;
import mp.agencja.puzzle.memory.fit.scenes.SplashScene;

public class SceneManager {
    private static final SceneManager INSTANCE = new SceneManager();
    private Engine engine = ResourcesManager.getInstance().engine;
    private SceneType currentSceneType = SceneType.SPLASH_SCENE;
    private static final String TAG_LOG = "SceneManager";
    private BaseScene currentScene;

    public enum SceneType {
        SPLASH_SCENE, MENU_SCENE, LVL_ONE_SCENE, LVL_TWO_SCENE
    }

    public void setScene(BaseScene scene) {
        currentSceneType = scene.getSceneType();
        engine.setScene(scene);
    }

    public void setScene(SceneType sceneType) {
        switch (sceneType) {
            case SPLASH_SCENE:
                setScene(currentScene);
                break;
            case MENU_SCENE:
                setScene(currentScene);
                break;
            case LVL_ONE_SCENE:
                setScene(currentScene);
                break;
            case LVL_TWO_SCENE:
                setScene(currentScene);
                break;
            default:
                break;
        }
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }

    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        currentScene = new SplashScene();
        pOnCreateSceneCallback.onCreateSceneFinished(currentScene);
    }

    public void createMenuScene() {
        //dispose splash
        currentScene.disposeScene();

        ResourcesManager.getInstance().loadMenuSceneResources();
        ResourcesManager.getInstance().loadGameSceneResources();
        currentScene = new MenuScene();
        setScene(SceneType.MENU_SCENE);
        Log.w(TAG_LOG, "Scene type: " + getCurrentSceneType());
    }

    public void createLvLOneScene() {
        currentScene = new LvLOneScene();
        setScene(SceneType.LVL_ONE_SCENE);
        Log.w(TAG_LOG, "Scene type: " + getCurrentSceneType());
    }

    public void createLvLTwoScene() {
        currentScene = new LvLTwoScene();
        setScene(SceneType.LVL_TWO_SCENE);
        Log.w(TAG_LOG, "Scene type: " + getCurrentSceneType());
    }

    public void backToMenuScene() {
        currentScene = new MenuScene();
        setScene(SceneType.MENU_SCENE);
        Log.w(TAG_LOG, "Scene type: " + getCurrentSceneType());
    }
}
