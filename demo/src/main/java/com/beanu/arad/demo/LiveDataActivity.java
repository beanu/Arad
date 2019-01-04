package com.beanu.arad.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.bean.Student;
import com.beanu.arad.demo.bean.StudentLiveData;

import androidx.lifecycle.Observer;

/**
 * @author Beanu
 */
public class LiveDataActivity extends ToolBarActivity implements View.OnClickListener {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        mTextView = findViewById(R.id.textView2);
        findViewById(R.id.button).setOnClickListener(this);


        StudentLiveData.getInstance().observe(this, new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                mTextView.setText(student.getName());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Student student = StudentLiveData.getInstance().getValue();
        student.setName("我是" + Math.random());
        StudentLiveData.getInstance().setValue(student);
    }
}
