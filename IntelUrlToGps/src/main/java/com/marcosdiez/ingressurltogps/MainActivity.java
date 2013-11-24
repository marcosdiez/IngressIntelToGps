package com.marcosdiez.ingressurltogps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
    final static String TAG = "Intel2GPS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -23.548943,-46.638818
        // -23.545751,-46.66941
        //openGpsUrl("-23.545751" , "-46.66941");
        // ParsedIntelUrlTest.doTests();
        processIntent();
        finish();
    }

    private void processIntent() {
        Intent intent = getIntent();
        if(intent == null){
            Toast.makeText(this,R.string.intent_not_received, Toast.LENGTH_SHORT).show();
            return;
        }
        String data = intent.getDataString();
        if( data == null ){
            Toast.makeText(this,R.string.intent_contains_not_data, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "Received URL:" + data);
        ParsedIntelUrl parsedData = new ParsedIntelUrl(data);
        openGpsUrl(parsedData);
    }

    void openGpsUrl(ParsedIntelUrl theData){
        openGpsUrl(theData.getLongitude(), theData.getLatitude());
    }

    void openGpsUrl(String longitude, String latitude){
        String theURL = "http://maps.google.com/maps?daddr=" + longitude + "," + latitude;
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
        startActivity(intent);
    }
}
