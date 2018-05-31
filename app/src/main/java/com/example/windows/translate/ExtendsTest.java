package com.example.windows.translate;

import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ExtendsTest {
    static ProgressTask qwerty;
    static String string = "";
    static String str1 = "";
    static String str2 = "";

    public static void translate() throws JSONException {
        switch (Fragment_Translate.TV1.getText().toString()) {
            case "Английский" :
                str1 = "en";
                break;
            case "Русский" :
                str1 = "ru";
                break;
            case "Казахский" :
                str1 = "kk";
                break;
            case "Турецкий" :
                str1 = "tr";
                break;
        }
        switch (Fragment_Translate.TV2.getText().toString()) {
            case "Английский" :
                str2 = "en";
                break;
            case "Русский" :
                str2 = "ru";
                break;
            case "Казахский" :
                str2 = "kk";
                break;
            case "Турецкий" :
                str2 = "tr";
                break;
        }
        qwerty = new ProgressTask();
        qwerty.execute();
    }

    public static String getJsonStringYandex(String trans, String text) throws IOException, ParseException, JSONException, org.json.simple.parser.ParseException {
        String apiKey = "trnsl.1.1.20170401T082558Z.dc2be5e0736f0e64.bbc664f5edc2f418a41153d0199e528316d94c2f";
        String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + apiKey + "&lang=" + trans + "&text=" + text;
        URL url = new URL(requestUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        int rc = httpConnection.getResponseCode();
        if (rc == 200) {
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            return getTranslateFromJSON(strBuilder.toString());
        }
        return text;
    }

    public static String getTranslateFromJSON(String str) throws org.json.simple.parser.ParseException, JSONException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(str);
        StringBuilder sb = new StringBuilder();
        JSONArray array = (JSONArray) object.get("text");
        for (Object s : array) {
            sb.append(s.toString() + "\n");
        }
        return sb.toString();
    }
    private static class ProgressTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d("mylogss", str1 + "-----" + str2);
                string = getJsonStringYandex(str1 + "-" + str2, Fragment_Translate.strOr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Fragment_Translate.TV.setText(string);
        }
    }
}