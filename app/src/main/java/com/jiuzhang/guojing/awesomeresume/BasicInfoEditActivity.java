package com.jiuzhang.guojing.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;

import java.util.Arrays;

public class BasicInfoEditActivity extends EditBaseActivity<BasicInfo> {
    public static final String KEY_BASIC_INFO = "basic_info";
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
    }

    @Override
    protected void saveAndExit(@Nullable BasicInfo data) {
        if(data == null){
            data = new BasicInfo();
        }
        data.name = ((EditText)findViewById(R.id.basic_info_edit_name)).getText().toString();
        data.email = ((EditText)findViewById(R.id.basic_info_edit_email)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_BASIC_INFO,  data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected BasicInfo initializeData() {
        return getIntent().getParcelableExtra(KEY_BASIC_INFO);
    }
}
