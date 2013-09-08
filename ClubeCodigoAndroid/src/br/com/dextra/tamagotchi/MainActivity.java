package br.com.dextra.tamagotchi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int DELAY_ACTION = 2000;

	private int life;
	private int xp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize(savedInstanceState);
		bindButtonLife();
		bindButtonPlay();
		bindButtonKill();

		updateScreenXp();
		updateScreenLife();

		updateScreenNormalImage();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		outState.putInt("xp", xp);
		outState.putInt("life", life);
	}

	private void bindButtonLife() {
		findViewById(R.id_main.feed).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				life += 20;

				disableAllButtons();
				updateScreenFeedImage();

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						updateScreenNormalImage();
						updateScreenLife();
					}
				});

			}
		});

	}

	private void bindButtonPlay() {
		findViewById(R.id_main.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				xp += 10;

				disableAllButtons();
				updateScreenPlayImage();

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						updateScreenNormalImage();
						updateScreenXp();
					}
				});
			}
		});
	}

	private void bindButtonKill() {
		findViewById(R.id_main.kill).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				life -= 10;
				xp += 10;

				disableAllButtons();
				updateScreenKillImage();

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						updateScreenNormalImage();
						updateScreenLife();
						updateScreenXp();
					}
				});
			}
		});
	}

	private void initialize(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate -> initialize");
		if (savedInstanceState == null) {
			Log.d(TAG + "onCreate -> initialize", "No previous state");
			life = 100;
			xp = 0;
		} else {
			Log.d(TAG + "onCreate -> initialize", "Recovering previous state");
			life = savedInstanceState.getInt("life");
			xp = savedInstanceState.getInt("xp");
		}
	}

	private void enableAllButtons() {
		findViewById(R.id_main.feed).setEnabled(true);
		findViewById(R.id_main.kill).setEnabled(true);
		findViewById(R.id_main.play).setEnabled(true);
	}

	private void disableAllButtons() {
		findViewById(R.id_main.feed).setEnabled(false);
		findViewById(R.id_main.kill).setEnabled(false);
		findViewById(R.id_main.play).setEnabled(false);
	}

	private void delay(int delay, Runnable run) {
		new Handler().postDelayed(run, delay);
	}

	private void updateScreenLife() {
		((ProgressBar) findViewById(R.id_main.progress_life)).setProgress(life);
	}

	private void updateScreenXp() {
		((TextView) findViewById(R.id_main.info_xp)).setText("XP: " + xp);
	}

	private void updateScreenNormalImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_normal);
	}

	private void updateScreenKillImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_kill);
	}

	private void updateScreenPlayImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_play);
	}

	private void updateScreenFeedImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_feed);
	}
}
