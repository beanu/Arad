package com.beanu.arad.demo;

import android.os.Parcel;
import android.os.Parcelable;

import com.beanu.arad.Arad;
import com.beanu.arad.demo.bean.Student;

import java.util.List;

public class AppHolder implements Parcelable {

    /**
     * 所有的钱包列表
     */
    private List<Student> mStudentList;
    private Student mStudent;


    private static AppHolder instance = null;

    private AppHolder() {
    }

    public synchronized static AppHolder getInstance() {
        if (instance == null) {
            instance = new AppHolder();
        }
        return instance;
    }

    public void reset() {
        this.mStudentList.clear();
        this.mStudentList = null;
        this.mStudent = null;
        flush();
    }

    public void flush() {
        Arad.kv.put("app", this);
    }

    public Student getStudent() {
        if (mStudent == null) {
            mStudent = Arad.kv.getParcelable("app", AppHolder.class).mStudent;
        }
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
        flush();
    }

    public List<Student> getStudentList() {
        if (mStudentList == null) {
            mStudentList = Arad.kv.getParcelable("app", AppHolder.class).mStudentList;
        }
        return mStudentList;
    }

    public void setStudentList(List<Student> studentList) {
        mStudentList = studentList;
        flush();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mStudentList);
        dest.writeParcelable(this.mStudent, flags);
    }

    protected AppHolder(Parcel in) {
        this.mStudentList = in.createTypedArrayList(Student.CREATOR);
        this.mStudent = in.readParcelable(Student.class.getClassLoader());
    }

    public static final Parcelable.Creator<AppHolder> CREATOR = new Parcelable.Creator<AppHolder>() {
        @Override
        public AppHolder createFromParcel(Parcel source) {
            return new AppHolder(source);
        }

        @Override
        public AppHolder[] newArray(int size) {
            return new AppHolder[size];
        }
    };
}
