//Vollyの通信クラスです。毎回このまま使います。
//packageはこのクラスの階層を示します。このクラスの場合はcom.gashfara.mogi.gsapp.VolleyApplicationになります。
package com.gashfara.mogi.gsapp;
//importはすでに用意されているAndroidSDKのクラスなどを取り込んで使用するための宣言です。
//プログラムでpackage以外のクラスを使用するとエラーになるのでそのクラスごとに必要に応じて追加すればOKです。
import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.kii.cloud.analytics.KiiAnalytics;
import com.kii.cloud.storage.Kii;

import java.util.HashMap;


//Applicationクラスを継承extend（コピーみたいなもの）しています。Applicationの機能がそのまま使えます。{}までがクラスです。
//これはクラスの定義です。このクラスを使うにはnewなどをしてインスタンス化（実態を作る）してから使います。
//VolleyApplicationが新しいクラス名になります。
//publicが付いていると他のクラスから使えます。privateだと使えません。
public class VolleyApplication extends Application {
    //クラスはこのようにintのような型として使えます。
    private static VolleyApplication sInstance;
    private RequestQueue mRequestQueue;

    //GrowthHackで追加ここから
    //トラッキングIDを設定
    private static final String PROPERTY_ID = "UA-65942603-1";
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
    }
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    //参考サイトのまま：http://qiita.com/chonbo2525/items/bbc55d728f8e1b8dca39
    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            t.enableAdvertisingIdCollection(true);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    //GrowthHackで追加ここまで

    //overrideは継承元のクラスApplicationの機能を引き継ぐのではなく上書きすることを宣言しています。
    //onCreateはアプリを起動した時にOSから呼び出される関数です。よく使います。
    @Override
    public void onCreate() {
        //superは親クラスです。ですのでApplicationです。このonCreate()関数を実行しています。overrideしていますが、親のonCreate()も実行しているわけです。
        //クラス内の関数（メソッド）はクラス名.関数名()というかんじに記載します。
        super.onCreate();
        //Volleyの通信用のクラスです。これを使って通信を行います。
        mRequestQueue = Volley.newRequestQueue(this);
        //自分自身のインスタンス（newなどでクラスを実体化したもの）を代入しています。
        sInstance = this;
        //Userで追加ここから
        //KiiCloudの初期化。Applicationクラスで実行してください。キーは自分の値にかえる。
        Kii.initialize(getApplicationContext(), "80dd6a22", "f74778c9d5280992913b93ed39ec0f5a", Kii.Site.JP,true);//GrowthHack(ABテスト)修正。trueを追加してKiiAnalyticsを有効にする。
        //Userで追加ここまで

    }
    //インスタンスを返す関数（メソッドです）。クラスの中にある変数はこのように関数を通じて返すようにするのが一般的です。
    //synchronizedは同時に動作すると不具合が起きるときに宣言します。Volleyの仕様です。
    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    //通信クラスを返す関数
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }



}
