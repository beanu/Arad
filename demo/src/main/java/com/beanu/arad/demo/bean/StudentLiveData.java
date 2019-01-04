package com.beanu.arad.demo.bean;

import com.beanu.arad.Arad;

import androidx.lifecycle.LiveData;

/**
 * 封装student成一个livedata的单例，只要student变化了 就能通知接收者
 *
 * @author Beanu
 */
public class StudentLiveData extends LiveData<Student> {

    private static StudentLiveData sIns;

    private StudentLiveData() {
    }

    public static StudentLiveData getInstance() {
        if (sIns == null) {
            sIns = new StudentLiveData();
        }
        return sIns;
    }

    @Override
    protected void onActive() {
        if (getValue() == null) {
            Student student = Arad.kv.getParcelable("student", Student.class);
            if (student != null) {
                setValue(student);
            }
        }

    }

    @Override
    protected void onInactive() {

    }

    @Override
    public void setValue(Student value) {
        super.setValue(value);
        //实例化到本地
        Arad.kv.put("student", value);
    }

    @Override
    public void postValue(Student value) {
        super.postValue(value);
        //最终调用setValue
    }

}
