package br.com.dextra.tamagotchi.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import br.com.dextra.tamagotchi.R;

public class ActionsFragment extends Fragment {

	private static final String TAG = ActionsFragment.class.getSimpleName();

	public static final int ACTION_NONE = 0;
	public static final int ACTION_FEED = 1;
	public static final int ACTION_PLAY = 2;
	public static final int ACTION_KILL = 3;

	private static final int DELAY_ACTION = 2000;

	public interface OnActionFired {
		public void changeLife(int delta);

		public void updateScreenLife();

		public void changeXp(int delta);

		public void updateScreenXp();

		public void updateScreenImage(int action);
	}

	private OnActionFired callback;
	private Button buttonFeed;
	private Button buttonPlay;
	private Button buttonKill;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_actions, null);

		LinearLayout linearLayout = (LinearLayout) view;
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			defineLayoutParamsButtons(linearLayout, LayoutParams.MATCH_PARENT, 0);
			Log.d(TAG, "Horizontal: " + view);
		} else {
			Log.d(TAG, "Vertical: " + view);
			linearLayout.setOrientation(LinearLayout.HORIZONTAL);
			defineLayoutParamsButtons(linearLayout, 0, LayoutParams.MATCH_PARENT);
		}

		linearLayout.findViewById(R.id.actions_feed);

		bindButtonFeed(view);
		bindButtonPlay(view);
		bindButtonKill(view);

		return view;
	}

	public void defineLayoutParamsButtons(View linearLayout, int width, int heigth) {
		linearLayout.findViewById(R.id.actions_feed).setLayoutParams(new LinearLayout.LayoutParams(width, heigth, 1.0f));

		linearLayout.findViewById(R.id.actions_kill).setLayoutParams(new LinearLayout.LayoutParams(width, heigth, 1.0f));

		linearLayout.findViewById(R.id.actions_play).setLayoutParams(new LinearLayout.LayoutParams(width, heigth, 1.0f));
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		callback = (OnActionFired) activity;
	}

	private void bindButtonFeed(View view) {
		buttonFeed = (Button) view.findViewById(R.id.actions_feed);
		buttonFeed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.changeLife(20);

				disableAllButtons();
				callback.updateScreenImage(ACTION_FEED);

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						callback.updateScreenImage(ACTION_NONE);
						callback.updateScreenLife();
					}
				});

			}
		});
	}

	private void bindButtonPlay(View view) {
		buttonPlay = (Button) view.findViewById(R.id.actions_play);
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.changeXp(10);

				disableAllButtons();
				callback.updateScreenImage(ACTION_PLAY);

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						callback.updateScreenImage(ACTION_NONE);
						callback.updateScreenXp();
					}
				});
			}
		});
	}

	private void bindButtonKill(View view) {
		buttonKill = (Button) view.findViewById(R.id.actions_kill);
		buttonKill.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.changeLife(-10);
				callback.changeXp(10);

				disableAllButtons();
				callback.updateScreenImage(ACTION_KILL);

				delay(DELAY_ACTION, new Runnable() {
					@Override
					public void run() {
						enableAllButtons();
						callback.updateScreenImage(ACTION_NONE);
						callback.updateScreenLife();
						callback.updateScreenXp();
					}
				});
			}
		});
	}

	private void enableAllButtons() {
		buttonFeed.setEnabled(true);
		buttonKill.setEnabled(true);
		buttonPlay.setEnabled(true);
	}

	private void disableAllButtons() {
		buttonFeed.setEnabled(false);
		buttonKill.setEnabled(false);
		buttonPlay.setEnabled(false);
	}

	private void delay(int delay, Runnable run) {
		new Handler().postDelayed(run, delay);
	}

}
