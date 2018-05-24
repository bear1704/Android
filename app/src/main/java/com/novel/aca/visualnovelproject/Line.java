package com.novel.aca.visualnovelproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aca on 2016-10-15.
 */

public class Line implements Serializable {


    public static final int NONE = 0;   //0번 이미지. 즉 없는 이미지(투명 이미지로 대체하기)

    private String name;
    private String text;
    private boolean vibrate;
    private boolean scrvib;
    private int textColor = NONE;
    private int leftImage = NONE;
    private int rightImage = NONE;
    private int centerImage = NONE;
    private int backgroundImage = NONE;
    private int sound = NONE;
    private int bgm = NONE;
   // private int whatImage;
    private boolean fade_start;
    private boolean fade_end;

    private boolean isGoto = false;  //Goto가 활성화 된 line인지?
    private boolean isSelect = false;
    private int tempKeynum = NONE;
    private int tempStagenum = NONE;

    private String[] selectChoice;
    private int[] selectStage;
    private int[] selectKeynum;




    public void setName(String name){
        this.name = name;
    }
    public void setText(String text){
        this.text = text;


    }
    public String getText(){ return text;}
    public String getName(){return name;}



    public void setTextColor(String color,Activity activity){

        if(color.equals("red")){
            textColor = ContextCompat.getColor(activity,R.color.red);
        }
        else if(color.equals("white")){
            textColor = ContextCompat.getColor(activity,R.color.default_color);
        }
        else{
            textColor = ContextCompat.getColor(activity,R.color.default_color);
        }


    }
    public int getTextColor(Context activity){
       if(textColor!=0){
        return textColor;}
        else{
           return ContextCompat.getColor(activity,R.color.black);}

    }

    public void setVibrate(boolean vibrate){
        this.vibrate = vibrate;

    }
    public boolean getVibrate(){
        return vibrate;
    }

    public void setScrvib(boolean scrvib){
        this.scrvib = scrvib;

    }

    public void setLeftImage(String leftImage){

        this.leftImage = Integer.parseInt(leftImage);

    }
    public void setRightImage(String rightImage){
        this.rightImage = Integer.parseInt(rightImage);
    }
    public void setCenterImage(String centerImage){
        this.centerImage = Integer.parseInt(centerImage);
    }
    public void setBackgroundImage(String backgroundImage){
        this.backgroundImage = Integer.parseInt(backgroundImage);
    }

    public int getLeftImage(){
        if(this.leftImage != NONE){
           return this.leftImage;
        }
        else{
            return NONE;
        }
    }
    public int getRightImage(){
        if(this.rightImage != NONE){
            return this.rightImage;
        }
        else{
            return NONE;
        }

    }
    public int getCenterImage(){
        if(this.centerImage != NONE){
            return this.centerImage;
        }
        else{
            return NONE;
        }

    }
    public int getBackgroundImage(){
        if(this.backgroundImage != NONE){
            return this.backgroundImage;
        }
        else{
            return NONE;
        }
    }


    public void setFade_start(boolean fade_start){
        this.fade_start = fade_start;
    }
    public void setFade_end(boolean fade_end){
        this.fade_end = fade_end;
    }

    public void setSound(int sound){
        this.sound = sound;
    }
    public void setBgm(int bgm){ this.bgm = bgm; }

    public void setGoto(String[] array){
           tempStagenum = Integer.parseInt(array[0]);
           tempKeynum = Integer.parseInt(array[1]);
           isGoto = true;
    }
    public void setSelect(String[] array){

        selectChoice = new String[3];
        selectStage = new int[3];
        selectKeynum = new int[3];

        selectChoice[0] = array[0];
        selectChoice[1] = array[3];
        selectChoice[2] = array[6];

        selectStage[0] = Integer.parseInt(array[1]);
        selectStage[1] = Integer.parseInt(array[4]);
        selectStage[2] = Integer.parseInt(array[7]);

        selectKeynum[0] = Integer.parseInt(array[2]);
        selectKeynum[1] = Integer.parseInt(array[5]);
        selectKeynum[2] = Integer.parseInt(array[8]);

        isSelect = true;



    }

    public int[] getGoto(){
        int[] num = {tempStagenum,tempKeynum};
         return num;
    }
    public boolean isGotoEnable(){
        if(isGoto == true){
            return true;
        }else{
            return false;
        }
    }

    public String[] getSelectChoice(){
        return selectChoice;
    }
    public int[] getSelectStage(){
        return selectStage;
    }
    public int[] getSelectKeynum(){
        return selectKeynum;
    }

    public boolean isSelectEnable(){
        if(isSelect == true){
            return true;
        }else{
            return false;
        }
    }


    public int getSound(){
        if(sound != NONE)
        {
        return sound;
        }else{
            return NONE;
        }
    }
    public int getBgm(){
        if(bgm != NONE){
            return bgm;
        }else{
            return NONE;
        }
    }



    @Override
    public String toString(){
        return " name = " + name + " " + " text = " + text + " vibrate = " + vibrate +
                " 스크린진동상태 = " + scrvib +   " textColor = " + textColor;
    }


}
