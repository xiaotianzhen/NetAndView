package com.dong.getmessage;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by 川东 on 2016/12/21.
 */

public class MyAccessibility extends AccessibilityService{
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int type=event.getEventType();
        String eventStr="";
        Log.i("520it", "===============START================");
        switch (type){
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventStr="TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventStr="TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventStr="TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventStr="TYPE_WINDOW_STATE_CHANGED";
                break;
        }
        Log.i("520it", "***********************"+eventStr);
        Log.i("520it", "===============END================");
    }

    @Override
    public void onInterrupt() {

    }
}
