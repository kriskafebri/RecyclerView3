package com.jasahub.recyclerview3;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jasahub.recyclerview3.model.DataHotel;

public class InputActivity extends AppCompatActivity
{

    static final int REQUEST_IMAGE_GET = 1;
    EditText etJudul;
    EditText etDeskripsi;
    EditText etDetail;
    EditText etLokasi;
    ImageView ivFoto;
    Uri uriFoto;
    DataHotel place;
    boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etJudul = (EditText) findViewById(R.id.editTextNama);
        etDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);
        etDetail = (EditText) findViewById(R.id.editTextDetail);
        etLokasi = (EditText) findViewById(R.id.editTextLokasi);
        ivFoto = (ImageView) findViewById(R.id.imageViewFoto);

        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();

            }
        });

        findViewById(R.id.buttonSimpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();

            }
        });

        long id = getIntent().getLongExtra(MainActivity.PLACE, -1);
        if (id != -1)
        {
            place = DataHotel.findById(DataHotel.class, id);
            setTitle("Edit " + place.judul);
            fillData();
            isNew = false;
        }
        else
        {
            setTitle("New Place");
            isNew = true;
        }

    }

    private void fillData() {
        etJudul.setText(place.judul);
        etDeskripsi.setText(place.deskripsi);
        etDetail.setText(place.detail);
        etLokasi.setText(place.lokasi);
        uriFoto = Uri.parse(place.foto);
        ivFoto.setImageURI(uriFoto);
    }

    private void doSave() {
        String judul = etJudul.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String detail = etDetail.getText().toString();
        String lokasi = etLokasi.getText().toString();

        if (isValid(judul, deskripsi, detail, lokasi, uriFoto))
        {
            if (isNew)
            {
                place = new DataHotel(judul, deskripsi,
                        detail, lokasi, uriFoto.toString());
                place.save();
            }
            else
            {
                place.judul = judul;
                place.deskripsi = deskripsi;
                place.detail = detail;
                place.lokasi = lokasi;
                place.foto = uriFoto.toString();
                place.save();
            }

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean isValid(String judul, String deskripsi, String detail, String lokasi, Uri uriFoto) {
        boolean valid = true;
        if (judul.isEmpty()) {
            setErrorEmpty(etJudul);
            valid = false;
        }
        if (deskripsi.isEmpty()) {
            setErrorEmpty(etDeskripsi);
            valid = false;
        }
        if (detail.isEmpty()) {
            setErrorEmpty(etDetail);
            valid = false;
        }
        if (lokasi.isEmpty()) {
            setErrorEmpty(etLokasi);
            valid = false;
        }
        if (uriFoto == null) {
            Snackbar.make(ivFoto, "Foto Belum Ada", Snackbar.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void setErrorEmpty(EditText editText) {
        editText.setError(((TextInputLayout) editText.getParent().getParent()).getHint() + " Belum Diisi");

    }


    private void pickPhoto() {
        Intent intent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        else
        {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK)
        {
            uriFoto = data.getData();
            ivFoto.setImageURI(uriFoto);
        }
    }
}

