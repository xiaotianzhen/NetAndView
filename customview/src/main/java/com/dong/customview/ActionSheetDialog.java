package com.dong.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 川东 on 2016/11/13.
 */

public class ActionSheetDialog {
    private Context context;
    private TextView tv_title, tv_cancle;
    private ScrollView sLayout;
    private LinearLayout lLayout;
    private boolean showtitle=false;
    private Display display;
    private Dialog dialog;
    private List<SheetItem> sheetItemList;

    public  ActionSheetDialog(Context context){

        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

    }


    public ActionSheetDialog Builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);
        view.setMinimumWidth(display.getWidth());

        tv_title = (TextView) view.findViewById(R.id.txt_title);
        tv_cancle = (TextView) view.findViewById(R.id.txt_cancel);
        sLayout = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout = (LinearLayout) view.findViewById(R.id.lLayout_content);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //定义 dialog的布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        //设置dialog在窗口的位置
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        if (!title.equals("")) {
            showtitle = true;
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public ActionSheetDialog addSheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(name, color, listener));

        return this;
    }

    public void show() {
        setSheetItem();
        dialog.show();
    }

    private void setSheetItem() {
        if (sheetItemList == null && sheetItemList.size() < 1) {
            return;
        }

        int size = sheetItemList.size();
        //控制高度
        if (size > 5) {
            ViewGroup.LayoutParams params = sLayout.getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout.setLayoutParams(params);
        }
        for (int i = 1; i <= size; i++) {
            final int index=i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String itemName = sheetItem.name;
            SheetItemColor sheetItemColor = sheetItem.color;
            final OnSheetItemClickListener listener = sheetItem.listener;

            TextView textView = new TextView(context);
            textView.setText(itemName);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            if (size == 1) {
                if (showtitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                }

            } else {
                if (showtitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            if(sheetItemColor!=null){
                textView.setTextColor(Color.parseColor(sheetItemColor.getName()));
            }else {
                textView.setTextColor(Color.parseColor(SheetItemColor.BULE.getName()));
            }
            //高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });
            lLayout.addView(textView);
        }


    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    private class SheetItem {
        String name;
        SheetItemColor color;
        OnSheetItemClickListener listener;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
            this.name = name;
            this.color = color;
            this.listener = listener;
        }
    }

    public enum SheetItemColor {
        BULE("#037BFF"), RED("#FD4A2E");

        String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
