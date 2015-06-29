package com.example.yasintosh.kayip_27_01_2015.util;

/**
 * Created by yasin on 7.2.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.yasintosh.kayip_27_01_2015.R;
import com.example.yasintosh.kayip_27_01_2015.app.AppController;
import com.example.yasintosh.kayip_27_01_2015.model.Ilan;
import com.example.yasintosh.kayip_27_01_2015.tablar.Anasayfa;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter   implements OnClickListener {

    private Activity activity;
    private ArrayList<Ilan> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Ilan tempValues=null;
    int i=0;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{

        public TextView baslik;

        public TextView aciklama;
        public TextView tarih;

        public NetworkImageView image;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.tab_anasayfa_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();

            holder.baslik=(TextView)vi.findViewById(R.id.tv_anasayfa_row_baslik);
            holder.aciklama=(TextView)vi.findViewById(R.id.tv_anasayfa_row_aciklama);
            holder.tarih=(TextView)vi.findViewById(R.id.tv_anasayfa_row_tarih);
            holder.image=(NetworkImageView)vi.findViewById(R.id.list_image);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.aciklama.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( Ilan ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.baslik.setText( tempValues.getBaslik() );
            holder.tarih.setText( tempValues.getTarih() );
            holder.aciklama.setText( tempValues.getAciklama() );
            try {
                if (!tempValues.getImage_url().equals(null)) {
                    holder.image.setImageUrl(tempValues.getImage_url().replaceAll("-", "/"), imageLoader);
                } else {
                    holder.image.setDefaultImageResId(R.drawable.no_photo);
                }
            } catch (Exception e) {
                holder.image.setDefaultImageResId(R.drawable.no_photo);
            }


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {


            Anasayfa d = (Anasayfa)activity;


            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            d.onItemClick(mPosition);

           // p.onItemClick(mPosition);

        }
    }

}