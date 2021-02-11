package org.fourqwebs.a4qwebapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.fourqwebs.androidtv.R;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    public static WebView webView;
    private String url = "";
    LottieAnimationView lottieAnimationView;
    LinearLayout animation, main;
    String temoport;
    String tempurl;
    @SuppressLint({"SetJavaScriptEnabled", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent it = getIntent();
        tempurl = it.getStringExtra("url");
         temoport = it.getStringExtra("port");
        url = tempurl + ":" + temoport;
        webView = findViewById(R.id.web_view);

        animation = findViewById(R.id.animation_layout);
        main = findViewById(R.id.main_layout);
        animation.setVisibility(View.INVISIBLE);
        main.setVisibility(View.VISIBLE);
        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView.setVisibility(View.GONE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (savedInstanceState == null) {
            check_connection();
        } else {
            webView.restoreState(savedInstanceState);
        }
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url == null || url.startsWith("http://") || url.startsWith("https://"))
                    return false;
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (view.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/home.html")) {
                    MyDataBase myDataBase = new MyDataBase(MainActivity.this);
                    myDataBase.getWritableDatabase();
                    myDataBase.insert_data("in", tempurl, temoport);
                    //  Toast.makeText(MainActivity.this, "sign in table created"+webView.getUrl(), Toast.LENGTH_SHORT).show(); ;
                }
                if (view.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/login.html")) {
                    MyDataBase myDataBase = new MyDataBase(MainActivity.this);
                    myDataBase.getWritableDatabase();
                    myDataBase.getWritableDatabase();
                    myDataBase.deletedata();
                    Intent it = new Intent(MainActivity.this, MyConnect.class);
                    startActivity(it);
                    MainActivity.this.finish();
                    // Toast.makeText(MainActivity.this, "signout datadeleted", Toast.LENGTH_SHORT).show();
                }
//                if (view.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/home?serverId=03ab1ae5b1fa4571b163a256fc245de4")) {
//                    MyDataBase myDataBase = new MyDataBase(MainActivity.this);
//                    myDataBase.getWritableDatabase();
//                    myDataBase.insert_data("in", tempurl, temoport);
//                    //  Toast.makeText(MainActivity.this, "sign in table created"+webView.getUrl(), Toast.LENGTH_SHORT).show(); ;
//                }
//                if (view.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/startup/manuallogin.html?serverId=03ab1ae5b1fa4571b163a256fc245de4")) {
//                    MyDataBase myDataBase = new MyDataBase(MainActivity.this);
//                    myDataBase.getWritableDatabase();
//                    myDataBase.deletedata();
//                    Intent it = new Intent(MainActivity.this, MyConnect.class);
//                    startActivity(it);
//                    MainActivity.this.finish();
//                    // Toast.makeText(MainActivity.this, "signout datadeleted", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }

    public void check_connection() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean connected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if (connected) {
                    animation.setVisibility(View.VISIBLE);
                    main.setVisibility(View.INVISIBLE);
                    lottieAnimationView.playAnimation();
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                } else {
                    animation.setVisibility(View.INVISIBLE);
                    main.setVisibility(View.VISIBLE);
                    webView.loadUrl(url);
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override//?serverId=03ab1ae5b1fa4571b163a256fc245de4
    public void onBackPressed() {
        if (webView.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/login.html")) {
            final AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Are You Sure To Exit");
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    b.setCancelable(true);
                }
            }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            }).show();
        }
        if (webView.getUrl().equals("http://4qplay.org:"+temoport+"/web/index.html#!/home.html" )) {
            final AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Are You Sure To Exit");
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    b.setCancelable(true);
                }
            }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            }).show();
        } else if (webView.canGoBack()) {
            webView.goBack();
        }else{
            final AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("Are You Sure To Exit");
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    b.setCancelable(true);
                }
            }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            }).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }
}
