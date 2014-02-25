package mp.agencja.puzzle.memory.fit.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mp.agencja.puzzle.memory.fit.R;

public class SoundsGameScene extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.soundsgame_scene, container, false);
        return view;
    }
}
