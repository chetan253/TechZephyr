package com.tzltce.techzephyr;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView backdrop;
    private Drawer result = null;
    private AccountHeader headerResult = null;
    private CollapsingToolbarLayout collapselayout;
    private FloatingActionButton fab;
    private AppBarLayout appbar;
    private FloatingActionMenu fabmenu;
    private FloatingActionButton fab1, fab2;
    private final static String APP_PACKAGE_NAME = "com.tzltce.techzephyr";
    boolean firstBackPressed = false;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    //location coordinates fpr locate fabutton
    private Double myLatitude = 19.1056879;
    private Double myLongitude = 73.0072627;
    private String labelLocation = "Lokmanya Tilak College of Engineering";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fab = (FloatingActionButton)findViewById(R.id.floating_action_button);
        collapselayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        collapselayout.setTitle("Tech Zephyr");
        appbar = (AppBarLayout)findViewById(R.id.appbar);
        transaction(new MainFragment());
        //Header Profile
        final IProfile profile = new ProfileDrawerItem().withName("Tech Zephyr 2016").withEmail("techzephyrltce@gmail.com").withIcon(R.mipmap.ic_tzlauncher);
        //Floating action menu
        fabmenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab1 =(FloatingActionButton)findViewById(R.id.fabbtncrew);
        fab1.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_people).color(Color.WHITE));
        fab2 =(FloatingActionButton)findViewById(R.id.fabbtnlocateus);
        fab2.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_gps_fixed).color(Color.WHITE));
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrewFragment crew_frag = new CrewFragment();
                transaction(crew_frag);
                fabmenu.close(true);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + myLatitude + ">,<" + myLongitude + ">?q=<" + myLatitude + ">,<" + myLongitude + ">(" + labelLocation + ")"));
                startActivity(intent);
                fabmenu.close(true);
            }
        });
        backdrop = (ImageView)findViewById(R.id.backdrop);
        backdrop.setImageResource(R.drawable.techlogo);
        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                }

            }
        });
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSelectionListEnabled(false)
                .withAlternativeProfileHeaderSwitching(false)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        //crossfader drawer(not for user as it may degrade UX)
        crossfadeDrawerLayout = new CrossfadeDrawerLayout(this);
        //setting up drawerlayout
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                //.withDrawerLayout(crossfadeDrawerLayout)//dev
                //.withHasStableIds(true)//dev
                //.withDrawerWidthDp(72)//dev
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withHeader(R.layout.nav_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Tech Zephyr").withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Events").withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName("Workshop").withIcon(GoogleMaterial.Icon.gmd_extension),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Registeration").withIcon(GoogleMaterial.Icon.gmd_create),
                        new PrimaryDrawerItem().withName("Social").withIcon(FontAwesome.Icon.faw_star)
                        //new PrimaryDrawerItem().withName("Show Entries").withIcon(GoogleMaterial.Icon.gmd_description)//Admin preview only
                )
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withName("About").withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                MainFragment main_frag = new MainFragment();
                                appbar.setExpanded(true);
                                fabmenu.setVisibility(View.VISIBLE);
                                collapselayout.setTitle("Tech Zephyr");
                                transaction(main_frag);
                                return false;
                            case 2:
                                EventsFragment event_frag = new EventsFragment();
                                appbar.setExpanded(false);
                                fabmenu.setVisibility(View.GONE);
                                collapselayout.setTitle("Events");
                                transaction(event_frag);
                                return false;
                            case 3:
                                WorkshopFragment workshop_frag = new WorkshopFragment();
                                fabmenu.setVisibility(View.GONE);
                                appbar.setExpanded(false);
                                collapselayout.setTitle("Workshops");
                                transaction(workshop_frag);
                                return false;
                            case 5:
                                Intent ireg = new Intent(MainActivity.this, RegisterActivity.class);
                                startActivity(ireg);
                                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                                return false;
                            case 6:
                                new BottomSheet.Builder(MainActivity.this).title("Social").sheet(R.menu.menu_social).listener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case R.id.facebook:
                                                appbar.setExpanded(false);
                                                fabmenu.setVisibility(View.GONE);
                                                Uri urifb = Uri.parse("fb://facewebmodal/f?href=https://m.facebook.com/Techzephyr/");
                                                Intent likefb = new Intent(Intent.ACTION_VIEW, urifb);
                                                likefb.setPackage("com.facebook.katana");
                                                try {
                                                    startActivity(likefb);
                                                } catch (ActivityNotFoundException e) {
                                                    Bundle bundlefb = new Bundle();//facebook liking
                                                    bundlefb.putString("url", "https://m.facebook.com/Techzephyr/");
                                                    WebviewFragment fbfragment = new WebviewFragment();
                                                    fbfragment.setArguments(bundlefb);
                                                    transaction(fbfragment);
                                                }
                                                break;
                                            case R.id.instagram:
                                                appbar.setExpanded(false);
                                                fabmenu.setVisibility(View.GONE);
                                                Uri uri = Uri.parse("http://instagram.com/_u/techzephyr2k16");
                                                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                                likeIng.setPackage("com.instagram.android");
                                                try {
                                                    startActivity(likeIng);
                                                } catch (ActivityNotFoundException e) {
                                                    Bundle bundleinsta = new Bundle();//Insta following
                                                    bundleinsta.putString("url", "https://www.instagram.com/techzephyr2k16/");
                                                    WebviewFragment instafragment = new WebviewFragment();
                                                    instafragment.setArguments(bundleinsta);
                                                    transaction(instafragment);
                                                }
                                                break;
                                            case R.id.twitter:
                                                appbar.setExpanded(false);
                                                fabmenu.setVisibility(View.GONE);
                                                Bundle bundletwitter = new Bundle();//twitter following
                                                bundletwitter.putString("url", "https://twitter.com/techzephyr2k16/");
                                                WebviewFragment twitterfragment = new WebviewFragment();
                                                twitterfragment.setArguments(bundletwitter);
                                                transaction(twitterfragment);
                                                break;
                                            case R.id.playstore:
                                                appbar.setExpanded(false);
                                                fabmenu.setVisibility(View.GONE);
                                                try {
                                                    MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW,
                                                            Uri.parse("market://details?id=" + APP_PACKAGE_NAME)));
                                                } catch (ActivityNotFoundException e) {
                                                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
                                                }

                                                break;
                                        }
                                    }
                                }).show();
                                return false;
                            default:
                                Intent inabt = new Intent(MainActivity.this, AboutActivity.class);
                                startActivity(inabt);
                                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                                return false;
                        }
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        RateItDialogFragment.show(this, getFragmentManager());
        result.getRecyclerView().setVerticalScrollBarEnabled(false);
    }

    private void transaction(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        mFragmentTransaction.replace(R.id.frag_container, fragment);
        mFragmentTransaction.commit();
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

    @Override
    public void onBackPressed() {
        if (!firstBackPressed) {
            firstBackPressed = true;
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
