package mp.agencja.puzzle.memory.fit.scenes;

import org.andengine.entity.sprite.Sprite;

import mp.agencja.puzzle.memory.fit.managers.SceneManager;

public class SplashScene extends BaseScene {
    private Sprite bg_splash;

    @Override
    public void createScene() {
        bg_splash = new Sprite(400, 240, resourcesManager.bg_splash, vbom);
        attachChild(bg_splash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {
        resourcesManager.unloadSplashSceneResources();
        bg_splash.setVisible(false);
        bg_splash.isIgnoreUpdate();
        bg_splash.detachSelf();
        bg_splash.dispose();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SPLASH_SCENE;
    }
}
