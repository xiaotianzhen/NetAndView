package com.dong.work.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 川东 on 2016/11/5.
 */

public class StreamTool {


    public static String streamToString(InputStream is) {
        String result = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            if ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            byte[] by = bos.toByteArray();
            bos.close();
            is.close();
            result = new String(by);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
