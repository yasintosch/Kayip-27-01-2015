package com.example.yasintosh.kayip_27_01_2015.model;

/**
 * Created by yasin on 7.2.2015.
 */
public class Ilan {


    private int iid;
    private int kid;
    private String baslik;
    private String aciklama;
    private String iletisim;
    private String kayipturu;
    private String ilanturu;
    private String tarih;
    private String konum;
    private String image_url;
    public Ilan() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Ilan(int iid, int kid, String baslik, String aciklama, String iletisim, String kayipturu, String ilanturu, String tarih, String konum) {
        this.iid = iid;
        this.kid = kid;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.iletisim = iletisim;
        this.kayipturu = kayipturu;
        this.ilanturu = ilanturu;
        this.tarih = tarih;
        this.konum = konum;
    }


    public Ilan(int iid, int kid, String baslik, String aciklama, String iletisim, String kayipturu, String ilanturu, String tarih, String konum,String image) {
        this.iid = iid;
        this.kid = kid;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.iletisim = iletisim;
        this.kayipturu = kayipturu;
        this.ilanturu = ilanturu;
        this.tarih = tarih;
        this.konum = konum;
        this.image_url=image;
    }

    public Ilan(int iid, String baslik, String aciklamama, String tarih) {
        this.iid = iid;
        this.baslik = baslik;
        this.aciklama = aciklamama;
        this.tarih = tarih;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getBaslik() {
        return baslik;
    }


    public void setBaslik(String baslık) {
        this.baslik = baslık;
    }
    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklamama) {
        this.aciklama = aciklamama;
    }
    public String getIletisim() {
        return iletisim;
    }

    public void setIletisim(String iletisim) {
        this.iletisim = iletisim;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getIlanturu() {
        return ilanturu;
    }

    public void setIlanturu(String ilanturu) {
        this.ilanturu = ilanturu;
    }

    public String getKayipturu() {
        return kayipturu;
    }

    public void setKayipturu(String kayipturu) {
        this.kayipturu = kayipturu;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image) {
        this.image_url = image;
    }
}
