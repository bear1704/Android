package com.novel.aca.visualnovelproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by aca on 2016-12-28.
 */

public class SaveData implements Serializable{

    private static final long serialVersionUID = 100000111L;

    private int keyNum;
    private int stageNum;
    transient private Bitmap screenShot; //세이브상황에서의 스크린샷 한장
    private String saveOrder; //세이브 슬롯 넘버
    private String saveWord; // 세이브 상황 시의 진행중이던 대사
    private byte[] encodeScreenShot = null;



    public void setKeyNum(int keyNum){

        this.keyNum = keyNum - 1;  //-1을 하는 이유 : keynum은 0에서 클릭한번할때 1이 증가한다. -1을 하지않으면 한번증가한 다음값을 보여주기도 하고 마지막 문장에선 튕긴다.

    }
    public void setStageNum(int stageNum){

        this.stageNum = stageNum;
    }

    public void setScreenShot(Bitmap screenShot) {this.screenShot = screenShot;}
    public void setSaveOrder(String saveOrder){
        this.saveOrder = saveOrder;
    }
    public void setSaveWord(String saveWord){
        this.saveWord = saveWord;
    }
    public void setEncodeScreenShot(byte[] encodeScreenShot){this.encodeScreenShot = encodeScreenShot;}


    public Bitmap getScreenShot(){
        return screenShot;
    }

    public byte[] getEncodeScreenShot_origin() { return encodeScreenShot;}
    public Bitmap getEncodeScreenShot(){return BitmapFactory.decodeByteArray(encodeScreenShot,0,encodeScreenShot.length);}


    public String getSaveOrder(){
        return saveOrder;
    }
    public String getSaveWord(){
        return saveWord;
    }


    public int getKeyNum(){
        return keyNum;
    }
    public int getStageNum(){
        return stageNum;
    }


}
