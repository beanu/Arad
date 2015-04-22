package com.beanu.arad.demo.module;

import android.os.Bundle;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.R;
import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NetWorkActivity extends ToolBarActivity {

    Dao dao = new Dao(this);
    @InjectView(R.id.result_textView) TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        ButterKnife.inject(this);
        dao.newsList();
        showProgress(true);
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        if (requestCode == 0) {
            mResultTextView.setText(dao.getStrResult());
            showProgress(false);
        }
    }

    private static class Dao extends IDao {

        private String strResult;

        public Dao(INetResult iNetResult) {
            super(iNetResult);
        }

        public void newsList() {
            getRequest("http://api.yi18.net/news/list", null);
        }

        @Override
        public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
            strResult = result.toString();
        }

        public String getStrResult() {
            return strResult;
        }
    }
}
