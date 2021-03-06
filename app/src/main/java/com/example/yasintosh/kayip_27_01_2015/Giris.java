package com.example.yasintosh.kayip_27_01_2015;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yasintosh.kayip_27_01_2015.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Giris extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputemail;
    EditText inputsifre;
    TextView kaydol;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        kaydol=(TextView)findViewById(R.id.tv_Kaydol);
        inputemail=(EditText)findViewById(R.id.et_email_giris);
        inputsifre=(EditText)findViewById(R.id.et_sifre_giris);



        kaydol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),
                        Kayit.class);
                startActivity(i);
            }
        });
        Button giris=(Button) findViewById(R.id.btn_giris);
        giris.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new GirisYap().execute();
            }
        });


    }
    class GirisYap extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Giris.this);
            pDialog.setMessage("Giriş Yapılıyor..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String email = inputemail.getText().toString();
            String sifre = inputsifre.getText().toString();
            String sonuc = null;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("sifre", sifre));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.URL_GIRIS,
                    "GET", params);

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
                    Log.d("Create Response", json.toString());
                    sonuc = json.getString("message");
                    return sonuc;
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return sonuc;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String sonuc) {
            // dismiss the dialog once done
            showAlert(sonuc);
            pDialog.dismiss();
        }

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Server Yanıtı")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
