package com.artifex.mupdfdemo.NetWorkPDF;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lrs.pdflibrarybyl.R;

import java.io.File;

public class NetDowActivity extends Activity {
    //请求地址
    private String path;
    //文件名称
    private String fileName;
    //本机地址
    private static String LoPath = "";
    //缓存目录
    private static String cachePath = "";
    //PDF文件 保存地址
    private static String PDFPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_dow);
        Intent intent = getIntent();
        LoPath = intent.getStringExtra("LoPath");
        cachePath = LoPath + "/.DwnloadFile";
        PDFPath = cachePath + "/.PDF";
        path = intent.getStringExtra("path");
        FileDownloader.setup(this);   //ac为activity的上下文对象
        initData();
    }

    /*
        初始化
     */
    private void initData() {
        //初始化 文件目录
        File pdffile = new File(PDFPath);
        if (!pdffile.exists()) {
            pdffile.mkdirs();
        }
        //获取文件名称
        fileName = getFileName(path);
        if (!getExistsOfPath(PDFPath, fileName)) {
            //下载文件
            dowFile(path, PDFPath + "/" + fileName);
        } else {
            File file = new File(PDFPath + "/" + fileName);
            if (file.exists()) {
                openFile(file + "");
            }
        }

    }

    public static String getFileName(String Path) {
        String fileName = null;
        if (Path != null && Path.length() > 0) {
            fileName = Path.substring(Path.lastIndexOf("/") + 1);
        }
        return fileName;
//        return "test1.pdf";
    }

    public static boolean getIsExists(String path) {
        boolean isexists = false;
        String fileName = getFileName(path);
        if (fileName != null && fileName.length() > 0) {
            isexists = getExistsOfPath(PDFPath, path);
        }
        return isexists;
    }

    /*
        指定文件夹是否存在指定文件
     */
    public static boolean getExistsOfPath(String PDFPath, String name) {
        boolean isex = false;
        File file = new File(PDFPath);
        File[] files = file.listFiles();
        if (files != null && file.length() > 0) {
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isDirectory()) {
                    String fileName = files[i].getName();
                    if (fileName != null && fileName.length() > 0 && fileName.equals(name)) {
                        isex = true;
                        break;
                    }
                }
            }
        }
        return isex;
    }

    /*
    清空文件
     */
    public static void clearPDFDir() {
        File file = new File(PDFPath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                file1.delete();
            }
        }
    }

    /*
    跳转阅读
     */
    public void openFile(String filePath) {
        Uri uri = Uri.parse(filePath);
        Intent intent = new Intent(this, MuPDFActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
        this.finish();

    }


    private void dowFile(String url, final String fileUrl) {
        FileDownloader.getImpl().create(url)
                .setPath(fileUrl)
                .setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    //等待
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        showProgressDialog(NetDowActivity.this, "加载中");
                    }

                    //下载进度回调
                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    //完成下载
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgressDialog();
                        openFile(fileUrl);
                    }

                    //暂停
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    //下载出错
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Toast.makeText(NetDowActivity.this, "加载失败,请重试", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                        NetDowActivity.this.finish();
                    }

                    //已存在相同下载
                    @Override
                    protected void warn(BaseDownloadTask task) {
                        openFile(fileUrl);
                    }
                }).start();
    }

    //加载框变量
    private ProgressDialog progressDialog;

    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);    //设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();

        //设置超时自动消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //取消加载框
                if (dismissProgressDialog()) {
                    //超时处理
                }
            }
        }, 60000);//超时时间60秒
    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;
    }
}
