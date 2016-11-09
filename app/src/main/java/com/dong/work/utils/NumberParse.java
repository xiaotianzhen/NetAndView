package com.dong.work.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by 川东 on 2016/11/6.
 */

public class NumberParse {


    public static String parseNumber(InputStream is) throws Exception {
        Log.i("520it","*************+你好");
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int type = parser.getEventType();
        Log.i("520it","*************"+type);
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                   String name= parser.getName();
                    Log.i("520it","*************"+name);
                    if ("getMobileCodeInfoResult".equals(parser.getName())) {
                        return parser.nextText();
                    }
                    break;
            }

           type= parser.next();
        }
        is.close();

        return "用户不存在";
    }


}
