package com.novel.aca.visualnovelproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

import static android.R.attr.tint;
import static android.R.attr.visible;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;



public class GameScreen extends Activity implements Serializable{

    public static final int SWIPE_MIN_DISTANCE = 120;
    public static final int SWIPE_MAX_DISTANCE = 250;
    public static final int SWIPE_LIMIT_VELOCITY = 200;
    public static final int NONE = 0;
    public static final int NEW_START = 50;
    public static final int LOAD_GAME = 51;
    public boolean backStatus = false;
    public static int globalStageNum;

    MediaPlayer mp_sound;
    MediaPlayer mp_bgm;

    private GestureDetector gestureDetector;  //제스쳐
    public static int keyNum_serial=0;
    Map<Integer, Line> map1 = null;//파싱해서 나올 결과를 저장해두는 Map 임시


    Bitmap screenShot;



    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(gestureDetector != null){
            return gestureDetector.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        setTextAlpha();
        System.gc();

        Intent stageSelectIntent = this.getIntent();
        int stageNum = stageSelectIntent.getIntExtra("stage",499);  // mainactivity로부터 스타트인지 로드인지 받아서 처리, 499는 디폴트값(값이안들어올경우)
        globalStageNum = stageNum;
        keyNum_serial = stageSelectIntent.getIntExtra("keynum",0);


        gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener(){

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_LIMIT_VELOCITY){//위로 스와이핑
                    /*RelativeLayout gamescreen = (RelativeLayout) findViewById(R.id.gamescreen);
                    LinearLayout gamescreen2 = (LinearLayout) findViewById(R.id.fullLayout);
                    gamescreen.setVisibility(INVISIBLE);
                    gamescreen2.setVisibility(VISIBLE);
                    Toast.makeText(getApplicationContext(),"위로 스와이핑",Toast.LENGTH_SHORT).show();

                    TextView fullTextView = (TextView) findViewById(R.id.fullwritetextview);
                    fullTextView.setAlpha(0.7f);*/

                    //현재는 의미없는 코드(제스쳐에 따라 full텍스트를 보여줄 생각이었으나, 철회됨)

                }
                else if(e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_LIMIT_VELOCITY){//아래로 스와이핑
                    TextView writeTextview = (TextView) findViewById(R.id.writeTextview);
                    writeTextview.setVisibility(INVISIBLE);
                    Toast.makeText(getApplicationContext(),"아래로 스와이핑",Toast.LENGTH_SHORT).show();
                }
                return super.onFling(e1,e2,velocityX,velocityY);
            }

        });




/************************************************제스쳐*****************************************************************/




        xmlTextParsing xmlparse = new xmlTextParsing();

        try {

            map1 = xmlparse.getInfoFromXml(this,stageNum);  // Intent를 통해 전송된 스테이지넘버를 사용



        }
        catch(XmlPullParserException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }




    }








    // 게임화면의 대사창의 투명도를 바꿔주는 메소드
    public void setTextAlpha(){
    TextView writeTextView = (TextView) findViewById(R.id.writeTextview);
    writeTextView.setAlpha(0.7f);


        ImageView fullImageView = (ImageView) findViewById(R.id.fullImageView);
        fullImageView.setAlpha(0.7f);

    }



    public void writeTextClicked(View v){


        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

    }

    @Override
    public void onBackPressed(){

        Button saveButton = (Button) findViewById(R.id.menu_saveloadButton);
        Button extraButton = (Button) findViewById(R.id.menu_ExtraButton);
        Button exitButton = (Button) findViewById(R.id.menu_ExitButton);
        ImageView fullImageView = (ImageView) findViewById(R.id.fullImageView);
        TextView writeTextView = (TextView) findViewById(R.id.writeTextview);
        RelativeLayout selectLayout = (RelativeLayout) findViewById(R.id.selectLayout);
        Button btn_select1 = (Button) findViewById(R.id.select1);
        Button btn_select2 = (Button) findViewById(R.id.select2);
        Button btn_select3 = (Button) findViewById(R.id.select3);
        screenShot = captureScreenshot();



        if(backStatus){

            backStatus = false;
            fullImageView.setVisibility(INVISIBLE);
            saveButton.setVisibility(INVISIBLE);
            extraButton.setVisibility(INVISIBLE);
            exitButton.setVisibility(INVISIBLE);
            writeTextView.setEnabled(true);
            btn_select1.setEnabled(true);
            btn_select2.setEnabled(true);
            btn_select3.setEnabled(true);

        }else{

            backStatus = true;
            fullImageView.setVisibility(VISIBLE);
            saveButton.setVisibility(VISIBLE);
            extraButton.setVisibility(VISIBLE);
            exitButton.setVisibility(VISIBLE);
            writeTextView.setEnabled(false);
            btn_select1.setEnabled(false);
            btn_select2.setEnabled(false);
            btn_select3.setEnabled(false);

        }



    }



    public Bitmap captureScreenshot(){

        View container;
        container = getWindow().getDecorView();
        container.buildDrawingCache();
        Bitmap captureView = container.getDrawingCache();

        return captureView;


    }

    public byte[] bitmapToByte(Bitmap bitmap){


        byte[] bitmapData = null;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            stream.flush();
            stream.close();

            bitmapData = stream.toByteArray();

        }
        catch(IOException e){
            e.printStackTrace();
        }


        return bitmapData;
    }






    public void menu_saveLoadButtonClicked(View v){  //세이브로드 버튼 누를시 창띄우는거

        byte[] currentImage = bitmapToByte(screenShot);


        Line temp_line = map1.get(keyNum_serial-1);//-1을 하는 이유 : map에서 xml파싱을 할 때, keyNum_Serial을 베이스로 파싱하는데
                                                    //세이브로드할땐 이미 한번클릭을했기때문에 키넘시리얼이 1이상이다. 근데 파싱은 0부터 시작이거든.
                                                    //그래서 -1을 안해주면 한단계 앞의 정보를 가져오는듯 하다.


        Intent slIntent = new Intent(getApplicationContext(),SaveLoad.class);
        slIntent.putExtra("keynum",keyNum_serial);
        slIntent.putExtra("stagenum",globalStageNum);
        slIntent.putExtra("words",temp_line.getText());  //오류가능성
        slIntent.putExtra("screenshot",currentImage);
        slIntent.putExtra("sp","screen");

       currentImage = null;

        temp_line = null;

        startActivity(slIntent);



    }

    public class MyAsyncTask extends AsyncTask<Object,String,Boolean> {




        TextView writeText = (TextView) findViewById(R.id.writeTextview);
        TextView name = (TextView) findViewById(R.id.name);
        ImageView leftImageView = (ImageView) findViewById(R.id.printImageLeft);
        ImageView rightImageView = (ImageView) findViewById(R.id.printImageRight);
        ImageView centerImageView = (ImageView) findViewById(R.id.printImageCenter);
        RelativeLayout backGroundImage = (RelativeLayout) findViewById(R.id.gamescreen);

        private int position = 0;


        @Override
        protected void onPreExecute(){



            Line line = map1.get(keyNum_serial);
            change_goto(line);


            name.setText(line.getName());   //이름바꾸는부분
            writeText.setEnabled(false);   // 대사출력중 터치못하게(안씹히게)

            change(line);
            vibrate(line);



            soundChange(line);
            bgmChange(line);

            change_select(line);

            writeText.setTextColor(line.getTextColor(getApplicationContext())); // 텍스트 컬러 바꾸기
            line = null; // line 메모리에서 out


        }

        protected Boolean doInBackground(Object... objects){

            Line line = map1.get(keyNum_serial);


            try{

                while(position != line.getText().length()){
                    publishProgress(line.getText().substring(0,++position));   //한글자씩 출력하는 부분
                    Thread.sleep(30);
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }

            return true;
        }
        @Override
        protected void onProgressUpdate(String... values){writeText.setText(values[0]);}

        protected void onPostExecute(Boolean result){keyNum_serial++;   writeText.setEnabled(true);}   //텍스트창 다시활성화

        public void change(Line line){

            Resources res = getResources();
            BitmapDrawable bd;
            Bitmap bit;


            switch (line.getLeftImage()){
                case 0:
                    leftImageView.setImageBitmap(null);

                    break;
                case 1:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.sangsa);
                    bit = bd.getBitmap();
                    leftImageView.setImageBitmap(bit);
                    bd = null;

                    break;
                case 2:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.hasa);
                    bit = bd.getBitmap();
                    leftImageView.setImageBitmap(bit);
                     bd = null;

                    break;
                case 3:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.donggyu);
                    bit = bd.getBitmap();
                    leftImageView.setImageBitmap(bit);
                    bd = null;

                    break;


            }
            switch (line.getCenterImage()){
                case 0:
                    centerImageView.setImageBitmap(null);
                    break;
                case 1:
                   bd = (BitmapDrawable)res.getDrawable(R.drawable.sangsa);
                   bit = bd.getBitmap();
                    centerImageView.setImageBitmap(bit);
                    bd = null;
                    break;
                case 2:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.hasa);
                    bit = bd.getBitmap();
                    centerImageView.setImageBitmap(bit);

                    bd = null;
                    break;
                case 3:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.donggyu);
                    bit = bd.getBitmap();
                    centerImageView.setImageBitmap(bit);

                    bd = null;
                    break;
            }
            switch (line.getRightImage()){
                case 0:
                    rightImageView.setImageBitmap(null);
                    break;
                case 1:
                     bd = (BitmapDrawable)res.getDrawable(R.drawable.sangsa);
                    bit = bd.getBitmap();
                    rightImageView.setImageBitmap(bit);

                    bd = null;
                    break;
                case 2:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.hasa);
                    bit = bd.getBitmap();
                    rightImageView.setImageBitmap(bit);

                    bd = null;
                    break;
                case 3:
                    bd = (BitmapDrawable)res.getDrawable(R.drawable.donggyu);
                    bit = bd.getBitmap();
                    rightImageView.setImageBitmap(bit);

                    bd = null;
                    break;
            }
            switch(line.getBackgroundImage()){
                case 0:
                    break; //null이면 배경그대로 유지
                case 1:
                    bd = (BitmapDrawable) res.getDrawable(R.drawable.room1);
                    backGroundImage.setBackground(bd);
                    bd = null;

                    break;

            }

            res = null;
            bit = null;

            System.gc();

        }



        public void soundChange(Line line){


            if(mp_sound!=null){
            mp_sound.release();
            }


            switch(line.getSound()){
                case NONE:
                    break;
                case 1:
                    mp_sound = MediaPlayer.create(GameScreen.this,R.raw.s1);
                        mp_sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                                public void onPrepared(MediaPlayer mp){
                                    mp.start();
                                }
                        });

                    break;
                case 2:
                    mp_sound = MediaPlayer.create(GameScreen.this,R.raw.s2);
                    mp_sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                        }
                    });

                    break;


            }

        }

        public void bgmChange(Line line){


            switch(line.getBgm()){
                case NONE:
                    break;
                case 1:
                    if(mp_bgm!=null){
                        mp_bgm.stop();
                    }
                    mp_bgm = MediaPlayer.create(GameScreen.this,R.raw.m1);
                    mp_bgm.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                            mp.setLooping(true);
                        }
                    });

                    break;
                case 2:
                    if(mp_bgm!=null){
                        mp_bgm.stop();
                    }
                    mp_bgm = MediaPlayer.create(GameScreen.this,R.raw.s2);
                    mp_bgm.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                            mp.setLooping(true);
                        }
                    });

                    break;


            }


        }

        public void vibrate(Line line){

            if(line.getVibrate()){
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                  vibrator.vibrate(1000);
                vibrator = null;
            }
                System.gc();

        }

        public void change_goto(Line line){
            final TextView writeTextView = (TextView) findViewById(R.id.writeTextview);

            if(line.isGotoEnable() == true){
                xmlTextParsing xml = new xmlTextParsing();
                try {
                    map1 = xml.getInfoFromXml(GameScreen.this, line.getGoto()[0]);  //0이 스테이지번호
                    keyNum_serial = line.getGoto()[1];
                    //writeTextView.performClick();
                    //추측이유 : 온클릭리스너가 클릭을 감지해서 새로 AsyncTask가 작동해야 제대로 작동을 하는데, change_goto메소드에서 클릭까지 해버리면
                    // AsyncTask가 초기화가 안돼서 바로 문장만 넘어가게 되는것 같다.
                }
                catch(IOException e){
                    e.printStackTrace();
                }catch(XmlPullParserException e){
                    e.printStackTrace();
                }
            }else{

            }

                                         }

        public void change_select(final Line line){
            /*final*/


            final RelativeLayout selectLayout = (RelativeLayout) findViewById(R.id.selectLayout);  // 선택지에서 visible되는 레이아웃
            Button btn_select1 = (Button) findViewById(R.id.select1);
            Button btn_select2 = (Button) findViewById(R.id.select2);
            Button btn_select3 = (Button) findViewById(R.id.select3);



                if(line.isSelectEnable() == true){
                    writeText.setVisibility(INVISIBLE);
                    //writeText.setEnabled(false);  아 대체 이게 왜안되는지 모르겠다. 같은 writeText를 쓰는 setvisibility는 개잘되는데 이건왜안되냐고.
                    selectLayout.setVisibility(VISIBLE);
                    btn_select1.setText(line.getSelectChoice()[0]);
                    btn_select2.setText(line.getSelectChoice()[1]);
                    btn_select3.setText(line.getSelectChoice()[2]);

                }

                //이제 해당버튼을 클릭하면 어디로 가는지만 만들면 됨.
                //차후에 플래그기능도 아마 여기다 추가하지않을까싶음
                //아래뷰 씹기도 만들어야함.

            btn_select1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    selectLayout.setVisibility(GONE);
                    xmlTextParsing xml = new xmlTextParsing();
                   // writeText.setEnabled(true);
                    writeText.setVisibility(VISIBLE);

                    try {
                        map1 = xml.getInfoFromXml(GameScreen.this, line.getSelectStage()[0]);
                        keyNum_serial = line.getSelectKeynum()[0];
                        writeText.performClick();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }catch(XmlPullParserException e){
                        e.printStackTrace();
                    }


                }
            });


            btn_select2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    selectLayout.setVisibility(INVISIBLE);
                    xmlTextParsing xml = new xmlTextParsing();
                   // writeText.setEnabled(true);
                    writeText.setVisibility(VISIBLE);

                    try {
                        map1 = xml.getInfoFromXml(GameScreen.this, line.getSelectStage()[1]);
                        keyNum_serial = line.getSelectKeynum()[1];
                        writeText.performClick();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }catch(XmlPullParserException e){
                        e.printStackTrace();
                    }


                }
            });

            btn_select3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    selectLayout.setVisibility(INVISIBLE);
                    xmlTextParsing xml = new xmlTextParsing();
                   // writeText.setEnabled(true);
                    writeText.setVisibility(VISIBLE);

                    try {
                        map1 = xml.getInfoFromXml(GameScreen.this, line.getSelectStage()[2]);
                        keyNum_serial = line.getSelectKeynum()[2];
                        writeText.performClick();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }catch(XmlPullParserException e){
                        e.printStackTrace();
                    }


                }
            });




        }











    }
}

