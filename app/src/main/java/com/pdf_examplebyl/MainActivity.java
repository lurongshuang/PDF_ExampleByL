package com.pdf_examplebyl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.artifex.mupdfdemo.MuPDFActivity;
import com.artifex.mupdfdemo.NetWorkPDF.NetDowActivity;
import com.pdf_examplebyl.pay.PayResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MuPDFActivity.onBookBuyListen {
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
                String url ="http://dl140.zlibcdn.com/download/book/2028677?token=7c19d4c9d6949c0f73e25d150a92e1b2";
                Bundle bundle = new Bundle();
                bundle.putString("path",url); //下载地址
                bundle.putString("name","test.pdf"); //保存本地文件名称 可不填 默认获取 path 连接地址 最后一个点到最后为名称（.fileName） 如 http:// www.XX/fileName.pdf 获取到fileName.pdf
                bundle.putString("LoPath",getApplicationContext().getExternalFilesDir(null).toString()); //保存本地地址
                bundle.putBoolean("openCharge",true);//图书是否收费
                bundle.putInt("experiencePage",15);//体验页数
                bundle.putBoolean("menu",true);//是否先跳转到目录
                intent.putExtra("data",bundle);
                startActivity(intent);

            }
        });
        btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetDowActivity.clearPDFDir();
            }
        });
        //支付监听
        MuPDFActivity.setBookBuyListen(this);
    }
    public void openFile(String type, String filePath) {
            Uri uri = Uri.parse(filePath);
            Intent intent = new Intent(this, MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);

    }

    @Override
    public void oneChatWPay() {
        Toast.makeText(this,"微信支付成功",Toast.LENGTH_SHORT).show();
        MuPDFActivity.onPaySuccess(2);
    }

    @Override
    public void onAliPay() {
        Toast.makeText(this,"支付宝支付成功",Toast.LENGTH_SHORT).show();
        MuPDFActivity.onPaySuccess(1);
//        alipay();
    }
    private Handler mHandler;
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            try {
                                JSONObject jsonObject = new JSONObject(resultInfo);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("alipay_trade_app_pay_response");
//                                SavePayData.getInstance().saveData(MainActivity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MuPDFActivity.onPaySuccess(1);
                        } else {
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(MainActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                            } else if (TextUtils.equals(resultStatus, "6001")) {
                                Toast.makeText(MainActivity.this, "用户取消支付", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }
    private static final int SDK_PAY_FLAG = 1;
    private void alipay() {
        initHandler();
        final String orderInfo = "";
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MainActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }
}
