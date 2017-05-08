package th.co.smk.smkagent;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import layout.FragmentInsuranceProcess;
import layout.FragmentRemindRenew;


public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FragmentInsuranceProcess insuranceProcess = new FragmentInsuranceProcess();
    private FragmentRemindRenew remindRenew = new FragmentRemindRenew();
    //private FragmentManager fragmentManager = getSupportFragmentManager();
    private android.app.FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingActionBarDrawerLayoutAndButtomBar();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

        /*
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        */
    }

    private void settingActionBarDrawerLayoutAndButtomBar() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView nav_Bottom = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        nav_Bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        nav_Bottom.setSelectedItemId(R.id.tabbar_remind_renew);

        NavigationView nav_Drawer = (NavigationView) findViewById(R.id.navigation_drawer);
        nav_Drawer.setNavigationItemSelectedListener(this);
        //set auto selected
        nav_Drawer.setCheckedItem(R.id.tabbar_remind_renew);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            NavigationView nav_View = (NavigationView) findViewById(R.id.navigation_drawer);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.tabbar_insurance_process:
                    transaction.replace(R.id.content, remindRenew);
                    transaction.commit();
                    nav_View.setCheckedItem(R.id.tabbar_insurance_process);
                    item.setChecked(true);
                    break;
                case R.id.tabbar_remind_renew:
                    /*fragmentManager.beginTransaction().replace(R.id.content, remindRenew, remindRenew.getTag()).commit();*/
                    transaction.replace(R.id.content, insuranceProcess);
                    transaction.commit();
                    nav_View.setCheckedItem(R.id.tabbar_remind_renew);
                    item.setChecked(true);
                    break;
                case R.id.tabbar_commission_fee:
                    nav_View.setCheckedItem(R.id.tabbar_commission_fee);
                    item.setChecked(true);
                    break;
            }
            return false;
        }

    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        BottomNavigationView nav_Bottom = (BottomNavigationView) findViewById(R.id.navigation_bottom);

         switch (item.getItemId()) {
            case R.id.tabbar_remind_renew:
                nav_Bottom.setSelectedItemId(R.id.tabbar_remind_renew);
                break;
            case R.id.tabbar_insurance_process:
                nav_Bottom.setSelectedItemId(R.id.tabbar_insurance_process);
                break;
            case R.id.tabbar_commission_fee:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_navigation_drawer, menu);
        return true;
    }
}
