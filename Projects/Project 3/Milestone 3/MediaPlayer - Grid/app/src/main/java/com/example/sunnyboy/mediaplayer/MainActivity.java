package com.example.sunnyboy.mediaplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.StateListDrawable;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> dirList = new ArrayList<String>();
    public ArrayList<Integer> dirSizeList = new ArrayList<Integer>();
    ListView list;
    public com.example.sunnyboy.mediaplayer.CustomList adapter;
    public GridView gridview;
    public ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int writePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(writePermissionCheck!= PackageManager.PERMISSION_GRANTED || readPermissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE }, 0);
        }

        getAllFiles();
        System.out.println("dirList " + dirList);
        System.out.println("dirSizeList " + dirSizeList);

        gridview = (GridView) findViewById(R.id.folderGrid);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);
        //imageAdapter.add(String.valueOf(R.drawable.pattern));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                System.out.println("onItemClick ");

                gridview.setSelector(new StateListDrawable());
                Intent intent = new Intent(getApplicationContext(), ListSongsActivity.class);
                intent.putExtra("dir_name", dirList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //clickImageButton.setEnabled(true);
            }
        }
    }

    public void getAllFiles(){
        dirList.clear();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            String mediaPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
            getFolderSize(mediaPath);

            String ringtonesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
            getFolderSize(ringtonesPath);

            String notificationsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
            getFolderSize(notificationsPath);

            String moviesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
            getFolderSize(moviesPath);

            String selection= MediaStore.Video.Media.DATA +" like?";
            String[] selectionArgs=new String[]{"%Video%"};

            Cursor videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, selection, selectionArgs, MediaStore.Video.Media.DATE_TAKEN + " DESC");

            int vidsCount = 0;
            if (videocursor != null) {
                vidsCount = videocursor.getCount();
                while (videocursor.moveToNext()) {
                    System.out.println("VIDEO" + videocursor.getString(0) + "count:" + vidsCount);
                }
            }

            dirList.add("Video");
            dirSizeList.add(vidsCount);

            dirList.add("All");
            int allFilesCount = 0;
            for(int i = 0; i<dirSizeList.size();i++){
                allFilesCount+=dirSizeList.get(i);
            }
            dirSizeList.add(allFilesCount);
        }

    }

    public void getFolderSize(String path){
        int numOfFiles = 0;
        ArrayList<String> localDirList = new ArrayList<String>();
        ArrayList<Integer> localDirSizeList = new ArrayList<Integer>();

        File mainDir = new File(path);

        System.out.println("files " + mainDir);
        localDirList.add(mainDir.getName());
        File[] files = mainDir.listFiles();

        if(mainDir.isDirectory()){
            if(files == null){
                System.out.println("files null");
                return;
            }
        }

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("old files " + file.getName());
                localDirList.add(file.getName());
            } else {
                numOfFiles++;
            }
        }

        System.out.println("count files" + numOfFiles);

        localDirSizeList.add(numOfFiles);
        for(int i =1; i<localDirList.size();i++){
            File targetDir = new File(path + "/"+localDirList.get(i)+"/");
            System.out.println("new files " + targetDir);
            File[] remainingFiles = targetDir.listFiles();
            System.out.println("count files" + remainingFiles.length);
            localDirSizeList.add(remainingFiles.length);
        }

        dirList.addAll(localDirList);
        dirSizeList.addAll(localDirSizeList);
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context cont;
        private int selectedPosition = -1;
        public ImageAdapter(Context c)
        {
            cont = c;
        }
        public int getCount()
        {
            return dirList.size();
        }
        public String getItem(int position)
        {
            return dirList.get(position);
        }
        public long getItemId(int position)
        {
            return 0;
        }
        public void add(String path){
            dirList.add(path);
        }
        public void remove(String path){
            dirList.remove(path);
        }
        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }
        void clear() {
            dirList.clear();
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            String file;
            File mainDir;
            Bitmap bitMap = null;


            Activity activity = (Activity) cont;
            LayoutInflater inflater = activity.getLayoutInflater();
            View gridViewNew;

            if (convertView == null) {

                gridViewNew = new View(cont);

                // get layout from mobile.xml
                gridViewNew = inflater.inflate(R.layout.grid_view_layout, null);

                // set value into textview
                TextView folder_name = (TextView) gridViewNew.findViewById(R.id.folder_name);
                folder_name.setText(dirList.get(position));
                TextView folder_size = (TextView) gridViewNew.findViewById(R.id.folder_size);
                System.out.println("dirList" + dirList.get(position));

                folder_size.setText(String.valueOf(dirSizeList.get(position)));
                System.out.println("check " + dirSizeList.get(position));

                // set image based on selected text
                imageView = (ImageView) gridViewNew.findViewById(R.id.img);

                //imageView = new ImageView(cont);

                imageView.setLayoutParams(new RelativeLayout.LayoutParams(350, 350));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = false ;
                bmOptions.inSampleSize = 4;
                bmOptions.inPurgeable = true ;

                bitMap = BitmapFactory.decodeResource(getResources(),R.drawable.pattern);

                imageView.setImageBitmap(bitMap);

            } else {
                gridViewNew = (View) convertView;
            }

            return gridViewNew;
        }
    }
}
