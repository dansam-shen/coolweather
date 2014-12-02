package net.itaem.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 * ���������Ľӿڵ���
 */
public class HttpUtil {


	/*
	 * ������������ⲿʹ�õķ�ʽ֮һ��ʹ�ýӿڻص�<>
	 */
	public static void sendRequest(final String address,
			final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				URL url = null;
				HttpURLConnection conn = null;
				try {
					url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(8000);
					conn.setRequestMethod("GET");
					conn.setReadTimeout(8000);
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					String line = null;
					StringBuilder response = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError();
					}
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}

			}
		}).start();
	}
}
