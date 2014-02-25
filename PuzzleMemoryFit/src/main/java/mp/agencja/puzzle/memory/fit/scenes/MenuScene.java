package mp.agencja.puzzle.memory.fit.scenes;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import mp.agencja.puzzle.memory.fit.managers.SceneManager;

public class MenuScene extends BaseScene {
    private static final String TAG_LOG = "MenuScene";
    private Sprite bg_menu;
    private Text levelOneButton,levelTwoButton;

    @Override
    public void createScene() {
        bg_menu = new Sprite(400, 240, resourcesManager.bg_menu, vbom);
        levelOneButton = new Text(400, 150, resourcesManager.font, "Start lvl 1", new TextOptions(HorizontalAlign.CENTER), vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                SceneManager.getInstance().createLvLOneScene();
                return false;
            }
        };
        levelTwoButton = new Text(400, 100, resourcesManager.font, "Start lvl 2", new TextOptions(HorizontalAlign.CENTER), vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                SceneManager.getInstance().createLvLTwoScene();
                return false;
            }
        };
        registerTouchArea(levelOneButton);
        registerTouchArea(levelTwoButton);

        attachChild(bg_menu);
        attachChild(levelOneButton);
        attachChild(levelTwoButton);
    }

    @Override
    public void onBackKeyPressed() {
        disposeScene();
        System.exit(0);
    }

    @Override
    public void disposeScene() {
        resourcesManager.unloadMenuSceneResources();
        resourcesManager.unloadGameSceneResources();

        bg_menu.setVisible(false);
        bg_menu.isIgnoreUpdate();
        bg_menu.detachSelf();
        bg_menu.dispose();

        levelOneButton.setVisible(false);
        levelOneButton.isIgnoreUpdate();
        levelOneButton.detachSelf();
        levelOneButton.dispose();

        levelTwoButton.setVisible(false);
        levelTwoButton.isIgnoreUpdate();
        levelTwoButton.detachSelf();
        levelTwoButton.dispose();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.MENU_SCENE;
    }

}
