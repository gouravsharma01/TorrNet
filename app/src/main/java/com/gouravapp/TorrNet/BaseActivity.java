package com.gouravapp.TorrNet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gouravapp.TorrNet.Browser.BrowserActivity;

import static com.gouravapp.TorrNet.Browser.BrowserActivity.urlIntent;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.website;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private DrawerLayout drawerLayout;


    public BaseActivity(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.settings:
                context.startActivity(new Intent(context, SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.shareAction:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout This awesome app for Downloading Torrent easily " + "\n \n \n" +
                        "https://drive.google.com/uc?authuser=0&id=1VhYyfLQ627fb79oYceXp1lNwYUjehIQB&export=download");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case R.id.contact:

                break;
            case R.id.torrnet:
                context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.torrentClient:
                Intent intent1 = new Intent(Intent.ACTION_SEARCH);
                intent1.putExtra(SearchManager.QUERY, "Torrent Client");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.vending");
                context.startActivity(intent1);
                break;
            case R.id.tpb:
                urlIntent = "https://m.thepiratebay.org";
                website = "ThePirateBay";
                context.startActivity(new Intent(context, BrowserActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.limetor:
                urlIntent = "https://Limetorrents.info";
                website = "Limetorrents";
                context.startActivity(new Intent(context, BrowserActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.torrenz:
                urlIntent = "https://torrentz2.eu";
                website = "Torrentz";
                context.startActivity(new Intent(context, BrowserActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
