package pachong;

import java.io.*;
import java.net.*;

public class SocketPC_1 {
    public static void doTest(URL url) {
        String host = url.getHost();
        try {
            Socket socket = new Socket(host, 80);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //注意GET后面和HTTP之前都要空格
            /**
             * HTTP1.0和HTTP1.1的区别
             * HTTP 1.0规定浏览器与服务器只保持短暂的连接，浏览器的每次请求都需要与服务器建立一个TCP连接，服务器完成请求处理后立即断开TCP连接，服务器不跟踪每个客户也不记录过去的请求。
             * HTTP 1.1的持续连接，也需要增加新的请求头来帮助实现。
             * 例如，Connection请求头的值为Keep-Alive时，客户端通知服务器返回本次请求结果后保持连接
             * Connection请求头的值为close时，客户端通知服务器返回本次请求结果后关闭连接。
             * HTTP 1.1还提供了与身份认证、状态管理和Cache缓存等机制相关的请求头和响应头。
             */
            socket.setKeepAlive(false);
            bufferedWriter.write("GET " + url + " HTTP/1.0\r\n");
            bufferedWriter.write("HOST:" + host + "\r\n");
            //如果行结束符号"\r\n"前面任何没有内容，则表示http的head输出给服务器端完成
            bufferedWriter.write("\r\n");
            //清空缓存
            bufferedWriter.flush();
            //设置编码
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            String readline = "";
            while ((readline = bufferedReader.readLine()) != null) {
                System.out.println(readline);
            }
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
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
