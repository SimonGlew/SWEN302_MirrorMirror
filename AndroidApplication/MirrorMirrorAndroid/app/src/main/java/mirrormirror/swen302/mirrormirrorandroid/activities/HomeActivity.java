package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.activities.CameraPreviewActivity;
import mirrormirror.swen302.mirrormirrorandroid.adapters.HorizontalAdapter;
import mirrormirror.swen302.mirrormirrorandroid.utilities.DateTimeManager;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ImageStorageManager;
import mirrormirror.swen302.mirrormirrorandroid.utilities.InputWeightDialog;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ServerController;

/**
 * Created by bondkyal on 10/08/17.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;

    public static final int CAMERA_ACTIVITY_REQUEST_CODE = 10;

    RecyclerView recyclerView;
    HorizontalAdapter horizontalAdapter;

    List<String> filePaths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        ActionBar titleBar = getSupportActionBar();
        titleBar.setDisplayHomeAsUpEnabled(true);

        setupDrawerLayout();

        initialLoadImages();
        setRecyclerView();
    }

    private void setupDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void setRecyclerView(){
        recyclerView = (RecyclerView)findViewById(R.id.horizontal_recycler_view);
        horizontalAdapter = new HorizontalAdapter(filePaths,this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(horizontalAdapter);
    }


    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.take_image){
            Intent intent = new Intent(this, CameraPreviewActivity.class);
            startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE);
        } else if(item.getItemId() == R.id.input_weight){
            //Popup weight input dialog
            InputWeightDialog iwd = new InputWeightDialog(this);
            iwd.show();
        }
        else if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadImages();
    }

    private void initialLoadImages(){
        File[] files = getFilesDir().listFiles();
        filePaths = new ArrayList<>(files.length);
        for(int i = files.length-1; i >= 0; i --){
            filePaths.add(files[i].getName());
        }
    }

    private void loadImages(){
        File[] files = getFilesDir().listFiles();
        for(int i = filePaths.size(); i < files.length; i ++){
            filePaths.add(0,files[i].getName());
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    //React to items selected within the sidebar.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent weightIntent = new Intent(HomeActivity.this, WeightGraphActivity.class);
        if(id == R.id.weight7days){
            weightIntent.putExtra("numDays", 7);
        }else if(id == R.id.weight30days){
            System.out.println("30");
            weightIntent.putExtra("numDays", 30);
        }else if(id == R.id.weight180days){
            System.out.println("180");
            weightIntent.putExtra("numDays", 180);
        }else if(id == R.id.weight365days){
            System.out.println("365");
            weightIntent.putExtra("numDays", 365);
        }
        HomeActivity.this.startActivity(weightIntent);

        return true;
    }

}
