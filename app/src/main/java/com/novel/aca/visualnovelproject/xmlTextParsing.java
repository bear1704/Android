package com.novel.aca.visualnovelproject;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aca on 2016-10-15.
 */

public class xmlTextParsing {

    public static final int NEW_START = 0;
    public static final int LOAD_GAME = 1;


    public Map getInfoFromXml(Activity activity,int stageNum) throws XmlPullParserException, IOException{

        Resources res = activity.getResources();
        XmlResourceParser xpp;



        switch(stageNum) {
            case 50:
                xpp = res.getXml(R.xml.scene1);//이건 나중에 스테이지 고르는 메소드 따로정의
                break;
            case 51:
                xpp = res.getXml(R.xml.scene2);
                break;
            default:
                xpp = res.getXml(R.xml.scene1);

        }



        xpp.next();
        int eventType = xpp.getEventType();
        String textName = null;
        Map<Integer, Line> map = new HashMap<Integer, Line>();
        int count = 0;
        Line line = new Line();

        while(eventType != XmlPullParser.END_DOCUMENT){

            switch(eventType){
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equals("name")){
                        textName = "NAME";
                    }
                    else if(xpp.getName().equals("text")){
                        textName = "TEXT";
                    }
                    else if(xpp.getName().equals("vibrate")){
                        textName = "VIBRATE";
                    }
                    else if(xpp.getName().equals("scrvib")){
                        textName = "SCRVIB";
                    }
                    else if(xpp.getName().equals("textcolor")){
                        textName = "TEXTCOLOR";
                    }
                    else if(xpp.getName().equals("leftimage")){
                        textName = "LEFTIMAGE";
                    }
                    else if(xpp.getName().equals("rightimage")){
                        textName = "RIGHTIMAGE";
                    }
                    else if(xpp.getName().equals("centerimage")){
                        textName = "CENTERIMAGE";
                    }
                    else if(xpp.getName().equals("fade_start")){
                        textName = "FADE_START";
                    }
                    else if(xpp.getName().equals("fade_end")){
                        textName = "FADE_END";
                    }
                    else if(xpp.getName().equals("sound")) {
                        textName = "SOUND";
                    }
                    else if(xpp.getName().equals("bgm")){
                        textName = "BGM";
                    }
                    else if(xpp.getName().equals("background")){
                        textName = "BACKGROUND";
                    }
                    else if(xpp.getName().equals("goto")){
                        textName = "GOTO";
                    }else if(xpp.getName().equals("select")){
                        textName = "SELECT";
                    }
                    break; // START_TAG 끝!
                case XmlPullParser.TEXT:
                    if(textName.equals("NAME")){
                        line.setName(xpp.getText());
                    } else if(textName.equals("TEXT")){
                        line.setText(xpp.getText());
                    }else if(textName.equals("VIBRATE")){
                        line.setVibrate(true);
                    }else if(textName.equals("SCRVIB")){
                        line.setScrvib(true);
                    }else if(textName.equals("TEXTCOLOR")){
                        line.setTextColor((xpp.getText()),activity);
                    }else if(textName.equals("LEFTIMAGE")){
                        line.setLeftImage(xpp.getText());
                    }else if(textName.equals("RIGHTIMAGE")){
                        line.setRightImage(xpp.getText());
                    }else if(textName.equals("CENTERIMAGE")){
                        line.setCenterImage(xpp.getText());
                    }else if(textName.equals("FADE_START")){
                        line.setFade_start(true);
                    }else if(textName.equals("FADE_END")){
                        line.setFade_end(true);
                    }else if(textName.equals("SOUND")){
                        line.setSound(Integer.parseInt(xpp.getText()));
                    }else if(textName.equals("BGM")){
                        line.setBgm(Integer.parseInt(xpp.getText()));
                    }
                    else if(textName.equals("BACKGROUND")){
                        line.setBackgroundImage(xpp.getText());
                    }
                    else if(textName.equals("GOTO")){
                         String[] array = xpp.getText().split(",");
                         line.setGoto(array);
                    }
                    else if(textName.equals("SELECT")){
                        String[] selectArray = xpp.getText().split(",");
                        line.setSelect(selectArray);
                    }
                    break; // TEXT(중간)부분 끝

                case XmlPullParser.END_TAG:
                    if(xpp.getName().equals("scene")){
                        map.put(count, line);
                        line = new Line();
                        count++;
                    }
                    break;
            }
            eventType = xpp.nextToken();
        }


       return map;
    }



}
