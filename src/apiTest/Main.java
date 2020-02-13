package apiTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

	private static HttpURLConnection connection;

	public static void main(String[] args) {

		// Method 1: java.net.HttpURLConnection
		// Allows us to make a connection with the API end point

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://api.twitch.tv/helix/streams?");

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				connection.setRequestProperty("Client-ID", "xc06qty6ku3s0jwewxqklma6zaby1e");
				int status = connection.getResponseCode();
				// System.out.println(status);

				if (status > 299) {
					reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
					while ((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				} else {
					reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while ((line = reader.readLine()) != null) {
						responseContent.append(line);
					}
					reader.close();
				}
				//System.out.println(responseContent.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		parse(responseContent.toString());
	}
	
	public static String parse (String responseBody) {
		
		JSONObject object = new JSONObject(responseBody);
		JSONArray arr = object.getJSONArray("data");
		
		for (int i=0; i<10;i++) {
			String userName = arr.getJSONObject(i).getString("user_name");
			String title = arr.getJSONObject(i).getString("title");
			int viewers = arr.getJSONObject(i).getInt("viewer_count");
			
			System.out.println("Username: " + userName + "\n" + "Title: " + title + "\n"+ "Viewers: " + viewers +"\n");
		}
		return null;
	}
}
