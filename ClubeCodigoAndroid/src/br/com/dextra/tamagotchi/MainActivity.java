package br.com.dextra.tamagotchi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.dextra.tamagotchi.fragment.ActionsFragment;
import br.com.dextra.tamagotchi.handler.FileHandler;

public class MainActivity extends FragmentActivity implements
		ActionsFragment.OnActionFired {

	private static final String TAG = MainActivity.class.getSimpleName();

	private int life;
	private int maxLife;
	private int minLife;
	private int xp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize(savedInstanceState);

		updateScreenXp();
		updateScreenLife();

		updateScreenImage(ActionsFragment.ACTION_NONE);

		ActionsFragment fragment = new ActionsFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_actions_container, fragment).commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		outState.putInt("xp", xp);
		outState.putInt("life", life);
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

		maxLife = getResources().getInteger(
				R.integer.tamagotchi_main_information_life_max);
		minLife = getResources().getInteger(
				R.integer.tamagotchi_main_information_life_min);
	}

	@Override
	public void changeLife(int delta) {
		int updated = life + delta;

		if (updated > maxLife) {
			updated = maxLife;
		}
		if (updated < minLife) {
			updated = minLife;
		}

		life = updated;
	}

	@Override
	public void changeXp(int delta) {
		xp += delta;
	}

	@Override
	public void updateScreenLife() {
		((ProgressBar) findViewById(R.id.main_progress_life)).setProgress(life);
	}

	@Override
	public void updateScreenXp() {
		((TextView) findViewById(R.id.main_info_xp)).setText("XP: " + xp);
	}

	private void updateScreenNormalImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id.main_image_view);
		imageDragon.setImageBitmap(FileHandler
				.readImageFromFile("dragon_normal.png"));
	}

	private void updateScreenKillImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id.main_image_view);
		imageDragon.setImageBitmap(FileHandler
				.readImageFromFile("dragon_kill.png"));
	}

	private void updateScreenPlayImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id.main_image_view);
		imageDragon.setImageBitmap(FileHandler
				.readImageFromFile("dragon_play.png"));
	}

	private void updateScreenFeedImage() {
		ImageView imageDragon = (ImageView) findViewById(R.id.main_image_view);
		imageDragon.setImageBitmap(FileHandler
				.readImageFromFile("dragon_feed.png"));
	}

	@Override
	public void updateScreenImage(int action) {
		if (action == ActionsFragment.ACTION_NONE) {
			updateScreenNormalImage();
			return;
		}
		if (action == ActionsFragment.ACTION_FEED) {
			updateScreenFeedImage();
			return;
		}
		if (action == ActionsFragment.ACTION_PLAY) {
			updateScreenPlayImage();
			return;
		}
		if (action == ActionsFragment.ACTION_KILL) {
			updateScreenKillImage();
			return;
		}
	}
}
