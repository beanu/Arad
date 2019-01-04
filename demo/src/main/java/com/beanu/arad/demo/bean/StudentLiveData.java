package com.beanu.arad.demo.bean;

import androidx.lifecycle.LiveData;

/**
 * 封装student成一个livedata的单例，只要student变化了 就能通知接收者
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
    public void setValue(Student value) {
        super.setValue(value);
    }

    @Override
    public void postValue(Student value) {
        super.postValue(value);
    }
}
