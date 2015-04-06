package com.example.yasintosh.kayip_27_01_2015.tablar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.yasintosh.kayip_27_01_2015.Constants;
import com.example.yasintosh.kayip_27_01_2015.util.JSONParser;
import com.example.yasintosh.kayip_27_01_2015.Main;
import com.example.yasintosh.kayip_27_01_2015.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasin on 3.2.2015.
 */
public class IlanVer extends Activity{

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputBaslik;
    EditText inputAciklama;
    EditText inputIletisim;
    Spinner inputkayipturu;
    Spinner inputilanturu;
    EditText inputTarih;
    EditText inputKonum;




    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_ilanver);


        inputBaslik=(EditText)findViewById(R.id.ilanver_et_baslik);
        inputAciklama=(EditText)findViewById(R.id.ilanver_et_aciklama);
        inputIletisim=(EditText)findViewById(R.id.ilanver_et_iletisim);

        inputTarih=(EditText)findViewById(R.id.ilanver_et_tarih);
        inputKonum=(EditText)findViewById(R.id.ilanver_et_konum);


        inputkayipturu = (Spinner) findViewById(R.id.ilanver_spn_kayipturu);
        inputilanturu= (Spinner) findViewById(R.id.ilanver_spn_ilanturu);

        ArrayAdapter<CharSequence> ilanturuadapter = ArrayAdapter.createFromResource(this,
                R.array.ilanturu, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> kayipturuadapter = ArrayAdapter.createFromResource(this,
                R.array.kayipturu, android.R.layout.simple_spinner_item);
        ilanturuadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kayipturuadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputilanturu.setAdapter(ilanturuadapter);
        inputkayipturu.setAdapter(kayipturuadapter);

        Button btnCreateIlan = (Button) findViewById(R.id.ilanver_btn_kaydet);


        btnCreateIlan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new CreateIlan().execute();
            }
        });



    }

    class CreateIlan extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IlanVer.this);
            pDialog.setMessage("ilan Oluşturuluyor..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String kid =null;
            String baslik = inputBaslik.getText().toString();
            String aciklama = inputAciklama.getText().toString();
            String iletisim = inputIletisim.getText().toString();
            String kayipturu = inputkayipturu.getSelectedItem().toString();
            String ilanturu = inputilanturu.getSelectedItem().toString();
            String tarih = inputTarih.getText().toString();
            String konum = inputKonum.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("kid", kid));
            params.add(new BasicNameValuePair("baslik", baslik));
            params.add(new BasicNameValuePair("aciklama", aciklama));
            params.add(new BasicNameValuePair("iletisim", iletisim));
            params.add(new BasicNameValuePair("kayipturu", kayipturu));
            params.add(new BasicNameValuePair("ilanturu", ilanturu));
            params.add(new BasicNameValuePair("tarih", tarih));
            params.add(new BasicNameValuePair("konum", konum));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.URL_CREATE_ILAN,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

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
