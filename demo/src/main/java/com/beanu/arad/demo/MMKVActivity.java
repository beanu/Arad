package com.beanu.arad.demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.bean.Student;

import java.util.List;

public class MMKVActivity extends ToolBarActivity implements OnClickListener {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv);

        mTextView = findViewById(R.id.textView);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);

        refreshUI();
    }


    private void refreshUI() {

        List<Student> list = AppHolder.getInstance().getStudentList();

        StringBuilder builder = new StringBuilder();

        for (Student student : list) {
            builder.append(student.getName() + "\n");
        }

        mTextView.setText(builder.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:

                Student student = new Student("张" + Math.random());
                AppHolder.getInstance().getStudentList().add(student);
                AppHolder.getInstance().flush();

                refreshUI();
                break;
            case R.id.btn_clear:

                AppHolder.getInstance().reset();
                refreshUI();
                break;
            default:
                break;
        }
    }
}
