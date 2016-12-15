package com.example.sunnyboy.mediaplayer;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> dirList = new ArrayList<String>();
    public ArrayList<Integer> dirSizeList = new ArrayList<Integer>();

    public CustomList(Activity context, ArrayList<String> dirList , ArrayList<Integer> dirSizeList) {
        super(context, R.layout.list_row_view, dirList);
        this.context = context;
        this.dirList = dirList;
        this.dirSizeList = dirSizeList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = null;
        return rowView;
    }
}
