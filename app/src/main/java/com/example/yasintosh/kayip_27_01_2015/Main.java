package com.example.yasintosh.kayip_27_01_2015;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.yasintosh.kayip_27_01_2015.tablar.Anasayfa;
import com.example.yasintosh.kayip_27_01_2015.tablar.IlanVer;
import com.example.yasintosh.kayip_27_01_2015.tablar.Profil;

/**
 * Created by yasin on 3.2.2015.
 */
public class Main extends TabActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablar);

        TabHost sekmeAlani = getTabHost();
        TabHost.TabSpec sekme;

        Intent i;

        i = new Intent(this, Anasayfa.class);
        sekme = sekmeAlani.newTabSpec("Anasayfa_ad").setIndicator("İlanlar").setContent(i);
        sekmeAlani.addTab(sekme);

        i = new Intent(this, Profil.class);
        sekme = sekmeAlani.newTabSpec("Profil_ad").setIndicator("Profil").setContent(i);
        sekmeAlani.addTab(sekme);
        i = new Intent(this, IlanVer.class);
        sekme = sekmeAlani.newTabSpec("IlanVer_ad").setIndicator("ilanver").setContent(i);
        sekmeAlani.addTab(sekme);


        sekmeAlani.setCurrentTab(0);


    }
}