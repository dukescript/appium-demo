package com.dukescript.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import com.dukescript.demo.js.PlatformServices;

public class AndroidMain extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.setWebContentsDebuggingEnabled(true);

        try {
            // delegate to original activity
            startActivity(new Intent(getApplicationContext(), Class.forName("com.dukescript.presenters.Android")));
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
        finish();
    }

    public static void main(android.content.Context context) throws Exception {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(AndroidMain.class.getPackage().getName(), 0);
        DataModel.onPageLoad(new AndroidServices(prefs));
    }

    private static final class AndroidServices extends PlatformServices {

        private final SharedPreferences prefs;

        AndroidServices(SharedPreferences prefs) {
            this.prefs = prefs;
        }

        @Override
        public String getPreferences(String key) {
            return prefs.getString(key, null);
        }

        @Override
        public void setPreferences(String key, String value) {
            prefs.edit().putString(key, value).apply();
        }
    }
}
