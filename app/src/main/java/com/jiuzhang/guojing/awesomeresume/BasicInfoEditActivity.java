package com.jiuzhang.guojing.awesomeresume;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;
import com.jiuzhang.guojing.awesomeresume.util.ImageUtils;
import com.jiuzhang.guojing.awesomeresume.util.PermissionUtils;

import java.util.Arrays;

public class BasicInfoEditActivity extends EditBaseActivity<BasicInfo> {
    public static final String KEY_BASIC_INFO = "basic_info";
    private static final int REQ_CODE_PICK_IMAGE = 100;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info_edit;
    }

    @Override
    protected void setupUIForCreate() {

    }

    @Override
    protected void setupUIForEdit(@NonNull BasicInfo data) {
        ( (EditText)findViewById(R.id.basic_info_edit_name)).setText(data.name);
        ( (EditText)findViewById(R.id.basic_info_edit_email)).setText(data.email);

        if (data.imageUri != null) {
            showImage(data.imageUri);
        }
        findViewById(R.id.basic_info_edit_image_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionUtils.checkPermission(BasicInfoEditActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtils.requestReadExternalStoragePermission(BasicInfoEditActivity.this);
                } else {
                    pickImage();
                }
            }
        });
    }

    @Override
    protected void saveAndExit(@Nullable BasicInfo data) {
        if(data == null){
            data = new BasicInfo();
        }
        data.name = ((EditText)findViewById(R.id.basic_info_edit_name)).getText().toString();
        data.email = ((EditText)findViewById(R.id.basic_info_edit_email)).getText().toString();
        data.imageUri = (Uri) findViewById(R.id.basic_info_edit_image).getTag();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_BASIC_INFO,  data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected BasicInfo initializeData() {
        return getIntent().getParcelableExtra(KEY_BASIC_INFO);

    }

    private void showImage(Uri imageUri){
        ImageView imageView = (ImageView) findViewById(R.id.basic_info_edit_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setTag(imageUri);
        ImageUtils.loadImage(this, imageUri, imageView);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), REQ_CODE_PICK_IMAGE);
    }
}
