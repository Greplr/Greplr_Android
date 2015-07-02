package com.greplr;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by raghav on 29/06/15.
 */
public class Utils {
    public static String readJSONFile(Context context, String jsonFileName){
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(jsonFileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e(LOG_TAG, "File not found: " + e.toString());
        } catch (IOException e) {
           // Log.e(LOG_TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static void writeJSONFile(String jsonString, Context context, String jsonFileName) {
        File jsonFile = new File(context.getFilesDir(), jsonFileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(jsonFile);
            fos.write(jsonString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
