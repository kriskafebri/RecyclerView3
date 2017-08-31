package com.jasahub.recyclerview3.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by SE on 30/08/2017.
 */

/*java class yang meng extends SugarRecord yang akan digunakan sebagai Table*/
/*merubah apapun di file java ini (Dataprofile.java), VERSION databasenya harus naikan juga*/

public class DataHotel extends SugarRecord implements Serializable {
    public String judul;
    public String deskripsi;
    public String detail;
    public String lokasi;
    public String foto;

    public DataHotel()
    {
    }

    public DataHotel(String judul, String deskripsi, String detail, String lokasi,
                     String foto)
    {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.detail = detail;
        this.lokasi = lokasi;
        this.foto = foto;
    }


}
