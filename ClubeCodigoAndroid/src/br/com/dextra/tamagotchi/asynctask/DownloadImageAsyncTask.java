package br.com.dextra.tamagotchi.asynctask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import br.com.dextra.tamagotchi.TamagotchiApplication;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Void> {

	private static final String TAG = DownloadImageAsyncTask.class
			.getSimpleName();

	@Override
	protected Void doInBackground(String... params) {
		for (String param : params) {
			downloadImage(param);
		}
		return null;
	}

	private Bitmap downloadImage(String url) {
		InputStream stream = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet metodo = new HttpGet(url);
			HttpResponse response = client.execute(metodo);

			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				stream = response.getEntity().getContent();
				Bitmap image = BitmapFactory.decodeStream(stream);

				if (image != null) {
					Log.d(TAG, "Image downloaded " + url);

					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.PNG, 100, bStream);
					saveToFile(bStream.toByteArray(), url);

					return image;
				}
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Error downloading image", e);
		} catch (IOException e) {
			Log.e(TAG, "Error downloading image", e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					Log.e(TAG, "Error closing stream", e);
				}
			}
		}

		return null;
	}

	public void saveToFile(byte[] content, String url) {
		FileOutputStream output = null;

		String[] split = url.split("/");
		String filename = split[split.length - 1];

		try {
			output = TamagotchiApplication.getContext().openFileOutput(
					filename, Context.MODE_PRIVATE);
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

}
