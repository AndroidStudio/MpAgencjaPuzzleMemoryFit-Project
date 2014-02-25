package mp.agencja.puzzle.memory.fit.games;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import mp.agencja.puzzle.memory.fit.R;
import mp.agencja.puzzle.memory.fit.ViewPagerParallax;

public class GamesParallax extends FragmentActivity {
    private boolean expanded = false;
    private RelativeLayout optionsLayout;
    private ImageButton btnLeft;
    private ImageButton btnRight;
    private ViewPagerParallax viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parallax_scene);


        final ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        final ImageButton btnFx = (ImageButton) findViewById(R.id.btnFx);
        final ImageButton btnVolume = (ImageButton) findViewById(R.id.btnVolume);

        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnLeft.setOnTouchListener(onLeftTouchListener);
        btnRight.setOnTouchListener(onRightTouchListener);

        btnSettings.setOnTouchListener(onSettingsTouchListener);
        btnFx.setOnTouchListener(onFxTouchListener);
        btnVolume.setOnTouchListener(onVolumeTouchListener);

        optionsLayout = (RelativeLayout) findViewById(R.id.optionsLayout);

        final SlidePagerAdapter pageAdapter = new SlidePagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPagerParallax) findViewById(R.id.viewpager);
        viewPager.setMaxPages(3);
        viewPager.setBackgroundAsset();
        viewPager.setOnPageChangeListener(onPageChangeListener);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(2);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("game_type")) {
            String gameType = bundle.getString("game_type");
            if (gameType != null) {
                if (gameType.equals("puzzle_game_scene")) {
                    viewPager.setCurrentItem(0);
                } else if (gameType.equals("memory_game_scene")) {
                    viewPager.setCurrentItem(1);
                } else if (gameType.equals("sounds_game_scene")) {
                    viewPager.setCurrentItem(2);
                }
            }
        }

    }

    private final ViewPagerParallax.OnPageChangeListener onPageChangeListener = new ViewPagerParallax.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {
            if (i == 0) {
                btnLeft.setVisibility(View.INVISIBLE);
            } else if (i == 2) {
                btnRight.setVisibility(View.INVISIBLE);
            } else {
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private final ImageButton.OnTouchListener onSettingsTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
                if (expanded) {
                    optionsLayout.setVisibility(View.GONE);
                    expanded = false;
                } else {
                    optionsLayout.setVisibility(View.VISIBLE);
                    expanded = true;
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
                final AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
                final AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
                if (action.equals("left")) {
                    scaleAnimation.setFillAfter(false);
                } else if (action.equals("right")) {
                    scaleAnimation.setFillAfter(false);
                }
            }
        });
        view.startAnimation(scaleAnimation);
    }
    private final ImageButton.OnTouchListener onLeftTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "left");
            }
            return false;
        }
    };

    private final ImageButton.OnTouchListener onRightTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "right");
            }
            return false;
        }
    };
    public void onRightButonClick(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem != viewPager.getMaxPages() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        }
    }

    public void onLeftButonClick(View view) {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1, true);
        }
    }

    private class SlidePagerAdapter extends FragmentPagerAdapter {
        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PuzzleGameScene();
                case 1:
                    return new MemoryGameScene();
                case 2:
                    return new SoundsGameScene();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void onHomeClick(View view) {
        finish();
    }
}

