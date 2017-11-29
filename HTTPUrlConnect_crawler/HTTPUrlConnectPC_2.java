package pachong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPUrlConnectPC_2 {
    public static void doTest(URL url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置编码
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String readlin = "";
            while ((readlin = bufferedReader.readLine()) != null) {
                System.out.println(readlin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("https://www.hao123.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        doTest(url);
    }
}

