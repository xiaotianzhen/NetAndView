package com.dong.customview;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private CircleImageView circleImage;
    /**
     * 指定拍摄图片文件位置避免获取到缩略图
     */
    private File outFile;

    /**
     * 选择头像相册选取
     */
    private static final int REQUESTCODE_PICK = 1;
    /**
     * 裁剪好头像-设置头像
     */
    private static final int REQUESTCODE_CUTTING = 2;
    /**
     * 选择头像拍照选取
     */
    private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
    /**
     * 裁剪好的头像的Bitmap
     */
    private Bitmap currentBitmap;

    /**
     * 圆形图片的Imageview
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleImage = (CircleImageView) findViewById(R.id.circleImage);
        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(MainActivity.this).Builder()
                        .addSheetItem("相册", ActionSheetDialog.SheetItemColor.BULE, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openPic();
                            }
                        }).addSheetItem("照相", ActionSheetDialog.SheetItemColor.RED, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        openCamera();
                    }
                }).show();
            }
        });
    }

    private void openPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdir();
            }
            outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Toast.makeText(MainActivity.this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(outFile));

                break;
            case REQUESTCODE_CUTTING:

                if (data != null) {
                    setPicToView(data);
                }
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri data) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);

    }


    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            /** 可用于图像上传 */
            currentBitmap = bundle.getParcelable("data");
            circleImage.setImageBitmap(currentBitmap);
        }
    }
}
