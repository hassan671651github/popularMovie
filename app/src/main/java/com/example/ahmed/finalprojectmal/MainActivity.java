package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements Connection {
    Boolean isTowPane;
    ArrayList<MovieDB> movieList;
    Async_Class async_class;
    CheckinternetConnection checkinternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkinternetConnection=new CheckinternetConnection(this);
        if(!checkinternetConnection.isConnectingToInternet()){
            Toast.makeText(this,"NO INTERNET CONNECTION !",Toast.LENGTH_LONG).show();
            finish();
        }
        if(checkinternetConnection.isConnectingToInternet()) {
            try {
                favoriteData = getSharedPreferences("favoriteData", Context.MODE_PRIVATE);
                String link = "http://api.themoviedb.org/3/movie/top_rated?api_key="+ApiKey.key;
                jsonparsing(link);
                setUI();
            }
            catch (Exception ex){Toast.makeText(this,"ERROR WHILE DOWNLOADING",Toast.LENGTH_LONG).show();}
        }
        else{
            Toast.makeText(this,"NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();
        }
    }

    public void setUI() {

        CustomAdapter myAdapter = new CustomAdapter(this, movieList);

        FrameLayout frame2 = (FrameLayout) findViewById(R.id.frame2);
        if (frame2 == null) {
            isTowPane = false;
        } else {
            isTowPane = true;

        }
        if (isTowPane) {
            mainFragment fragment = new mainFragment();
            fragment.setConnection(this);
            fragment.setAdapter(myAdapter);
            getFragmentManager().beginTransaction().replace(R.id.frame1, fragment).commit();

        } else {
            mainFragment fragment = new mainFragment();
            fragment.setConnection(this);
            fragment.setAdapter(myAdapter);
            getFragmentManager().beginTransaction().replace(R.id.layout_mainmlk, fragment).commit();
        }
    }

    public void jsonparsing(String Link) {

        async_class = new Async_Class();
        String h="";
        try {
            h=  async_class.execute(Link).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(h);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                MovieDB movieDB = new MovieDB();
                JSONObject object1 = jsonArray.getJSONObject(i);
                movieDB.setTitle(object1.getString("title"));
                movieDB.setPoster("http://image.tmdb.org/t/p/w185/" + object1.getString("poster_path"));
                movieDB.setDescription(object1.getString("overview"));
                movieDB.setDate(object1.getString("release_date"));
                movieDB.setRating(object1.getString("vote_average") + "/10");
                movieDB.setId(object1.getInt("id"));
                movieList.add(movieDB);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    SharedPreferences favoriteData;
    ArrayList<String>myfavoriteList;

    private ArrayList<String> getFavoriteList(){
        try {


            Set<String> set = new HashSet<String>();
            set = favoriteData.getStringSet("list", null);
            return new ArrayList<>(set);
        }

        catch (Exception ex){
            Toast.makeText(this,"retrive",Toast.LENGTH_LONG).show();
        }
        return null;
    }
    private void  saveFavoriteList(ArrayList<String> myfavoriteList){
        try {
            Set<String> favSet = new HashSet<String>();
            favSet.addAll(myfavoriteList);
            SharedPreferences.Editor myeditor = favoriteData.edit();
            myeditor.putStringSet("list", favSet);
            myeditor.commit();
        }
        catch (Exception ex){
            Toast.makeText(this,"save",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            try {
                String id = data.getIntExtra("id",1)+"";
                boolean set = data.getBooleanExtra("set", false);
                HandleFavoriteData(set,id);
            }
            catch (Exception ex){ }
        }


    }
    public void HandleFavoriteData(boolean set,String id){
        if(set) {
            ArrayList<String> mylist = getFavoriteList();
            if(mylist!=null){
                if (!mylist.contains(id)) {
                    mylist.add(id);
                    saveFavoriteList(mylist);
                }}
            else{
                mylist=new ArrayList<>();
                mylist.add(id);
                saveFavoriteList(mylist);
            }
        }
        else{
            ArrayList<String> mylist = getFavoriteList();
            if(mylist!=null) {
                if (mylist.contains(id)) {
                    mylist.remove(id);
                    saveFavoriteList(mylist);
                }
            }

        }

    }

    @Override
    public void connect(int posirion,String sender) {
        if(sender.equals("mainfragment")){
            if (isTowPane) {
                DetaieldFragment fragment = new DetaieldFragment();
                fragment.setConnection(this);
                Bundle bundle = new Bundle();
                bundle.putString("title",movieList.get(posirion).getTitle() );
                bundle.putString("poster", movieList.get(posirion).getPoster());
                bundle.putString("overview", movieList.get(posirion).getDescription());
                bundle.putString("rating", movieList.get(posirion).getRating());
                bundle.putString("date", movieList.get(posirion).getDate().split("-")[0]);
                bundle.putString("time", movieList.get(posirion).getTime());
                bundle.putInt("id", movieList.get(posirion).getId());
                ArrayList<String>arrayList=getFavoriteList();
                if(arrayList!=null&&arrayList.contains(movieList.get(posirion).getId()+""))
                    bundle.putBoolean("isFavorite",true);
                else
                    bundle.putBoolean("isFavorite", false);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame2, fragment).commit();
            } else {
                DetaieldActivity detaieldActivity=new DetaieldActivity();
                Intent i = new Intent(this, DetaieldActivity.class);
                i.putExtra("isFavorite",true);
                i.putExtra("title",movieList.get(posirion).getTitle() );
                i.putExtra("poster", movieList.get(posirion).getPoster());
                i.putExtra("overview", movieList.get(posirion).getDescription());
                i.putExtra("rating", movieList.get(posirion).getRating());
                i.putExtra("date", movieList.get(posirion).getDate().split("-")[0]);
                i.putExtra("time", movieList.get(posirion).getTime());
                i.putExtra("id", movieList.get(posirion).getId());
                ArrayList<String>arrayList=getFavoriteList();
                if(arrayList!=null&&arrayList.contains(movieList.get(posirion).getId()+""))
                    i.putExtra("isFavorite",true);
                else
                    i.putExtra("isFavorite",false);

                startActivityForResult(i,100);
            }}



    }

    @Override
    public void connect(int id, boolean state, String sender) {
        if(sender.equals("detailedFragment")){
            HandleFavoriteData(state,id+"");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.top_rated) {

            try {
                String link = "http://api.themoviedb.org/3/movie/top_rated?api_key="+ApiKey.key;
                jsonparsing(link);
                setUI();
            }catch (Exception ex){}

            return true;
        }
        if(id==R.id.popular){

            try {
                String link = "http://api.themoviedb.org/3/movie/popular?api_key="+ApiKey.key;
                jsonparsing(link);
                setUI();
            }catch (Exception ex){}
            return true;
        }
        if(id==R.id.favorite){
            ArrayList<MovieDB> arrayList=new ArrayList<>();
            ArrayList<String>IdList=getFavoriteList();
            if(IdList==null)
            {

                return  true;

            }


            for(int i=0;i<IdList.size();i++){

                arrayList.add(getMovie("http://api.themoviedb.org/3/movie/" +
                        IdList.get(i)+"?api_key="+ApiKey.key));
            }



            movieList=arrayList;
            setUI();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public MovieDB getMovie(String Link) {

        async_class = new Async_Class();
        String h = "";
        try {
            h = async_class.execute(Link).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        MovieDB movieDB = new MovieDB();
        try {
            JSONObject object1 = new JSONObject(h);
            movieDB.setTitle(object1.getString("title"));
            movieDB.setPoster("http://image.tmdb.org/t/p/w185/" + object1.getString("poster_path"));
            movieDB.setDescription(object1.getString("overview"));
            movieDB.setDate(object1.getString("release_date"));
            movieDB.setTime("120min");
            movieDB.setRating(object1.getString("vote_average") + "/10");
            movieDB.setId(object1.getInt("id"));
            movieList.add(movieDB);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return movieDB;
    }

}

