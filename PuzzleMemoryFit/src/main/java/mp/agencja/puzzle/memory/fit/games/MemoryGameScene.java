package mp.agencja.puzzle.memory.fit.games;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mp.agencja.puzzle.memory.fit.R;

public class MemoryGameScene extends Fragment implements View.OnClickListener {
    private Handler handler = new Handler();
    private int score = 0;
    private ImageButton lastTouched = null;
    private int lastTag = 0;
    private View view;
    private boolean allowClick = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memorygame_scene, container, false);
        createGame();
        return view;
    }

    private void createGame() {
        final List<Integer> animalList = creatIndexList();
        final List<Integer> selectedTreeAnimals = new ArrayList<Integer>(3);
        final List<ImageButton> imageButtons = new ArrayList<ImageButton>(6);

        for (int k = 0; k < 3; k++) {
            final Random rand_a = new Random();
            final int random_animal = rand_a.nextInt(animalList.size());
            final int animal = animalList.get(random_animal);
            selectedTreeAnimals.add(animal);
            selectedTreeAnimals.add(animal);
            animalList.remove(random_animal);
        }


        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.table);

        final int tableCount = tableLayout.getChildCount();
        for (int i = 0; i < tableCount; i++) {
            final View row = tableLayout.getChildAt(i);
            if (row instanceof TableRow) {
                final int rowCount = ((ViewGroup) row).getChildCount();
                for (int j = 0; j < rowCount; j++) {
                    final ImageButton imageButton = ((ImageButton) ((ViewGroup) row).getChildAt(j));
                    if (imageButton != null) {
                        imageButton.setOnClickListener(this);
                        imageButtons.add(imageButton);
                    }
                }
            }
        }

        for (ImageButton imageButton : imageButtons) {
            final Random rand_a = new Random();
            final int random_animal = rand_a.nextInt(selectedTreeAnimals.size());
            final int animal = selectedTreeAnimals.get(random_animal);
            final int animalId = small_animals[animal];
            imageButton.setTag(animalId);
            selectedTreeAnimals.remove(random_animal);
        }
    }

    private List<Integer> creatIndexList() {
        final List<Integer> indexs = new ArrayList<Integer>(7);
        for (int i = 0; i < 7; i++) {
            indexs.add(i);
        }
        return indexs;
    }

    private final int[] small_animals = new int[]{
            R.drawable.bear_small,
            R.drawable.bird_small,
            R.drawable.cat_small,
            R.drawable.chicken_small,
            R.drawable.cow_small,
            R.drawable.dog_small,
            R.drawable.horse_small};

    @Override
    public void onClick(final View view) {
        if (allowClick) {
            final ImageButton imageButton = ((ImageButton) view);
            final int tag = Integer.valueOf(view.getTag().toString());
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_left_in);
            if (set != null) {
                set.setTarget(imageButton);
                set.start();
            }
            imageButton.setBackgroundResource(R.drawable.memory_box_empty);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        imageButton.setImageResource(tag);
                    } catch (Exception ignored) {

                    }
                }
            }, 300);
            if (lastTag == tag && lastTouched != imageButton && lastTouched != null) {
                allowClick = false;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            scaleAnimation(0.5f, 1.15f, lastTouched, "memory");
                            scaleAnimation(0.5f, 1.15f, imageButton, "memory");
                            lastTouched = null;
                            score++;
                            if (score == 3) {
                                Toast.makeText(getActivity(), "Done !!!", Toast.LENGTH_LONG).show();
                                score = 0;
                            }
                            allowClick = true;
                        } catch (Exception ignored) {

                        }
                    }
                }, 1300);
            } else if (lastTouched != null && lastTag != tag) {
                allowClick = false;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_left_in);
                            if (set != null) {
                                set.setTarget(imageButton);
                                set.start();
                            }
                            AnimatorSet setTouched = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.card_flip_left_in);
                            if (setTouched != null) {
                                setTouched.setTarget(lastTouched);
                                setTouched.start();
                            }
                            lastTouched.setImageDrawable(null);
                            imageButton.setImageDrawable(null);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        lastTouched.setBackgroundResource(R.drawable.memory_box);
                                        imageButton.setBackgroundResource(R.drawable.memory_box);
                                        lastTouched = null;
                                        allowClick = true;
                                    } catch (Exception ignored) {
                                    }
                                }
                            }, 300);
                        } catch (Exception ignored) {
                        }
                    }
                }, 2000);
            } else {
                lastTouched = imageButton;
                lastTag = tag;
            }
        }
    }

    private void scaleAnimation(float from, float to, final View view, final String action) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(300);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation anim) {
            }

            public void onAnimationRepeat(Animation anim) {
            }

            public void onAnimationEnd(Animation anim) {
                if (action.equals("memory")) {
                    final AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setFillAfter(false);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            scaleAnimation.setFillAfter(false);
                            view.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.startAnimation(alphaAnimation);
                }
            }
        });
        view.startAnimation(scaleAnimation);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
