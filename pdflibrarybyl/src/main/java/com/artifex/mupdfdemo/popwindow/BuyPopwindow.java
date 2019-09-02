package com.artifex.mupdfdemo.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lrs.pdflibrarybyl.R;

public class BuyPopwindow extends PopupWindow {
    public static BuyPopwindow buypop;
    public static Activity ac;
    public static View headView;
    public static CheckBox checkbox_payPage_aliPay;
    public static CheckBox checkbox_payPage_weChat;
    public static int payType = 1; //0没有 1 支付宝 2 微信
    public static String comName;
    public static int Bookprice;
    public  static  onBookBuyListen onBookBuyListen;

    public BuyPopwindow(View view, int matchParent, int wrapContent, boolean b) {
        super(view, matchParent, wrapContent, b);
    }

    /**
     * 获取BuyPopwindow
     *
     * @param activity 当前的activity
     * @param view     辅助view
     * @return BuyPopwindow
     */
    public static BuyPopwindow getBuypop(Activity activity, View view, String name, int price, onBookBuyListen listen) {
        ac = activity;
        headView = view;
        comName = name;
        Bookprice = price;
        onBookBuyListen = listen;
        if (buypop == null) {
            initPopwindow();
        }
        return buypop;
    }
    /**
     * 初始化对象
     */
    public static void initPopwindow() {
        if (buypop == null) {
            View view = ac.getLayoutInflater().inflate(R.layout.buy_popwindow, null, false);
            //关闭点击事件
            view.findViewById(R.id.ivclose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissPop();
                }
            });
            TextView tvcomname = view.findViewById(R.id.tvcomname);
            tvcomname.setText(comName);
            TextView tvprice = view.findViewById(R.id.tvprice);
            tvprice.setText(Bookprice + "元");
            checkbox_payPage_aliPay = view.findViewById(R.id.checkbox_payPage_aliPay);
            checkbox_payPage_weChat = view.findViewById(R.id.checkbox_payPage_weChat);
            checkbox_payPage_aliPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payType = 1;
                    checkbox_payPage_weChat.setChecked(false);
                    checkbox_payPage_aliPay.setChecked(true);
                }
            });
            checkbox_payPage_weChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payType = 2;
                    checkbox_payPage_aliPay.setChecked(false);
                    checkbox_payPage_weChat.setChecked(true);
                }
            });

            Button btpay = view.findViewById(R.id.btpay);
            btpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (payType == 1) {
                        //支付宝支付
                        if(onBookBuyListen!=null) {
                            onBookBuyListen.onAliPay();
                        }
                    } else {
                        //微信支付
                        if(onBookBuyListen!=null) {
                            onBookBuyListen.oneChatWPay();
                        }
                    }
                }
            });

            buypop = new BuyPopwindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //关闭监听
            buypop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(ac, 1f);
                    buypop = null;
                    ac = null;
                }
            });
            backgroundAlpha(ac, 0.5f);

            //点击外部是否关闭
            buypop.setOutsideTouchable(true);
            buypop.setFocusable(true);
            // 设置PopupWindow是否能响应外部点击事件
            buypop.setOutsideTouchable(false);
            // 设置PopupWindow是否能响应点击事件
            buypop.setTouchable(true);
            buypop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //进入退出动画
            buypop.setAnimationStyle(R.style.anim_menu_bottombar);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    //显示
    public void showPop() {
        if (buypop != null) {
            if (buypop.isShowing()) {
                return;
            }
            buypop.showAtLocation(headView, Gravity.BOTTOM, 0, 0);
        }
    }

    //关闭
    public static void dismissPop() {
        if (buypop != null && buypop.isShowing()) {
            buypop.dismiss();
        }
    }
    //支付方式返回Activity
    public interface  onBookBuyListen{
        //支付宝支付
        void oneChatWPay();
        //微信支付
        void onAliPay();
    }
}
