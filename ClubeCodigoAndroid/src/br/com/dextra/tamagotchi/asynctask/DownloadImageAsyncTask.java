package br.com.dextra.tamagotchi.asynctask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import br.com.dextra.tamagotchi.handler.FileHandler;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Void> {

	private static final String TAG = DownloadImageAsyncTask.class.getSimpleName();

	@Override
	protected Void doInBackground(String... params) {
		for (int i = 0; i < params.length; i += 2) {
			downloadImage(params[i], params[i + 1]);
		}
		return null;
	}

	private Bitmap downloadImage(String url, String filename) {
		InputStream stream = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			Log.d(TAG, "Init download: " + url);
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

					FileHandler.saveToFile(bStream.toByteArray(), filename);

					return image;
				}
			}else{
				Log.d(TAG, "Cannot download Image erro code:" + status);
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

}
