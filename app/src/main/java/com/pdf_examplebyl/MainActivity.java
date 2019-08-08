package com.pdf_examplebyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.artifex.mupdfdemo.MuPDFActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public Button btpdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btpdf = findViewById(R.id.btpdf);

        btpdf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/SL 27-2014 水闸施工规范.pdf");
                if (file.exists()) {
                    openFile("pdf", file + "");
                }

            }
        });
    }
    public void openFile(String type, String filePath) {
            Uri uri = Uri.parse(filePath);
            Intent intent = new Intent(this, MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);

    }
}
