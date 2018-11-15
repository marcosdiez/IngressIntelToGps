package com.marcosdiez.ingressurltogps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {                    theActivity
    final static String TAG = "Intel2GPS";
    public static ParsedIntelUrl theData=null;
    public static Activity theActivity=null;

    public static void showExportDialog(final Activity theActivity) {
        // final Activity activityCopy = theActivity;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(theActivity);
        alertDialog.setMessage(R.string.choose_your_maps_app)
                .setPositiveButton(R.string.google_maps, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url = buildGoogleMapsUrl(theData.getLongitude(), theData.getLatitude());
                        Log.d(TAG,url);
                        openUrl(url, theActivity);
                        theActivity.finish();
                    }
                })
                .setNegativeButton(R.string.yandex_maps, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url = buildYandexMapsUrl(theData.getLongitude(), theData.getLatitude());
                        Log.d(TAG,url);
                        openUrl(url, theActivity);
                        theActivity.finish();

                    }
                });

        alertDialog.create().show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // -23.548943,-46.638818
        // -23.545751,-46.66941
        //openGpsUrl("-23.545751" , "-46.66941");
        // ParsedIntelUrlTest.doTests();

        processIntent();
        //


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

//

    void openGpsUrl(ParsedIntelUrl theDataX){

    Log.d(TAG , "ru.yandex.yandexmaps: " + (appInstalledOrNot("ru.yandex.yandexmaps") ? "true" : "false") );

        if(appInstalledOrNot("ru.yandex.yandexmaps")) {
            MainActivity.theData=theDataX;
            MainActivity.theActivity=this;
            showExportDialog(this); // Yandex requires a different intent
        }else {
            // google maps
            String url = buildGoogleMapsUrl(theDataX.getLongitude(), theDataX.getLatitude());
            openUrl(url, this);
            finish();
        }
    }

    static String buildGoogleMapsUrl(String longitude, String latitude){
        // yes, longitude and latitude are swapped :(
        String theURL = "http://maps.google.com/maps?daddr=" + longitude + "," + latitude;
        return theURL;
    }

    static String buildYandexMapsUrl(String longitude, String latitude){
        // yes, longitude and latitude are swapped :(
        String theURL = "http://maps.yandex.com/?ll=" + latitude + "," + longitude;
        return theURL;
    }


    static void openUrl(String theURL, Activity theActivity){
        Log.d(TAG, "Opening location:" + theURL);
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(theURL));
        theActivity.startActivity(intent);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }
}

