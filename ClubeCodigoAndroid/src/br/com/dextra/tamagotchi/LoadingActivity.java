package br.com.dextra.tamagotchi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import br.com.dextra.tamagotchi.asynctask.AsyncOnCompleteTask;
import br.com.dextra.tamagotchi.asynctask.DownloadImageAsyncTask;

public class LoadingActivity extends Activity implements AsyncOnCompleteTask {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		new DownloadImageAsyncTask(this).execute(TamagotchiApplication.property(R.string.property_url_dragon_kill), "dragon_kill.png",
				TamagotchiApplication.property(R.string.property_url_dragon_normal), "dragon_normal.png", TamagotchiApplication.property(R.string.property_url_dragon_feed),
				"dragon_feed.png", TamagotchiApplication.property(R.string.property_url_dragon_play), "dragon_play.png");

	}

	@Override
	public void onFinished() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
