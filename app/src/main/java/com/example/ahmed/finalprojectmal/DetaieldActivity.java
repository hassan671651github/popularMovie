package com.example.ahmed.finalprojectmal;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;

public class DetaieldActivity extends Activity  implements Connection{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        DetaieldFragment fragment = new DetaieldFragment();
        Intent i=getIntent();
        Bundle bundle=getIntent().getExtras();
        fragment.setConnection(this);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.activityDetaildId, fragment).commit();
    }
    @Override
    public void connect(int posirion, String sender) {}
    Intent result;
    @Override
    public void connect(int posirion, boolean state,String sender) {
        if(sender.equals("detailedFragment")){
    result=new Intent();
   if(state==true) {
    result.putExtra("set", true);
    result.putExtra("id", posirion);
       setResult(100, result);

}
            else {
        result.putExtra("set", false);
        result.putExtra("id", posirion);
       setResult(100, result);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
