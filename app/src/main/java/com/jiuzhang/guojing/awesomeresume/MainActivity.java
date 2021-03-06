package com.jiuzhang.guojing.awesomeresume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;
import com.jiuzhang.guojing.awesomeresume.util.ImageUtils;
import com.jiuzhang.guojing.awesomeresume.util.ModelUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jiuzhang.guojing.awesomeresume.EducationEditActivity.KEY_EDUCATION;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_BASIC_INFO_EDIT = 101;
    private static final String MODEL_EDUCATION = "educations";
    private static final String MODEL_BASIC_INFO = "basic_info";
    private BasicInfo basicInfo;
    private List<Education> educations = new ArrayList<Education>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setupUI();
    }

    private void loadData() {
        List<Education> saveEducation = ModelUtils.read(this, MODEL_EDUCATION, new TypeToken<List<Education>>(){});
        educations = saveEducation == null? new ArrayList<Education>():saveEducation;

        BasicInfo  saveBasicInfo = ModelUtils.read(this, MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        basicInfo = saveBasicInfo == null? new BasicInfo(): saveBasicInfo;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_EDUCATION_EDIT && resultCode == RESULT_OK){
            String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
            if( educationID != null){
                //delete education
                for(int i = 0; i < educations.size(); i++){
                    if(educations.get(i).id.equals(educationID)){
                        educations.remove(i);
                    }
                }
            }
            else
            {
                Education newEducation = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                boolean isUpdate = false;
                for(int i = 0; i < educations.size(); i++){
                    if(educations.get(i).id.equals(newEducation.id)){
                        educations.set(i, newEducation);
                        isUpdate = true;
                        break;
                    }
                }
                if(!isUpdate){
                    educations.add(newEducation);
                }
            }
            ModelUtils.save(this, MODEL_EDUCATION, educations);
            setupEducationsUI();
        }else if(requestCode == REQ_CODE_BASIC_INFO_EDIT && resultCode == RESULT_OK){
            BasicInfo newBasicinfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
            basicInfo = newBasicinfo;
            ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);
            setupBasicInfoUI();
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        (findViewById(R.id.add_education_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        setupBasicInfoUI();
        setupEducationsUI();
    }

    private void setupBasicInfoUI() {
        ((TextView) findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)? "Your name":basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)? "Your name":basicInfo.email);

        ImageView userPicture = (ImageView) findViewById(R.id.user_picture);
        if(basicInfo.imageUri != null){
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        }else{
            userPicture.setImageResource(R.drawable.ic_baseline_person_24px);
        }

        (findViewById(R.id.edit_basic_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASIC_INFO_EDIT);
            }
        });

        (findViewById(R.id.user_picture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserPictureActivity.class);
                intent.putExtra(UserPictureActivity.KEY_BASIC_PIC, basicInfo);
//                startActivityForResult(intent, REQ_CODE_BASIC_INFO_EDIT);
                startActivity(intent);
            }
        });
    }

    private void setupEducationsUI() {
        // Follow the example in setupBasicInfoUI
        // You will probably find formatItems method useful when displaying the courses
        LinearLayout educationsContainer = (LinearLayout) findViewById(R.id.education_container);
        educationsContainer.removeAllViews();
        for(Education education:educations){
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);
            setupEducation(educationView, education);
            educationsContainer.addView(educationView);
        }
    }

    private void setupEducation(View educationView, final Education education) {
        String dateString = DateUtils.dateToString(education.startDate)
                + " ~ " + DateUtils.dateToString(education.endDate);
        ((TextView) educationView.findViewById(R.id.education_school))
                .setText(education.school + " (" + dateString + ")");
        ((TextView) educationView.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        ImageButton editEducationBtn = (ImageButton) educationView.findViewById(R.id.edit_education_btn);
        editEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
