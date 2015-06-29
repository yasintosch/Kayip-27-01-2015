package com.example.yasintosh.kayip_27_01_2015.tablar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.yasintosh.kayip_27_01_2015.Constants;
import com.example.yasintosh.kayip_27_01_2015.Main;
import com.example.yasintosh.kayip_27_01_2015.MapsActivity;
import com.example.yasintosh.kayip_27_01_2015.R;
import com.example.yasintosh.kayip_27_01_2015.app.AppController;
import com.example.yasintosh.kayip_27_01_2015.util.AndroidMultiPartEntity;
import com.example.yasintosh.kayip_27_01_2015.util.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yasin on 3.2.2015.
 */
public class IlanVer extends Activity{
    private static final String TAG = IlanVer.class.getSimpleName();
    private ProgressDialog pDialog;
    int hour, minute, year, month, day;
    private ImageButton resimEdit, baslikEdit, aciklamaEdit, telefonEdit, tarihEdit, konumEdit, emailEdit, ilanturuEdit, kayipTuruEdit;
    private EditText inputBaslik, inputAciklama, inputTelefon, inputTarih, inputEmail, inputKonum, inputIlanturu, inputKayipturu;
    private ImageView image;
    JSONParser jsonParser = new JSONParser();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri fileUri;
    private String filePath = null;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private File mediaFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_ilanver2);

//------------------------Edit Text tanımlama-------------
        inputBaslik=(EditText)findViewById(R.id.ilanver_et_baslik);
        inputAciklama=(EditText)findViewById(R.id.ilanver_et_aciklama);
        inputTelefon = (EditText) findViewById(R.id.ilanver_et_telefon);
        inputEmail = (EditText) findViewById(R.id.ilanver_et_email);
        inputTarih=(EditText)findViewById(R.id.ilanver_et_tarih);
        inputKonum=(EditText)findViewById(R.id.ilanver_et_konum);
        inputIlanturu = (EditText) findViewById(R.id.ilanver_et_ilanturu);
        inputKayipturu = (EditText) findViewById(R.id.ilanver_et_kayipturu);

        image = (ImageView) findViewById(R.id.ilanver_iv_ilan_image);
//-------------------------Button Tanımlama-------------------
        //image.setDefaultImageResId(R.drawable.noimage);
        resimEdit = (ImageButton) findViewById(R.id.ilanver_btn_image_edit);
        baslikEdit = (ImageButton) findViewById(R.id.ilanver_btn_baslik_edit);
        aciklamaEdit = (ImageButton) findViewById(R.id.ilanver_btn_aciklama_edit);
        telefonEdit = (ImageButton) findViewById(R.id.ilanver_btn_telefon_edit);
        emailEdit = (ImageButton) findViewById(R.id.ilanver_btn_email_edit);
        tarihEdit = (ImageButton) findViewById(R.id.ilanver_btn_tarih_edit);
        konumEdit = (ImageButton) findViewById(R.id.ilanver_btn_konum_edit);
        ilanturuEdit = (ImageButton) findViewById(R.id.ilanver_btn_ilanturu_edit);
        kayipTuruEdit = (ImageButton) findViewById(R.id.ilanver_btn_kayipturu_edit);
//---------------------tarih

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        resimEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        tarihEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });
        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(true);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Dosya Yolu bulunamadı..", Toast.LENGTH_LONG).show();
        }


        ImageButton btnOnay = (ImageButton) findViewById(R.id.ilanver_btn_onay);
        ImageButton btnIptal = (ImageButton) findViewById(R.id.ilanver_btn_iptal);

        konumEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(map);
            }
        });
        btnOnay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new CreateIlan().execute();
                previewMedia(true);
            }
        });
        btnIptal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int i) {


        if (i == 3)
            return new DatePickerDialog(this, datePickerListener, year, month, day);


        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) {

            year = yearSelected;
            month = monthOfYear;
            day = dayOfMonth;
            StringBuilder sb = new StringBuilder();
            if (day >= 10)
                sb.append(String.valueOf(day) + ".");
            else
                sb.append("0" + String.valueOf(day) + ".");

            if (month >= 9)
                sb.append(String.valueOf(month + 1) + ".");
            else
                sb.append("0" + String.valueOf(month + 1) + ".");

            sb.append(String.valueOf(year));
            inputTarih.setText(sb.toString());
        }
    };

    private void selectImage() {
        final CharSequence[] items = {"Kameradan Seç", "Galeriden Seç",
                "İptal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(IlanVer.this);
        builder.setTitle("Resim Seç!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Kameradan Seç")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri();
                    filePath = fileUri.getPath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent.putExtra("filePath", filePath);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Galeriden Seç")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("İptal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                //           Intent i = getIntent();
                //               filePath = i.getStringExtra("filePath");
                new UploadFileToServer().execute();
            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "Kullanıcı iptal etti..", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Resim çekilemedi..", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, Constants.IMAGE_DIRECTORY_NAME + " adlı klasör oluşturulamadı..");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());


        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");


        return mediaFile;
    }

    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            image.setImageBitmap(bitmap);
        }

    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.FILE_UPLOAD_URL);

            try {

                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });


                File sourceFile = new File(filePath);

                entity.addPart("image", new FileBody(sourceFile));

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Http hata Kodu:: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Server cevabı: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }


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
            String kid = "a";
            String baslik = inputBaslik.getText().toString();
            String aciklama = inputAciklama.getText().toString();
            String telefon = inputTelefon.getText().toString();
            String email = inputEmail.getText().toString();
            String tarih = inputTarih.getText().toString();
            String konum = inputKonum.getText().toString();
            String longkonum = "a";
            String latkonum = "a";
            String ilanturu = inputIlanturu.getText().toString();
            String kayipturu = inputKayipturu.getText().toString();
            String image_url = Constants.FILE_UPLOAD_URL;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_KID, kid));
            params.add(new BasicNameValuePair(Constants.TAG_BASLIK, baslik));
            params.add(new BasicNameValuePair(Constants.TAG_ACIKLAMA, aciklama));
            params.add(new BasicNameValuePair(Constants.TAG_TELEFON, telefon));
            params.add(new BasicNameValuePair(Constants.TAG_EMAIL, email));
            params.add(new BasicNameValuePair(Constants.TAG_ILANTURU, ilanturu));
            params.add(new BasicNameValuePair(Constants.TAG_TARİH, tarih));
            params.add(new BasicNameValuePair(Constants.TAG_KONUM, konum));
            params.add(new BasicNameValuePair(Constants.TAG_KONUM_LONG, longkonum));
            params.add(new BasicNameValuePair(Constants.TAG_KONUM_LAT, latkonum));
            params.add(new BasicNameValuePair(Constants.TAG_ILANTURU, ilanturu));
            params.add(new BasicNameValuePair(Constants.TAG_KAYIPTURU, kayipturu));
            params.add(new BasicNameValuePair(Constants.UPLOADED_FILE_URL + "-" + mediaFile.getName(), image_url));


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
                    Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_SHORT).show();
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


    /**
     * Uploading the file to server
     */

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
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
