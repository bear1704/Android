package com.novel.aca.visualnovelproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BaseAdapterEx extends BaseAdapter {


    Context             mContext    = null;
    ArrayList<SaveData> mData       = null;
    LayoutInflater      mLayoutInflater = null;


    public BaseAdapterEx(Context context, ArrayList<SaveData> data)
    {
        mContext = context;
        mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount(){

        return mData.size();

    }

    @Override
    public long getItemId(int position){

        return position;

    }

    @Override
    public SaveData getItem(int position){

        return mData.get(position);
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent)
    {

        View itemLayout = convertView;

        if(itemLayout == null)
        {
            itemLayout = mLayoutInflater.inflate(R.layout.saveload_listview_item, null);
        }

        TextView saveOrder = (TextView) itemLayout.findViewById(R.id.saveload_order);
        TextView saveWords = (TextView) itemLayout.findViewById(R.id.saveload_words);
        ImageView saveImage = (ImageView) itemLayout.findViewById(R.id.saveload_image);


        saveOrder.setText(mData.get(position).getSaveOrder());
        saveWords.setText(mData.get(position).getSaveWord());
        saveImage.setImageBitmap(mData.get(position).getScreenShot());
        if(mData.get(position).getEncodeScreenShot_origin() != null){
        saveImage.setImageBitmap(mData.get(position).getEncodeScreenShot());}

        return itemLayout;

    }



}
