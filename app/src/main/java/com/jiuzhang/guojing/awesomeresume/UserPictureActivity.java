package com.jiuzhang.guojing.awesomeresume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.util.ImageUtils;

public class UserPictureActivity extends AppCompatActivity {
    public static final String KEY_BASIC_PIC = "basic_pic";
    BasicInfo data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = getIntent().getParcelableExtra(KEY_BASIC_PIC);
        setup_picture_UI();
    }

    private void setup_picture_UI() {
        ImageView imageView = (ImageView) findViewById(R.id.full_user_picture);
        ImageUtils.loadImage(this, data.imageUri, imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
