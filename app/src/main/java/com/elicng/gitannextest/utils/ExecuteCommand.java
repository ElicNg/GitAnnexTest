package com.elicng.gitannextest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.elicng.gitannextest.MainActivity;

public class ExecuteCommand extends AsyncTask<String, String, String> {

    Context mContext=null;
    public ExecuteCommand(Context _ctx)
    {
        mContext =_ctx;
    }

    ProgressDialog progressdailog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressdailog = ProgressDialog.show(mContext,
                "Executing", "Please Wait");
    }
    @Override
    protected String doInBackground(String... params) {
        Process p;
        StringBuffer output = new StringBuffer();
        try {
            p = Runtime.getRuntime().exec(params[0]);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                p.waitFor();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressdailog.dismiss();
        ((MainActivity) mContext).txtResult.setText(result); //
        Log.d("Output", result);
    }
}