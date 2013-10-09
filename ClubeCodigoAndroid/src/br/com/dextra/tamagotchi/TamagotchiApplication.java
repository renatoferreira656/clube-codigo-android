package br.com.dextra.tamagotchi;

import android.app.Application;
import android.content.Context;

public class TamagotchiApplication extends Application  {

	private static TamagotchiApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}

	public static Context getContext() {
		return application.getApplicationContext();
	}

	public static String property(int chave) {
		return application.getString(chave);
	}

}
