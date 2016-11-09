package com.dong.work.server;

import android.util.Log;
import android.widget.Toast;

import com.dong.work.utils.StreamTool;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 川东 on 2016/11/5.
 */

public class LoginServer {

    public static String getSendData(String path, String name, String password) throws Exception {
        String param1 = URLEncoder.encode(name);
        String param2 = URLEncoder.encode(password);
        String urlPath = path + "?name=" + param1 + "&password=" + param2;
        String result = null;
        try {

            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            //只有当客户端向服务端发送相应的时候，客户端才开始传输数据给服务端，
            int code = conn.getResponseCode();
            Log.i("520it", "**************" + code);
            if (code == 200) {
                result = StreamTool.streamToString(conn.getInputStream());
            } else {
                Log.i("520it", "网络连接失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String postSendData(String path, String name, String password) throws Exception {
        String result = "";
        String param1 = URLEncoder.encode(name);
        String param2 = URLEncoder.encode(password);
        String data = "name=" + param1 + "&password=" + param2;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        int code = conn.getResponseCode();
        InputStream is = conn.getInputStream();
        if (code == 200) {
            result = StreamTool.streamToString(is);
        }

        return result;
    }

    public static String postSendDataAddFile(String path, String name, String password, String filepath) throws Exception {

        Part[] parts = new Part[]{new StringPart("name", name),
                new StringPart("password", password),
                new FilePart("file", new File(filepath))
        };
        PostMethod filePost = new PostMethod(path);
        RequestEntity entity = new MultipartRequestEntity(parts, filePost.getParams());
        filePost.setRequestEntity(entity);
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        int code = client.executeMethod(filePost);
        if (code == 200) {
            String body=new String(filePost.getResponseBody());
           String end= new String(body.getBytes("ISO-8859-1"),"UTF-8");
            Log.i("520it","**********"+end);
            return end;
        }else {
         Log.i("520it","上传文件失败");
        }

        return null;
    }

}
