package com.example.randompokemongenerator;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class CompletedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        System.out.println("got here");

        /*
        TODO:
            is triggered when DownloadManager is started
            here somehow image needs to be fetched and saved it to the Imageview
         */
        //New
        Toast.makeText(context,"Download Complete", Toast.LENGTH_SHORT).show();

        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

        }
    }


}
