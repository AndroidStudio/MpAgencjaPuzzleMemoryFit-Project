package mp.agencja.puzzle.memory.fit;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusShare;


public class MainMenuScene extends Activity {
    private RelativeLayout optionsLayout;
    private boolean expanded = true;
    private boolean canPresentShareDialog;
    private UiLifecycleHelper uiHelper;
    private int REQUEST_CODE_INTERACTIVE_POST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_scene);

        findViewById(R.id.home_button).setVisibility(View.INVISIBLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        String density = null;

        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                density = "LOW";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                density = "MEDIUM";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                density = "HIGH";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                density = "XHIGH";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                density = "XXHIGH";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                density = "XXXHIGH";
                break;
            case DisplayMetrics.DENSITY_400:
                density = "400";
                break;
            case DisplayMetrics.DENSITY_TV:
                density = "TV";
                break;
        }
        String screenSize;
        switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                screenSize = "xLarge screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenSize = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenSize = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenSize = "Small screen";
                break;
            default:
                screenSize = "Undefined";
                break;
        }
        String ratio;
        if (width / height > 1.67) {
            ratio = "Long";
        } else {
            ratio = "NotLong";
        }

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Name: " + getDeviceName() + "\nWidth: " + width + "px\nHeight: " + height
                + "px\nDensity: " + density + "\nScreen Size: " + screenSize + "\nAspect ratio: " + ratio + " (" + width / calculateratio(width, height) + ":" + height / calculateratio(width, height) + ")"
                + "\nHeap size: " + Integer.toString(memoryClass) + " MB")
                .setTitle("Device info:")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.SHARE_DIALOG);

        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageButton btnFx = (ImageButton) findViewById(R.id.btnFx);
        ImageButton btnVolume = (ImageButton) findViewById(R.id.btnVolume);

        btnSettings.setOnTouchListener(onSettingsTouchListener);
        btnFx.setOnTouchListener(onFxTouchListener);
        btnVolume.setOnTouchListener(onVolumeTouchListener);

        ImageButton btnStart = (ImageButton) findViewById(R.id.btnStart);
        ImageButton btnGoogle = (ImageButton) findViewById(R.id.btnGoogle);
        ImageButton btnRate = (ImageButton) findViewById(R.id.btnRate);

        ImageButton btnFacebook = (ImageButton) findViewById(R.id.btnFacebook);

        btnStart.setOnTouchListener(onStartTouchListener);
        btnGoogle.setOnTouchListener(onGoogleTouchListener);
        btnRate.setOnTouchListener(onRateTouchListener);
        btnFacebook.setOnTouchListener(onFacebookTouchListener);

        findViewById(R.id.menuLayout).setVisibility(View.VISIBLE);
        optionsLayout = (RelativeLayout) findViewById(R.id.optionsLayout);
    }

    private int calculateratio(int a, int b) {
        return (b == 0) ? a : calculateratio(b, a % b);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

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

    private final ImageButton.OnTouchListener onStartTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "start");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onGoogleTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "google");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onRateTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "none");
            }
            return false;
        }
    };
    private final ImageButton.OnTouchListener onFacebookTouchListener = new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                scaleAnimation(1f, 0.75f, view, "none");
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                scaleAnimation(0.75f, 1f, view, "facebook");
            }
            return false;
        }
    };

    private void setMessage() {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setName("Puzzle Memory Fit")
                    .setDescription("Gry dla dzieci")
                    .setLink("http://www.androidstudio.pl")
                    .setPicture("http://www.androidstudio.pl/animal_adventure.png")
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else {
            Toast.makeText(this, "Please install facebook aplication", Toast.LENGTH_LONG).show();
        }
    }

    private void scaleAnimation(float from, float to, View view, final String action) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(300);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation anim) {
            }

            public void onAnimationRepeat(Animation anim) {
            }

            public void onAnimationEnd(Animation anim) {
                if (action.equals("start")) {
                    Intent intent = new Intent(MainMenuScene.this, GameListScene.class);
                    startActivity(intent);
                } else if (action.equals("facebook")) {
                    setMessage();
                } else if (action.equals("google")) {
                    int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainMenuScene.this);
                    if (available != ConnectionResult.SUCCESS) {
                        Toast.makeText(MainMenuScene.this, "Please install GooglePlayService", Toast.LENGTH_LONG).show();
                    } else {
                        Intent shareIntent = new PlusShare.Builder(MainMenuScene.this)
                                .setType("text/plain")
                                .setText("Puzzle Memory Fit")
                                .setContentUrl(Uri.parse("http://www.androidstudio.pl/animal_adventure.png"))
                                .getIntent();

                        startActivityForResult(shareIntent, REQUEST_CODE_INTERACTIVE_POST);
                    }
                }
            }
        });
        view.startAnimation(scaleAnimation);
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTERACTIVE_POST) {
            if (resultCode == RESULT_OK) {
                Log.e("succes", "Succes create google+ post");
            } else {
                Log.e("error", "canceled");
            }
        }

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
}
