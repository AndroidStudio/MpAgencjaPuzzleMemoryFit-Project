package mp.agencja.puzzle.memory.fit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import mp.agencja.puzzle.memory.fit.games.GameActivity;
import mp.agencja.puzzle.memory.fit.games.GamesParallax;

public class GameListScene extends Activity {
    private RelativeLayout optionsLayout;
    private boolean expanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelist_scene);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageButton btnFx = (ImageButton) findViewById(R.id.btnFx);
        ImageButton btnVolume = (ImageButton) findViewById(R.id.btnVolume);

        btnSettings.setOnTouchListener(onSettingsTouchListener);
        btnFx.setOnTouchListener(onFxTouchListener);
        btnVolume.setOnTouchListener(onVolumeTouchListener);

        ImageButton puzzle_game_button = (ImageButton) findViewById(R.id.puzzle_game_button);
        ImageButton memory_game_button = (ImageButton) findViewById(R.id.memory_game_button);
        ImageButton sounds_game_button = (ImageButton) findViewById(R.id.sounds_game_button);
        ImageButton adventure_game_button = (ImageButton) findViewById(R.id.adventure_game_button);

        puzzle_game_button.setOnTouchListener(onPuzzleButtonTouchListener);
        memory_game_button.setOnTouchListener(onMemoryButtonTouchListener);
        sounds_game_button.setOnTouchListener(onSoundsButtonTouchListener);
        adventure_game_button.setOnTouchListener(onAdventureButtonTouchLisstener);

        optionsLayout = (RelativeLayout) findViewById(R.id.optionsLayout);
    }
    private final ImageButton.OnTouchListener onAdventureButtonTouchLisstener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "adventure_game_scene");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onSoundsButtonTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "sounds_game_scene");
            }
            return false;
        }
    };

    private final ImageButton.OnTouchListener onMemoryButtonTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "memory_game_scene");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onPuzzleButtonTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "puzzle_game_scene");
            }
            return false;
        }
    };

    private final ImageButton.OnTouchListener onSettingsTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
                if (!expanded) {
                    optionsLayout.setVisibility(View.GONE);
                    expanded = true;
                } else {
                    optionsLayout.setVisibility(View.VISIBLE);
                    expanded = false;
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "none");
            }
            return false;
        }
    };

    private final ImageButton.OnTouchListener onFxTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audio.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
                        AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "none");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onVolumeTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "none");
            }
            return false;
        }
    };

    private void scaleAnimation(float from, float to, View view, final String action) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(
                from, to, from, to,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(300);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation anim) {
            }

            public void onAnimationRepeat(Animation anim) {
            }

            public void onAnimationEnd(Animation anim) {
                if (action.equals("puzzle_game_scene")) {
                    Intent intent = new Intent(GameListScene.this, GamesParallax.class);
                    intent.putExtra("game_type","puzzle_game_scene");
                    startActivity(intent);
                }else if(action.equals("memory_game_scene")){
                    Intent intent = new Intent(GameListScene.this, GamesParallax.class);
                    intent.putExtra("game_type","memory_game_scene");
                    startActivity(intent);
                }else if(action.equals("sounds_game_scene")){
                    Intent intent = new Intent(GameListScene.this, GamesParallax.class);
                    intent.putExtra("game_type","sounds_game_scene");
                    startActivity(intent);
                }else if(action.equals("adventure_game_scene")){
                    Intent intent = new Intent(GameListScene.this, GameActivity.class);
                    startActivity(intent);
                }
            }
        });
        view.startAnimation(scaleAnimation);
    }

    public void onHomeClick(View view) {
        finish();
    }
}
