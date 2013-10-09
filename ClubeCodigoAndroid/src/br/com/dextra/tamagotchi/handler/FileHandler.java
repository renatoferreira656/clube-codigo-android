package br.com.dextra.tamagotchi.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import br.com.dextra.tamagotchi.TamagotchiApplication;

public class FileHandler {

	private static final String TAG = FileHandler.class.getSimpleName();

	public static void saveToFile(byte[] content, String filename) {
		FileOutputStream output = null;

		try {
			output = TamagotchiApplication.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
			output.write(content);
			Log.d(TAG, "Image saved to file");
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File not found - " + filename, e);
		} catch (IOException e) {
			Log.e(TAG, "Error writing file - " + filename, e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					Log.e(TAG, "Error closing stream", e);
				}
			}
		}
	}

	public static Bitmap readImageFromFile(String filename) {
		FileInputStream input = null;
		try {
			input = TamagotchiApplication.getContext().openFileInput(filename);
			if (input != null) {
				return BitmapFactory.decodeStream(input);
			}
			return null;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File not found - " + filename, e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Log.e(TAG, "Error closing stream", e);
				}
			}
		}
		return null;
	}

	public static boolean fileAlreadyStorage(String filename) {
		File input = TamagotchiApplication.getContext().getFileStreamPath(filename);
		if (input != null) {
			return input.exists();
		}
		return false;
	}
}
