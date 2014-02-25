package mp.agencja.puzzle.memory.fit.managers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import mp.agencja.puzzle.memory.fit.games.GameActivity;


public class ResourcesManager {
    private static final String TAG_LOG = "ResourcesManager";
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    public AssetManager assetManager;
    public TextureManager textureManager;
    public Engine engine;
    public GameActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vbom;
    private TexturePack texturePack;
    private TexturePackTextureRegionLibrary texturePackLibrary;
    public Font font;
    // **************************************
    // TEXTURE REGIONS
    // **************************************
    public ITextureRegion bg_splash;
    public ITextureRegion bg_menu;

    public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        getInstance().textureManager = activity.getTextureManager();
        getInstance().assetManager = activity.getAssets();
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public void loadSplashSceneResources() {
        Log.w(TAG_LOG, "loadSplashSceneResources");
        try {
            texturePack = new TexturePackLoader(textureManager, "game_gfx/texture_splash/").loadFromAsset(assetManager, "bg_splash.xml");
            texturePack.loadTexture();
            texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
            bg_splash = texturePackLibrary.get(0);
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }
    }

    public void unloadSplashSceneResources() {
        Log.w(TAG_LOG, "unloadSplashSceneResources");
        texturePack.unloadTexture();
        bg_splash = null;
    }

    public void loadMenuSceneResources() {
        Log.w(TAG_LOG, "loadMenuSceneResources");
        try {
            texturePack = new TexturePackLoader(textureManager, "game_gfx/texture_menu/").loadFromAsset(assetManager, "bg_menu.xml");
            texturePack.loadTexture();
            texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
            bg_menu = texturePackLibrary.get(0);

            font = FontFactory.create(activity.getFontManager(), textureManager, 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 40);
            font.load();
        } catch (Exception e) {
            Debug.e(e);
        }
    }

    public void unloadMenuSceneResources() {
        Log.w(TAG_LOG, "unloadMenuSceneResources");
        texturePack.unloadTexture();
        bg_menu = null;
    }

    public void loadGameSceneResources(){
        Log.w(TAG_LOG, "loadGameSceneResources");
    }

    public void unloadGameSceneResources() {
        Log.w(TAG_LOG, "unloadGameSceneResources");
    }
}
