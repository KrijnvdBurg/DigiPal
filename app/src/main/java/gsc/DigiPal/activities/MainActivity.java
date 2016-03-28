package gsc.DigiPal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import gsc.DigiPal.R;
import gsc.DigiPal.activities.fragments.editProfile;
import gsc.DigiPal.activities.fragments.viewProfile;
import gsc.DigiPal.app.globalUser;
import gsc.DigiPal.helper.SQLiteHandler;
import gsc.DigiPal.helper.SessionManager;


public class MainActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public globalUser globalUser;
    public Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalUser = new globalUser();
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) { logoutUser(); }
        HashMap<String, String> user = db.getUserDetails();

        globalUser.setLocalUser_UUID(user.get("id"));
        globalUser.setLocalUser_username(user.get("username"));
        globalUser.setLocalUser_firstName(user.get("firstName"));
        globalUser.setLocalUser_lastName(user.get("lastName"));

       // String localUser_UUID = user.get("id");
        createDrawer();
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void createDrawer(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                } else{ menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (menuItem.getItemId()){
                    case R.id.find_DigiPal:
                        Toast.makeText(getApplicationContext(), "find DigiPal Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.profile:
                        //Toast.makeText(getApplicationContext(),"profile Selected",Toast.LENGTH_SHORT).show();
                        //drawerLayout.setDrawerTitle(Gravity.LEFT, "[[full name]]"); //doesnt work
                        fragment = new viewProfile();
                        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                        return true;
                    case R.id.sent_mail:
                        fragment = new editProfile();
                        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                        Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
}
