package br.com.dextra.tamagotchi;

import br.com.dextra.tamagotchi.asynctask.DownloadImageAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private int life;
	private int xp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new DownloadImageAsyncTask()
				.execute("http://www.plugmasters.com.br/downloads/icones/banco-de-icones/flats/flats-dragon.png");

		initialize(savedInstanceState);
		bindButtons();

		updateScreenXp();
		updateScreenLife();

		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_normal);
	}

	private void bindButtons() {
		findViewById(R.id_main.feed).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				life += 20;
				updateScreenLife();
			}
		});
		findViewById(R.id_main.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				xp += 10;
				updateScreenXp();
			}
		});
		findViewById(R.id_main.kill).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				life -= 10;
				updateScreenLife();
			}
		});
	}

	private void updateScreenLife() {
		((TextView) findViewById(R.id_main.info_life)).setText("LIFE: " + life);
	}

	private void updateScreenXp() {
		((TextView) findViewById(R.id_main.info_xp)).setText("XP: " + xp);
	}

	private void initialize(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate -> initialize");
		if (savedInstanceState == null) {
			Log.d(TAG + "onCreate -> initialize", "No previous state");
			life = 0;
			xp = 0;
		} else {
			Log.d(TAG + "onCreate -> initialize", "Recovering previous state");
			life = savedInstanceState.getInt("life");
			xp = savedInstanceState.getInt("xp");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		outState.putInt("xp", xp);
		outState.putInt("life", life);
	}
}
