package com.greplr.common.utils;

import android.os.AsyncTask;

/**
 * Created by prempal on 9/7/15.
 */
public class IsOnlineTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = process.waitFor();
            return returnVal == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
