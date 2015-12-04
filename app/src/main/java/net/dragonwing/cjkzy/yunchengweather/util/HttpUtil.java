package net.dragonwing.cjkzy.yunchengweather.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xuncl on 2015/12/4.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    if (null!=listener){
                        listener.onFinish(response.toString());
                    }
                }catch (MalformedURLException e){
                    if (null!=listener){
                        listener.onError(e);
                    }
                }catch (IOException e2){
                    if (null!=listener){
                        listener.onError(e2);
                    }
                }finally {
                    if (null!=connection){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
