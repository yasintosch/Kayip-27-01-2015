package com.example.yasintosh.kayip_27_01_2015.tablar;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.yasintosh.kayip_27_01_2015.Constants;
import com.example.yasintosh.kayip_27_01_2015.util.CustomAdapter;
import com.example.yasintosh.kayip_27_01_2015.model.Ilan;
import com.example.yasintosh.kayip_27_01_2015.IlanDetay;
import com.example.yasintosh.kayip_27_01_2015.util.JSONParser;
import com.example.yasintosh.kayip_27_01_2015.R;

/**
 * Created by yasin on 3.2.2015.
 */
public class Anasayfa extends Activity {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();






    JSONArray products = null;



    public ArrayList<Ilan> CustomListViewValuesArr = new ArrayList<Ilan>();
    public  Anasayfa anasayfa = null;
    ListView list;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_anasayfa);

        anasayfa=this;
        new TumIlanlariGoster().execute();

    }

    public void onItemClick(int mPosition) {

        Ilan tempValues = CustomListViewValuesArr.get(mPosition);


        Intent intentim = new Intent(Anasayfa.this,IlanDetay.class);

        intentim.putExtra("baslik", tempValues.getBaslik());
        intentim.putExtra("aciklama", tempValues.getAciklama());
        intentim.putExtra("iletisim", tempValues.getIletisim());
        intentim.putExtra("kayipturu", tempValues.getKayipturu());
        intentim.putExtra("ilanturu", tempValues.getIlanturu());
        intentim.putExtra("tarih", tempValues.getTarih());
        intentim.putExtra("konum", tempValues.getKonum());
        intentim.putExtra("image_url", tempValues.getImage_url());

        startActivity(intentim);
    }

    class TumIlanlariGoster extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Anasayfa.this);
            pDialog.setMessage("İlanlar listeleniyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(Constants.URL_ALL_ILANLAR, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("ilanlar: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(Constants.TAG_ILANLAR);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String iid = c.getString(Constants.TAG_IID);
                        String kid = c.getString(Constants.TAG_KID);
                        String baslik = c.getString(Constants.TAG_BASLIK);
                        String aciklama = c.getString(Constants.TAG_ACIKLAMA);
                        String iletisim = c.getString(Constants.TAG_ILETISIM);
                        String kayipturu = c.getString(Constants.TAG_KAYIPTURU);
                        String ilanturu = c.getString(Constants.TAG_ILANTURU);
                        String tarih = c.getString(Constants.TAG_TARİH);
                        String konum=c.getString(Constants.TAG_KONUM);
                       String image_url= c.getString(Constants.TAG_IMAGE_URL);



                        Ilan ilan =new Ilan(Integer.parseInt(iid),Integer.parseInt(kid),baslik,aciklama,iletisim,kayipturu,ilanturu,tarih,konum,image_url);
                        // adding HashList to ArrayList
                        CustomListViewValuesArr.add(ilan);

                    }
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
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    Resources res =getResources();
                    //setListData();
                    list = ( ListView )findViewById( R.id.list_ilanlar );
                    adapter=new CustomAdapter(anasayfa, CustomListViewValuesArr, res);

                    list.setAdapter( adapter );
                }
            });

        }

    }


}
