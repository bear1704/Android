package com.novel.aca.visualnovelproject;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends Activity {


    public static int STAGE1 = 50;
    public static int STAGE2 = 51;
    public static final int INTERVAL_TIME = 1500;
    public static final int PERMISSION_REQUEST_CODE_EXTERNAL = 4000;
    public static final int PERMISSION_REQUEST_CODE_EXTERNAL_READ = 4001;
    public long pressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /***   퍼미션(External WRITE)  ***/

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"퍼미션이 허용되어 자동으로 넘어갑니다",Toast.LENGTH_SHORT).show();

        }else{
            //퍼미션 거부됨

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){


                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE_EXTERNAL);





            }
            else{

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE_EXTERNAL);


            }


        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults){

        switch(requestCode){

            case PERMISSION_REQUEST_CODE_EXTERNAL:

                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"허용되어 넘어갈수있습니다",Toast.LENGTH_SHORT).show();

                }
                else{
                    finish();
                }


                return;
        }


    }








    public void startBtnClicked(View v){
        Intent gameScreen = new Intent(getApplicationContext(),GameScreen.class);

        gameScreen.putExtra("stage",STAGE1);
        startActivity(gameScreen);


        overridePendingTransition(R.anim.fade_in,R.anim.fade_mid);
        overridePendingTransition(R.anim.fade_mid,R.anim.fade_out);
        finish();

    }

    public void loadButtonClicked(View v){
        Intent saveLoad = new Intent(getApplicationContext(),SaveLoad.class);
        saveLoad.putExtra("sp","main");

        startActivity(saveLoad);
    }

    @Override
    public void onBackPressed(){

        long curTime = System.currentTimeMillis();
        long intervalTime = curTime - pressedTime;



        if(intervalTime >= 0 && INTERVAL_TIME > intervalTime){

            finish();
        }else{
            Toast.makeText(getApplicationContext(),"한 번 더 누르면 종료됩니다.",Toast.LENGTH_LONG).show();
            pressedTime = curTime;
        }

    }

}
