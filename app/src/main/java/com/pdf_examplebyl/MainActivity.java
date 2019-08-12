package com.pdf_examplebyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.artifex.mupdfdemo.NetWorkPDF.NetDowActivity;

public class MainActivity extends AppCompatActivity {
    public Button btpdf;
    public Button btclear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btpdf = findViewById(R.id.btpdf);
        btclear = findViewById(R.id.btclear);

        btpdf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/SL 27-2014 水闸施工规范.pdf");
//                if (file.exists()) {
//                    openFile("pdf", file + "");
//                }
                Intent intent = new Intent(MainActivity.this, NetDowActivity.class);
                String url ="https://file3.data.weipan.cn/1231300/9c7a47a4d973f86702f41a608d92bdbd3d69eb3c?ip=1565583179,120.244.92.201&ssig=NLzhomRfUn&Expires=1565583779&KID=sae,l30zoo1wmz&fn=%E7%AE%80%E5%8E%86%E4%B8%8E%E6%B1%82%E8%81%8C%E4%BF%A12015%E6%A0%A1%E5%9B%AD%E6%8B%9B%E8%81%98%E6%B1%82%E8%81%8C%E5%A4%A7%E7%A4%BC%E5%8C%85.pdf&se_ip_debug=120.244.92.201&from=1221134";
                intent.putExtra("path",url);
                intent.putExtra("LoPath",getApplicationContext().getExternalFilesDir(null).toString());
                startActivity(intent);

            }
        });
        btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetDowActivity.clearPDFDir();
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
