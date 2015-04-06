package com.example.yasintosh.kayip_27_01_2015;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yasintosh.kayip_27_01_2015.util.JSONParser;

/**
 * Created by yasin on 3.2.2015.
 */
public class Kayit extends Activity {


    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputAd;
    EditText inputEmail;
    EditText inputSifre;



    // JSON Node names



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        // Edit Text
        inputAd = (EditText) findViewById(R.id.et_ad_kayit);
        inputEmail = (EditText) findViewById(R.id.et_email_kayit);
        inputSifre = (EditText) findViewById(R.id.et_sifre_kayit);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btn_kayit);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateKullanici().execute();
            }
        });
    }

    class CreateKullanici extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Kayit.this);
            pDialog.setMessage("Kullanıcı Oluşturuluyor..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
    protected String doInBackground(String... args) {
        String ad = inputAd.getText().toString();
        String email = inputEmail.getText().toString();
        String sifre = inputSifre.getText().toString();

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ad", ad));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("sifre", sifre));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(Constants.URL_CREATE_KULLANICI,
                "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(Constants.TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);

                // closing this screen
                finish();
            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        pDialog.dismiss();
    }

}


}
