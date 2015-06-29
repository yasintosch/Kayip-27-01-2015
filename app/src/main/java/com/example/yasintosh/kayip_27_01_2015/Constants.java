package com.example.yasintosh.kayip_27_01_2015;

/**
 * Created by yasin on 6.4.2015.
 */
public class Constants {


    private static String anamakine = "http://192.168.3.255";
    private static String anamakine2 = "http:--192.168.3.255";
    //----Giris---
    public static String URL_GIRIS = anamakine+"/kayipwebservis/kullanici_girisi.php";
    //Kayit
    public static String URL_CREATE_KULLANICI = anamakine+"/kayipwebservis/create_kullanici.php";
    //Anasayfa
    public static String URL_ALL_ILANLAR = anamakine+"/kayipwebservis/tumilanlar.php";
    //ilan ver
    public static String URL_CREATE_ILAN = anamakine + "/kayipwebservis/ilanver.php";
    public static final String FILE_UPLOAD_URL = anamakine + "/kayipwebservis/fileupload.php";
    public static final String UPLOADED_FILE_URL = anamakine2 + "-uploads-";
    public static final String IMAGE_DIRECTORY_NAME = "Kayip File Upload";

    public static final String TAG_SUCCESS = "success";
    public  static final String TAG_ILANLAR = "ilanlar";
    public  static final String TAG_IID = "iid";
    public  static final String TAG_KID = "kid";
    public  static final String TAG_BASLIK = "baslik";
    public  static final String TAG_ACIKLAMA = "aciklama";
    public static final String TAG_TELEFON = "telefon";
    public static final String TAG_EMAIL = "email";
    public  static final String TAG_KAYIPTURU = "kayipturu";
    public  static final String TAG_ILANTURU = "ilanturu";
    public  static final String TAG_TARİH = "tarih";
    public static final String TAG_KONUM_LAT = "latkonum";
    public static final String TAG_KONUM_LONG = "longkonum";
    public  static final String TAG_KONUM = "konum";
    public  static final String TAG_IMAGE_URL = "image_url";
}
