package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetaieldFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetaieldFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetaieldFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetaieldFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetaieldFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetaieldFragment newInstance(String param1, String param2) {
        DetaieldFragment fragment = new DetaieldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
public void favoriteAnimation(boolean isFavorite ){
    if(isFavorite) {
        favorite.setBackgroundColor(Color.RED);
        favorite.setText("MARK AS\nUNFAVORITE");
    }
    else{
        favorite.setBackgroundColor(Color.GREEN);
        favorite.setText("MARK AS\nFAVORITE");
    }
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Connection connection;
    public void setConnection(Connection connection){
        this.connection=connection;
    }
    Bundle b;
    TextView title;
    ImageView poster;
    TextView overview;
    TextView date;
    TextView time;
    TextView rate;
    ListView listView;
    ListView ListView3;
    Button favorite;
     int id;
    Boolean isFavorite;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View fragment=inflater.inflate(R.layout.detailed_fragment, container, false);
        b=getArguments();
        title=(TextView)fragment.findViewById(R.id.movie_title);
        poster=(ImageView)fragment.findViewById(R.id.movie_poster);
         overview=(TextView)fragment.findViewById(R.id.movie_overview);
         date=(TextView)fragment.findViewById(R.id.movie_date);
        rate=(TextView)fragment.findViewById(R.id.movie_rating);
        listView=(ListView)fragment.findViewById(R.id.listView);
         ListView3=(ListView)fragment.findViewById(R.id.listView3);
         favorite=(Button)fragment.findViewById(R.id.favoriteButton);
        try {


             id=b.getInt("id");
            isFavorite=b.getBoolean("isFavorite");
            title.setText(b.getString("title"));
            overview.setText(b.getString("overview"));
            date.setText(b.getString("date"));
            rate.setText(b.getString("rating"));
            favoriteAnimation(isFavorite);
            Picasso.with(getActivity()).load(b.getString("poster")).into(poster);

           String result=new Async_Class().execute("http://api.themoviedb.org/3/movie/"+id+"/" +
                   "videos?api_key=").get();
            JSONObject myjson=new JSONObject(result);
            final JSONArray jsonArray=myjson.getJSONArray("results");
         final    ArrayList<Trailer>trailerArrayList=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Trailer trailer=new Trailer();
                trailer.setName(jsonObject.getString("name"));
                trailer.setKey(jsonObject.getString("key"));
                trailerArrayList.add(trailer);
            }
            CustomAdapterTrailer customAdapterTrailer=new CustomAdapterTrailer(getActivity(),trailerArrayList);

            listView.setAdapter(customAdapterTrailer);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String key = trailerArrayList.get(position).getKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + key));
                        getActivity().startActivity(Intent.createChooser( intent, "Chosee Browser" ));
                    } catch (Exception ex) {

                    }

                }
            });

                String result2 = new Async_Class().execute("http://api.themoviedb.org/3/movie/" + id +
                        "/reviews?api_key=").get();
                JSONObject reviewsJson = new JSONObject(result2);
                JSONArray reviewJsonArray = reviewsJson.getJSONArray("results");
                ArrayList<reviews> reviewsesList = new ArrayList<>();
                for (int i = 0; i < reviewJsonArray.length(); i++) {
                    reviews myreviews = new reviews();
                    myreviews.setAuther(reviewJsonArray.getJSONObject(i).getString("author"));
                    myreviews.setContent(reviewJsonArray.getJSONObject(i).getString("content"));
                    reviewsesList.add(myreviews);
                }
                CustomAdapterReviews customAdapterReviews = new CustomAdapterReviews(reviewsesList, getActivity());
                ListView3.setAdapter(customAdapterReviews);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavorite)
                    {
                    isFavorite=false;
                        connection.connect(id, isFavorite, "detailedFragment");
                     favorite.setBackgroundColor(Color.GREEN);
                    }
                  else {
                             isFavorite=true;
                              connection.connect(id, isFavorite, "detailedFragment");
                               favorite.setBackgroundColor(Color.RED);
                           }
                    favoriteAnimation(isFavorite);
                }
            });



            listView.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
ListView3.setOnTouchListener(new ListView.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                // Allow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        // Handle ListView touch events.
        v.onTouchEvent(event);
        return true;
    }
});

        }
        catch(Exception ex){
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG);
        }


        return  fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
