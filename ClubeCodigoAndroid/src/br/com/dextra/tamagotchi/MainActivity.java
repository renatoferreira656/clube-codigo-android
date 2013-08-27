package br.com.dextra.tamagotchi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private int life;
	private int xp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize(savedInstanceState);
		bindButtons();

		ImageView imageDragon = (ImageView) findViewById(R.id_main.image_view);
		imageDragon.setBackgroundResource(R.drawable.dragon_normal);
	}

	private void bindButtons() {
		// TODO Auto-generated method stub

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
