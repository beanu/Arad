package com.beanu.arad.demo.ui.module;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.R;
import com.beanu.arad.demo.models.Gitapi;
import com.beanu.arad.demo.models.data.User;
import com.beanu.arad.utils.Log;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import com.bumptech.glide.Glide;

public class NetWorkActivity extends ToolBarActivity {
    //
//    Dao dao = new Dao(this);
    @Bind(R.id.result_textView)
    TextView mResultTextView;
    @Bind(R.id.github_avatar)
    ImageView mGithubAcatar;
    @Bind(R.id.github_avatar2)
    ImageView mGithubAcatar2;


    String API = "https://api.github.com";  // BASE URL
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        ButterKnife.bind(this);


        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        // Retrofit section start from here...
        // create an adapter for retrofit with base url
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }


    @OnClick(R.id.network_btn)
    public void onClick() {

        Gitapi gitapi = retrofit.create(Gitapi.class);
        gitapi.getFeed("beanu")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(user.getName());
                    }
                });
//        Call<User> callback = gitapi.getFeed("beanu");
//        callback.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
//                mResultTextView.setText(response.body().getName());
//                Arad.imageLoader.load(response.body().getAvatar_url()).into(mGithubAcatar);
//
////                Glide.with(NetWorkActivity.this).load(response.body().getAvatar_url()).into(mGithubAcatar2);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });

    }
//
//    @Override
//    public void onRequestSuccess(int requestCode) {
//        if (requestCode == 0) {
//            mResultTextView.setText(dao.getStrResult());
//            showProgress(false);
//        }
//    }
//
//    private static class Dao extends IDao {
//
//        private String strResult;
//
//        public Dao(INetResult iNetResult) {
//            super(iNetResult);
//        }
//
//        public void newsList() {
//            getRequest("http://api.yi18.net/news/list", null);
//        }
//
//        @Override
//        public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
//            strResult = result.toString();
//        }
//
//        public String getStrResult() {
//            return strResult;
//        }
//    }


    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }


}
