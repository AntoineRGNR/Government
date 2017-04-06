package com.example.antoine.knowyourgovernment;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by antoine on 4/6/17.
 */

public class AsyncTaskLoadOfficial extends AsyncTask<String, Integer, String>
{ //  <Parameter, Progress, Result>

    private int count;

    private final String TAGAS = "AsyncTask";

    private final String dataURL = "https://www.googleapis.com/civicinfo/v2/representatives?key=";

    private String API_KEY = "AIzaSyBrVeqK9HJFogKiinQciLobFRNrJGLjYjU";

    private MainActivity mainActivity;

    private String symbolAsk;

    public AsyncTaskLoadOfficial(MainActivity ma)
    {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... params)
    {
        Log.d(TAGAS, "doInBackground: " + params[0]);

        Uri dataUri = Uri.parse(dataURL);
        String urlToUse = dataUri.toString() + API_KEY + "&address=" + params[0];
        symbolAsk = params[0];
        Log.d(TAGAS, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try
        {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
                Log.d(TAGAS, "doInBackground: " + sb.toString());
            }
        }
        catch (Exception e)
        {
            Log.e(TAGAS, "doInBackground: ", e);
            return null;
        }

        Log.d(TAGAS, "NE doInBackground: " + sb.toString());

        return sb.toString();
    }

    @Override
    protected void onPreExecute()
    {
        // I have nothing useful to do here so leaving it empty
        Log.d(TAGAS, "onPreExecute");

    }

    @Override
    protected void onPostExecute(String string)
    {
        Log.d(TAGAS, "onPostExecute: " + string);
        multipleStockDialog(parseJSON(string));
    }

    private ArrayList<Politician> parseJSON(String s)
    {
        Log.d(TAGAS, "parseJSON: Start: ");
        ArrayList<Politician> stockFound = new ArrayList<>();
        try
        {
            JSONArray jObjMain = new JSONArray(s);
            count = jObjMain.length();
            if(count != 0){
                for (int i = 0; i < jObjMain.length(); i++)
                {
                    JSONObject jStock = (JSONObject) jObjMain.get(i);
                    String name = jStock.getString("company_name");
                    Log.d(TAGAS, "parseJSON: " + name);
                    String symbol = jStock.getString("company_symbol");
                    Log.d(TAGAS, "parseJSON: " + symbol);

                    //stockFound.add(new Stock(symbol, name, 0, 0, 0));
                }
                Log.d(TAGAS, "parseJSON: Return");
                return stockFound;
            }
        } catch (Exception e) {
            Log.d(TAGAS, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void multipleStockDialog(final ArrayList<Politician> stockList)
    {
        Log.d(TAGAS, "multipleStockDialog: Start");

        if(stockList != null)
        {
            int size = stockList.size();
            final CharSequence[] sArray = new CharSequence[size];
            for (int i = 0; i < size; i++)
            {
               // sArray[i] = stockList.get(i).getStockSymbol() + " - " + stockList.get(i).getCompanyName();
                Log.d(TAGAS, "multipleStockDialog: " + sArray[i]);
            }
            if(size == 1)
            {
                //mainActivity.processNewStock(stockList.get(0));
            }
            else
            {
                Log.d(TAGAS, "multipleStockDialog: AlertDialogBuilder");
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                Log.d(TAGAS, "multipleStockDialog: AlertDialogBuilder mainActivity");
                builder.setTitle("Choose a Symbol");
                builder.setItems(sArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // mainActivity.processNewStock(stockList.get(which));
                    }
                });
                builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else
        {
            errorStockNotFound(symbolAsk);
        }
    }

    public void errorStockNotFound(String stockSymbol)
    {
        Log.d(TAGAS, "errorStockNotFound");
        //AlertDialog Declaration and Initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage("No Data Found For This Symbol");
        builder.setTitle("Symbol Not Found: " + stockSymbol);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

