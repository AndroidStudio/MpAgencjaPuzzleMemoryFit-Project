package mp.agencja.puzzle.memory.fit.scenes;

import android.app.Activity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import mp.agencja.puzzle.memory.fit.managers.ResourcesManager;
import mp.agencja.puzzle.memory.fit.managers.SceneManager;


public abstract class BaseScene extends Scene {

	protected Engine engine;
	protected Activity activity;
	protected ResourcesManager resourcesManager;
	protected VertexBufferObjectManager vbom;
	protected BoundCamera camera;

	public BaseScene() {
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = resourcesManager.engine;
		this.activity = resourcesManager.activity;
		this.vbom = resourcesManager.vbom;
		this.camera = resourcesManager.camera;
		createScene();
	}

	public abstract void createScene();
	public abstract SceneManager.SceneType getSceneType();
	public abstract void onBackKeyPressed();

	public abstract void disposeScene();
}