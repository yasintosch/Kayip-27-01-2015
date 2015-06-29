package com.example.yasintosh.kayip_27_01_2015;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.yasintosh.kayip_27_01_2015.app.AppController;

/**
 * Created by yasin on 7.2.2015.
 */
public class IlanDetay extends Activity {

    TextView txtBaslik,txtAciklama,txtIletisim,txtKayipturu,txtIlanturu,txtTarih,txtKonum;
    NetworkImageView image;
    String baslik,aciklama,iletisim,kayipturu,ilanturu,tarih,konum,image_url;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ilan_detay_2);

        txtBaslik = (TextView) findViewById(R.id.ilandetay_tv_baslik);
        txtAciklama = (TextView) findViewById(R.id.ilandetay_tv_aciklama);
        //txtIletisim=(TextView)findViewById(R.id.ilandetay_tv);
        txtKayipturu = (TextView) findViewById(R.id.ilandetay_tv_kayipturu);
        txtIlanturu = (TextView) findViewById(R.id.ilandetay_tv_ilanturu);
        txtTarih = (TextView) findViewById(R.id.ilandetay_tv_tarih);
        //txtKonum=(TextView)findViewById(R.id.ilan_detay_konum);
        image = (NetworkImageView) findViewById(R.id.ilandetay_iv_ilan_image);

        Bundle paketim  = new  Bundle();
        paketim = getIntent().getExtras();
        baslik=paketim.getString("baslik");
        aciklama=paketim.getString("aciklama");
        iletisim=paketim.getString("iletisim");
        kayipturu=paketim.getString("kayipturu");
        ilanturu=paketim.getString("ilanturu");
        tarih=paketim.getString("tarih");
        konum=paketim.getString("konum");
        image_url=paketim.getString("image_url");




        txtBaslik.setText(baslik);
        txtAciklama.setText(aciklama);
        // txtIletisim.setText(iletisim);
        txtKayipturu.setText(kayipturu);
        txtIlanturu.setText(ilanturu);
        txtTarih.setText(tarih);
        //txtKonum.setText(konum);
        image.setImageUrl(image_url.replaceAll("-","/"),imageLoader);



    }
}
