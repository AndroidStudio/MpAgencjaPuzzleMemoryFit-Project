package mp.agencja.puzzle.memory.fit.games;

import android.content.ClipData;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mp.agencja.puzzle.memory.fit.CustomDragShadowBuldier;
import mp.agencja.puzzle.memory.fit.R;

public class PuzzleGameScene extends Fragment {
    private CustomDragShadowBuldier customDragShadowBuldier;
    private int score = 0;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.puzzlegame_scene, container, false);
        customDragShadowBuldier = new CustomDragShadowBuldier();
        createGameEngine();
        return rootView;
    }

    private void createGameEngine() {
        List<Integer> indexs = creatIndexList();
        List<Integer> randomChilds = randomChilds();
        final LinearLayout panel = (LinearLayout) rootView.findViewById(R.id.panel);
        final LinearLayout white_animals_panel = (LinearLayout) rootView.findViewById(R.id.white_animals_panel);

        for (int i = 0; i < panel.getChildCount(); i++) {
            final View animals_small = panel.getChildAt(i);
            assert animals_small != null;
            animals_small.setVisibility(View.VISIBLE);

            final Random rand_child = new Random();
            int random_child = rand_child.nextInt(randomChilds.size());
            final View white_animal = white_animals_panel.getChildAt(randomChilds.get(random_child));

            final Random rand = new Random();
            int random = rand.nextInt(indexs.size());

            ((ImageButton) animals_small).setImageResource(small_animals[indexs.get(random)]);
            animals_small.setOnTouchListener(onTouchAnimalListener);
            animals_small.setTag(indexs.get(random));

            assert white_animal != null;
            ((ImageButton) white_animal).setImageResource(white_animals[indexs.get(random)]);
            white_animal.setOnDragListener(new BoxDragListener());
            white_animal.setTag(indexs.get(random));

            indexs.remove(random);
            randomChilds.remove(random_child);
        }

    }

    private final ImageButton.OnTouchListener onTouchAnimalListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final ClipData clipData = ClipData.newPlainText(view.getTag().toString(), view.getTag().toString());
            customDragShadowBuldier.fromResource(getActivity(), animals[Integer.valueOf(view.getTag().toString())]);
            view.startDrag(clipData, customDragShadowBuldier, view, 2);
            view.setVisibility(View.GONE);
            return true;
        }
    };

    private List<Integer> randomChilds() {
        final List<Integer> rChilds = new ArrayList<Integer>(4);
        for (int i = 0; i < 4; i++) {
            rChilds.add(i);
        }
        return rChilds;
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

    private final int[] animals = new int[]{
            R.drawable.bear,
            R.drawable.bird,
            R.drawable.cat,
            R.drawable.chicken,
            R.drawable.cow,
            R.drawable.dog,
            R.drawable.horse};

    private final int[] white_animals = new int[]{
            R.drawable.bear_white,
            R.drawable.bird_white,
            R.drawable.cat_white,
            R.drawable.chicken_white,
            R.drawable.cow_white,
            R.drawable.dog_white,
            R.drawable.horse_white};

    private class BoxDragListener implements View.OnDragListener {
        private boolean entered = false;

        @Override
        public boolean onDrag(View view, DragEvent event) {
            if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                view.animate().scaleX(0.9f);
                view.animate().scaleY(0.9f);
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
                entered = true;
            } else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED) {
                entered = false;
            } else if (event.getAction() == DragEvent.ACTION_DROP) {
                View draged = (View) event.getLocalState();
                if (entered) {
                    assert draged != null;
                    if (view.getTag().toString().equals(draged.getTag().toString())) {
                        ((ImageButton) view).setImageResource(animals[Integer.valueOf(view.getTag().toString())]);
                        score++;
                        if (score == 4) {
                            Toast.makeText(getActivity(), "Done !!!", Toast.LENGTH_LONG).show();
                            //createGameEngine();
                            score = 0;
                        }
                    } else {
                        draged.setVisibility(View.VISIBLE);
                    }
                }
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                final boolean dropped = event.getResult();
                if (!dropped) {
                    View draged = (View) event.getLocalState();
                    assert draged != null;
                    draged.setVisibility(View.VISIBLE);
                }
                view.animate().scaleX(1.0f);
                view.animate().scaleY(1.0f);
            }
            return true;
        }
    }
}
