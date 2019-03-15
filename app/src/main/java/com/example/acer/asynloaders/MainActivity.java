package com.example.acer.asynloaders;

import android.app.LoaderManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<String> {

    String newsurl="https://newsapi.org/v2/everything?q=bitcoin&apiKey=d55bbd570ea3462b81f4e926b4fdd3ff";
    ArrayList<NewsModel> newsModels;
    RecyclerView recyclerView;
    ProgressBar p;
    public static final int loader_id=789;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        p=findViewById(R.id.progress);
        newsModels=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new NewsAdapter(this,newsModels));
        //new MyAsynTask().execute();
        getSupportLoaderManager().initLoader(789,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(newsurl);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    InputStream inputStream=urlConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                p.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        p.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("articles");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                //JSONObject jsonObject2=jsonObject1.getJSONObject("source");
                String title=jsonObject1.getString("title");
                String imgurl=jsonObject1.getString("urlToImage");
                Log.i("img",imgurl);
                newsModels.add(new NewsModel(title,imgurl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    /*class MyAsynTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(newsurl);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return scanner.next();
                }else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.setVisibility(View.GONE);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("articles");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    //JSONObject jsonObject2=jsonObject1.getJSONObject("source");
                    String title=jsonObject1.getString("title");
                    String imgurl=jsonObject1.getString("urlToImage");
                    Log.i("img",imgurl);
                    newsModels.add(new NewsModel(title,imgurl));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}
