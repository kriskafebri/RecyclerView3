package com.jasahub.recyclerview3;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jasahub.recyclerview3.adapter.PlaceAdapter;
import com.jasahub.recyclerview3.model.DataHotel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlaceAdapter.IPlaceAdapter {
    public static final String PLACE = "place";
    public static final int REQUEST_CODE_ADD = 88;
    public static final int REQUEST_CODE_EDIT = 99;
    ArrayList<DataHotel> mList = new ArrayList<>();
    PlaceAdapter mAdapter;
    String mQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAdd();

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlaceAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        if (DataHotel.count(DataHotel.class) == 0)
            fillDataToDB();

        refreshData(null);
    }

    private void refreshData(String query) {
        mList.clear();

        if (query == null || query.isEmpty())
            mList.addAll(DataHotel.listAll(DataHotel.class));
        else
            mList.addAll(DataHotel.find(DataHotel.class, "judul LIKE ? OR deskripsi LIKE ?" + " OR lokasi LIKE ?", "%" + query + "%", "%" + query + "%", "%" + query + "%"));

        mAdapter.notifyDataSetChanged();
    }

    private void fillDataToDB() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.places);
        String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
        String[] arDetail = resources.getStringArray(R.array.place_details);
        String[] arLokasi = resources.getStringArray(R.array.place_locations);
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        String[] arFoto = new String[a.length()];
        for (int i = 0; i < arFoto.length; i++)
        {
            int id = a.getResourceId(i, 0);
            arFoto[i] = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(id) + '/'
                    + resources.getResourceTypeName(id) + '/'
                    + resources.getResourceEntryName(id);
        }
        a.recycle();

        List<DataHotel> places = new ArrayList<>();
        for (int i = 0; i < arJudul.length; i++)
        {
            places.add(new DataHotel(arJudul[i], arDeskripsi[i],
                    arDetail[i], arLokasi[i], arFoto[i]));
        }
        DataHotel.saveInTx(places);
    }

    private void goAdd() {
        startActivityForResult(new Intent(this, InputActivity.class), REQUEST_CODE_ADD);
    }

    /*private void filldata() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.places);
        String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
        String[] arDetail = resources.getStringArray(R.array.place_details);
        String[] arLokasi = resources.getStringArray(R.array.place_locations);
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        String[] arFoto = new String[a.length()];

        for (int i = 0; i < arFoto.length; i++) {
            int id = a.getResourceId(i, 0);
            arFoto[i] = ContentResolver.SCHEME_ANDROID_RESOURCE+"://"
                    + resources.getResourcePackageName(id)+'/'
                    + resources.getResourceTypeName(id)+'/'
                    + resources.getResourceEntryName(id);

        }

        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            mList.add(new Hotel(arJudul[i], arDeskripsi[i], arDetail[i], arLokasi[i], arFoto[i]));
        }
        mAdapter.notifyDataSetChanged();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK)
        {
            refreshData(mQuery);
        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK)
        {
            refreshData(mQuery);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener()
                {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        mQuery = newText.toLowerCase();
                        refreshData(mQuery);
                        return true;
                    }
                });

        return true;
    }

   /* private void doFilter(String query) {
        if (!isFiltered) {
            mListAll.clear();
            mListAll.addAll(mList);
            isFiltered = true;
        }
        mList.clear();
        if (query.isEmpty()) {
            mList.addAll(mListAll);
            isFiltered = false;
        } else {
            mListMapFilter.clear();
            for (int i = 0; i < mListAll.size(); i++) {
                Hotel hotel = mListAll.get(i);
                if (hotel.judul.toLowerCase().contains(query) ||
                        hotel.deskripsi.toLowerCase().contains(query) ||
                        hotel.lokasi.toLowerCase().contains(query)) {
                    mList.add(hotel);
                    mListMapFilter.add(i);

                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(PLACE, mList.get(pos).getId());
        startActivity(intent);

    }

    @Override
    public void doEdit(int pos) {
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra(PLACE, mList.get(pos).getId());
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void doDelete(int pos) {
        final DataHotel place = mList.get(pos);
        mList.remove(pos);
        mAdapter.notifyDataSetChanged();
        Snackbar.Callback callback = new Snackbar.Callback()
        {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event)
            {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                    place.delete();
                super.onDismissed(transientBottomBar, event);
            }
        };
        Snackbar.make(findViewById(R.id.fab), place.judul + " Terhapus", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        refreshData(mQuery);
                    }
                })

                .show();

    }


    @Override
    public void doFav(int pos) {

    }

    @Override
    public void doShare(int pos) {

    }
}


