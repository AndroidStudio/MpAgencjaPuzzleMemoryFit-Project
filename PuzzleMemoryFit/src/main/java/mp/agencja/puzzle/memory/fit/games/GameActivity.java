package mp.agencja.puzzle.memory.fit.games;

import android.view.KeyEvent;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

import mp.agencja.puzzle.memory.fit.CropResolutionPolicy;
import mp.agencja.puzzle.memory.fit.managers.SceneManager;


public class GameActivity extends BaseGameActivity {
	private BoundCamera camera;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

    @Override
    public EngineOptions onCreateEngineOptions() {
        BoundCamera camera = new BoundCamera(0, 0, 800, 480);
        final CropResolutionPolicy crp = new CropResolutionPolicy(800, 480);
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, crp, camera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        return engineOptions;
    }

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
		//ResourcesManager.prepareManager(this.mEngine, this, this.camera, getVertexBufferObjectManager());
		//ResourcesManager.getInstance().loadSplashSceneResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
        Scene  scene = new Scene();
        scene.setBackground(new Background(Color.RED));
      mEngine.setScene(scene);
	}

	@Override
	public void onPopulateScene(final Scene scene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		//scene.registerUpdateHandler(new TimerHandler(3F, true, new ITimerCallback() {
		//	@Override
		//	public void onTimePassed(final TimerHandler pTimerHandler) {
		//		scene.unregisterUpdateHandler(pTimerHandler);
		//		SceneManager.getInstance().createMenuScene();
		//	}
		//}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//	if (keyCode == KeyEvent.KEYCODE_BACK) {
		///	SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		//}
		return false;
	}
}
