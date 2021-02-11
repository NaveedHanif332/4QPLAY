package org.fourqwebs.a4qwebapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.fourqwebs.androidtv.R;

public class MyConnect extends AppCompatActivity {
    private AutoCompleteTextView url, port;
    Button btn_connect,btn_cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_connect);
        intializeGUI();

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_url=url.getText().toString().trim();
                String temp_port=port.getText().toString().trim();
                if(validate(temp_port,temp_url))
                {
                    //measn not empty
                    if(temp_url.contains("https"))
                    {
                        url.setError("Enter Only http");
                    }
                    else {
                        Intent it=new Intent(MyConnect.this,MainActivity.class);
                        it.putExtra("url",temp_url);
                        it.putExtra("port",temp_port);
                        startActivity(it);
                    }

                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url.setText("");
                port.setText("");
            }
        });
    }

    private boolean validate(String temp_port, String temp_url) {
        if(temp_url.isEmpty())
        {
            url.setError("Url is empty");
            return false;
        }
        if(temp_port.isEmpty())
        {
            port.setError("Port is empty");
            return false;
        }

        return true;
    }

    private void intializeGUI() {
        url=findViewById(R.id.url);
        port=findViewById(R.id.port);
        btn_cancle=findViewById(R.id.btnCancel);
        btn_connect=findViewById(R.id.btnConnect);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}