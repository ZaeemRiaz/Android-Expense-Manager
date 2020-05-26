package com.base.software_for_mobile_devices_project;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataSyncService extends Service {
    private static final String TAG = "=== DataSyncService ===";

    Messenger messenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private ArrayList<Transaction> parseImport(String xmlText) {
        ArrayList<Transaction> ret = new ArrayList<>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xmlText));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                if (event == XmlPullParser.START_TAG && parser.getName().equals("student")) {
                    String date = parser.getAttributeValue(null, "date");
                    double amount = Double.parseDouble(parser.getAttributeValue(null, "amount"));
                    String description = parser.getAttributeValue(null, "description");
                    ret.add(new Transaction(date, amount, description));
                }
                event = parser.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    private class ImportTask extends AsyncTask<String, Void, Void> {
        ArrayList<Transaction> transactions;

        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: import init");
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                StringBuilder content = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String xmlText;
                while ((xmlText = reader.readLine()) != null) {
                    content.append(xmlText);
                }

                xmlText = content.toString();
                transactions = parseImport(xmlText);

                connection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: import init");
            ContentValues cv;

            if (!transactions.isEmpty()) {
                for (Transaction transaction : transactions) {
                    try {
                        getContentResolver().insert(TransactionProvider.CONTENT_URI, transaction.getContentValues());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Import Successful. Please Refresh.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

//    private class ExportTask extends AsyncTask<String, Void, Void> {
//        ArrayList<Student> students;
//        String date;
//        boolean success;
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            Log.d(TAG, "doInBackground: export init");
//            date = strings[0];
//            try {
//                URL url = new URL(strings[1]);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setReadTimeout(10000);
//                connection.setConnectTimeout(15000);
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-type", "text/xml");
//                connection.setDoOutput(true);
//                connection.connect();
//
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//                writer.write(getAttendanceContent(date));
//                writer.flush();
//
//                StringBuilder content = new StringBuilder();
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(connection.getInputStream()));
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    content.append(line);
//                }
//                Log.d(TAG, "doInBackground: Server Response: " + content.toString());
//
//                connection.disconnect();
//                success = true;
//            } catch (Exception ex) {
//                success = false;
//                ex.printStackTrace();
//            }
//
//            return null;
//        }
//
//        protected void onPostExecute(Void aVoid) {
//            Log.d(TAG, "onPostExecute: export init");
//            Toast toast;
//            if (success) {
//                toast = Toast.makeText(getApplicationContext(), "Export Successful.", Toast.LENGTH_SHORT);
//            } else {
//                toast = Toast.makeText(getApplicationContext(), "Export Not Successful.", Toast.LENGTH_SHORT);
//            }
//            toast.show();
//        }
//    }

    public class IncomingHandler extends Handler {

        public void handleMessage(Message message) {
            String url;
            url = (String) message.getData().getSerializable("url");
            switch (message.what) {
                case 1:
                    Log.d(TAG, "importProcessor: calling ImportTask");
                    new ImportTask().execute(url);
                    break;
                case 2:
                    Log.d(TAG, "exportProcessor: calling ExportTask");
//                    new ExportTask().execute(url);
                    break;
            }
            super.handleMessage(message);
        }
    }
}
