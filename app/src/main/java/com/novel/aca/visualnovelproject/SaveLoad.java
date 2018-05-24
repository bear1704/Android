package com.novel.aca.visualnovelproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by aca on 2016-12-12.
 */

public class SaveLoad extends Activity implements Serializable{



    private static final long serialVersionUID = 100000113L;


    public static int STAGE1 = 50;
    public static int STAGE2 = 51;
    public static int STAGE3 = 52;
    public static int STAGE4 = 53;


    int keyNum;
    int stageNum;
    ArrayList<SaveData>     saveData = null;
    ListView    mListView = null;
    BaseAdapterEx   mAdapter = null;
    private int itemClicked = 0;








    public SaveLoad(){

    }

    public SaveLoad(int keyNum,int stageNum){
        this.keyNum = keyNum;
        this.stageNum = stageNum;

    }

    public void refresh(){
        mAdapter = new BaseAdapterEx(this,saveData);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.save_load_slot);
            Intent dataIntent = this.getIntent();
            final int keyNum = dataIntent.getIntExtra("keynum",5000);
            final int stageNum = dataIntent.getIntExtra("stagenum",9000);
            final String words = dataIntent.getStringExtra("words"); //setSaveWord적용때 final 붙이래서..
            final byte[] encodeScreenShot = dataIntent.getByteArrayExtra("screenshot");


            Resources res = getResources();
            BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.image01);
            Bitmap bit = bd.getBitmap(); // 초기 설정 세이브상황 이미지

            Button saveBtn = (Button) findViewById(R.id.saveButton);
            Button loadBtn = (Button) findViewById(R.id.loadButton);

            if(dataIntent.getStringExtra("sp").equals("main")){
                saveBtn.setEnabled(false);  // 메인화면에서 로드창 띄울시 save를 비활성화하는 기능

            }



            saveData = new ArrayList<SaveData>();
            //Arraylist(세이브데이터) 초기 설정(로드정보 불러오기 전의 값)

            for(int i = 0 ; i< 20 ; i++){
                SaveData temp_savedata = new SaveData();

            temp_savedata.setSaveOrder("Savedata "+ i + " ");
            temp_savedata.setScreenShot(bit);
            temp_savedata.setSaveWord("No SaveData");

            saveData.add(temp_savedata);
        }
        /////////////////////////



                mAdapter = new BaseAdapterEx(this,saveData);

                mListView = (ListView) findViewById(R.id.listview);
                mListView.setAdapter(mAdapter);


        /* 초기설정 이후 로드설정 */

        File isFileExist = new File("/sdcard/savefile.sav"); //세이브파일이 존재할때만 자동로드

        if(isFileExist.isFile()){

                saveData = (ArrayList<SaveData>) unSerialize();
                refresh();
        }


                //로드데이터를 이곳에서 refresh()한다.
                //로드데이터를 불러오고 표현하는걸 만들어야함


                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Calendar c = Calendar.getInstance();  //현재날짜



                        String date = c.get(Calendar.YEAR) + "/" + ((c.get(Calendar.MONTH))+1) +"/" + c.get(Calendar.DATE) + " 저장됨 "; //시분초 추가해야됨

                SaveData new_saveData = new SaveData();

                new_saveData.setSaveOrder("Save " + itemClicked + "  \n"+ date);
                new_saveData.setSaveWord(words);
                new_saveData.setKeyNum(keyNum);
                new_saveData.setStageNum(stageNum);
                new_saveData.setEncodeScreenShot(encodeScreenShot);

                saveData.remove(itemClicked);
                saveData.add(itemClicked,new_saveData);


                ///본격직렬화 저장///

                String filePath = "/sdcard/savefile.sav";

                try{
                    FileOutputStream fos = new FileOutputStream(filePath);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ObjectOutputStream oos = new ObjectOutputStream(bos);

                    oos.writeObject(saveData);
                    oos.close();



                }catch(IOException e){
                    e.printStackTrace();

                }

               Log.v("직렬화","정상완료");
                refresh();


            }


        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toGameScreen = new Intent(getApplicationContext(),GameScreen.class);
                toGameScreen.putExtra("stage",saveData.get(itemClicked).getStageNum());
                toGameScreen.putExtra("keynum",saveData.get(itemClicked).getKeyNum());
                startActivity(toGameScreen);

                finish();


            }
        });






        ////////*/////////////////// 아이템 클릭유무 확인 //////////////////////////////

       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           @Override
           public void onItemClick(AdapterView<?> adapterView,View view,int position,long id){

               switch(position){
                   case 0:
                        view.setSelected(true);
                       itemClicked = 0;
                        break;
                   case 1:
                       view.setSelected(true);
                       itemClicked = 1;
                       break;
                   case 2:
                       view.setSelected(true);
                       itemClicked = 2;
                       break;
                   case 3:
                       view.setSelected(true);
                       itemClicked = 3;
                       break;
                   case 4:
                       view.setSelected(true);
                       itemClicked = 4;
                       break;
                   case 5:
                       view.setSelected(true);
                       itemClicked = 5;
                       break;
                   case 6:
                       view.setSelected(true);
                       itemClicked = 6;
                       break;
                   case 7:
                       view.setSelected(true);
                       itemClicked = 7;
                       break;
                   case 8:
                       view.setSelected(true);
                       itemClicked = 8;
                       break;
                   case 9:
                       view.setSelected(true);
                       itemClicked = 9;
                       break;
                   case 10:
                       view.setSelected(true);
                       itemClicked = 10;
                       break;
                   case 11:
                       view.setSelected(true);
                       itemClicked = 11;
                       break;
                   case 12:
                       view.setSelected(true);
                       itemClicked = 12;
                       break;
                   case 13:
                       view.setSelected(true);
                       itemClicked = 13;
                       break;
                   case 14:
                       view.setSelected(true);
                       itemClicked = 14;
                       break;
                   case 15:
                       view.setSelected(true);
                       itemClicked = 15;
                       break;
                   case 16:
                       view.setSelected(true);
                       itemClicked = 16;
                       break;
                   case 17:
                       view.setSelected(true);
                       itemClicked = 17;
                       break;
                   case 18:
                       view.setSelected(true);
                       itemClicked = 18;
                       break;
                   case 19:
                       view.setSelected(true);
                       itemClicked = 19;
                       break;


               }


           }
       });





    }


    @Override
    public void onBackPressed(){

        saveData = null;
        mListView = null;
        mAdapter = null;
        System.gc();
        Toast.makeText(getApplicationContext(),"해제",Toast.LENGTH_SHORT).show();
        finish();

    }



    public ArrayList<SaveData> unSerialize() {

        try {

            FileInputStream fis = new FileInputStream("/sdcard/savefile.sav");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);

            ArrayList<SaveData> savedata = new ArrayList<SaveData>();


              savedata = (ArrayList<SaveData>) ois.readObject();

            return savedata;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }
        Log.v("직렬화","savedata 리턴 실패");
         return null;
    }



/*
즐거운 세이브 로드 만들기



 */


}
