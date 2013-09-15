package br.com.dextra.tamagotchi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

public class TamagotchiApplication extends Application {

	private static TamagotchiApplication application;
	private static Properties properties;

	@Override
	public void onCreate() {
		super.onCreate();

		application = this;
		loadProperties();
	}

	private static void loadProperties() {
		Resources resources = application.getResources();
		AssetManager assetManager = resources.getAssets();
		try {
			InputStream inputStream = assetManager.open("strings.properties");
			properties = new Properties();
			properties.load(inputStream);
			System.out.println("The properties are now loaded");
			System.out.println("properties: " + properties);
		} catch (IOException e) {
			System.err.println("Failed to open microlog property file");
			e.printStackTrace();
		}
	}

	public static Context getContext() {
		return application.getApplicationContext();
	}

	public static String property(String chave) {
		return properties.getProperty(chave);
	}
}
