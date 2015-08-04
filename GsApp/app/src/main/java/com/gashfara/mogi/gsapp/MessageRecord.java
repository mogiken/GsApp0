//1つのセルにあるデータを保存するためのデータクラスです。
package com.gashfara.mogi.gsapp;

//ListView2ではデータクラスにidを追加してください。
public class MessageRecord {
    //保存するデータ全てを変数で定義します。
    private String imageUrl;
    private String comment;
    private String id;

    //データを１つ作成する関数です。項目が増えたら増やしましょう。
    public MessageRecord(String id,String imageUrl, String comment) {
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.id = id;
    }
    //それぞれの項目を返す関数です。項目が増えたら増やしましょう。
    public String getComment() {return comment;}
    public String getImageUrl() {return imageUrl;}
    public String getId() {
        return id;
    }
}
