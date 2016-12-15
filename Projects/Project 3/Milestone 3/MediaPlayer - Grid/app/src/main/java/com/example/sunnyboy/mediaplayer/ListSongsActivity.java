package com.example.sunnyboy.mediaplayer;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListSongsActivity extends AppCompatActivity {

    public String dir_name;

    ArrayList<Map<String,String>> songsMap = new ArrayList<Map<String,String>>();
    public ArrayList<String> songsList = new ArrayList<String>();
    private SimpleAdapter adapter;
    boolean isAllOptionSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_songs);
        System.out.println("ListSongsActivity onCreate called");

        dir_name = getIntent().getExtras().getString("dir_name");
        System.out.println("setAdapter called"+dir_name);

        songsMap.clear();
        System.out.println("targetPath called"+dir_name);

        TextView tv = (TextView) findViewById(R.id.listTitle);
        tv.setText("List Of "+dir_name);


        switch(dir_name){
            case "Video":
                extractAllvideos();
                System.out.println("video done "+dir_name);
                break;
            case "Movies":
                extractAllMovies();
                System.out.println("Movies done "+dir_name);
                break;
            case "All":
                extractAllSongs();
                extractAllvideos();
                extractAllMovies();
                break;
            default:
                extractAllSongs();
                System.out.println("music done "+dir_name);
        }

        ListView listSongs = (ListView)findViewById(R.id.listSongs);

        System.out.println("songsMap " +songsMap.size());
        System.out.println("songsMap " +songsMap);

        adapter = new SimpleAdapter(this, songsMap, R.layout.song,
            new String[] { "title","duration","singer"},
            new int[] {  R.id.textTitle, R.id.textDuration, R.id.textSinger} );

        LayoutInflater inflater = ListSongsActivity.this.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.song, null, true);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.play_img);
        //imageView.setImageResource(R.drawable.play);

        listSongs.setAdapter(adapter);

        listSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View item,
                                    int position, long id) {

            }
        });

    }

    public void playSelected(View view)
    {
        View parentRow = (View) view.getParent();
        ListView listView = (ListView)findViewById(R.id.listSongs);
        final int position = listView.getPositionForView(parentRow);
        System.out.println("position: "+position);
        String path = songsList.get(position);
        Uri audio = Uri.parse("file://" +path);
        System.out.println("song path" +path);
        Intent intent = new Intent( Intent.ACTION_VIEW);
        intent.setDataAndType(audio, "audio/*");
        startActivity(intent);
    }

    public void extractAllvideos(){
        String selection= MediaStore.Video.Media.DATA +" like?";
        String[] selectionArgs=new String[]{"%Video%"};
        Cursor videoCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, selection, selectionArgs, MediaStore.Video.Media.DATE_TAKEN + " DESC");

        int vidsCount = 0;
        int videoColumnIndex;
        if (videoCursor != null) {
            vidsCount = videoCursor.getCount();
            while (videoCursor.moveToNext()) {
                System.out.println("VIDEO"+videoCursor.getString(0)+ "count:"+ vidsCount);
                videoColumnIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                //videoCursor.moveToPosition(position);
                String filename = videoCursor.getString(videoColumnIndex);
                File curFile = new File(filename);
                System.out.println("FileName: "+curFile);

                addMedia(curFile);
            }
            //videocursor.close();
        }
    }

    public void extractAllMovies(){
        String moviesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
        getAllMediaFiles(moviesPath);
    }

    public void extractAllSongs(){
        String musicPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        getAllMediaFiles(musicPath);

        String ringtonesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
        getAllMediaFiles(ringtonesPath);

        String notificationsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
        getAllMediaFiles(notificationsPath);
    }

    public void getAllMediaFiles(String path){
        File mainDir = new File(path);
        System.out.println("targetDirector called");

        if(mainDir.getName().equals(dir_name) || dir_name.equals("All")){
            File[] files = mainDir.listFiles();
            for (File curFile : files) {
                if (curFile.isDirectory()) {
                } else {
                    System.out.println("files called");
                    addMedia(curFile);
                }
            }
        } else {
            File subDir = new File(path + "/"+dir_name+"/");
            if(subDir.exists()){
                System.out.println("new files " + subDir);
                File[] remainingFiles = subDir.listFiles();
                for (File curFile : remainingFiles) {
                    if (curFile.isDirectory()) {

                    } else {
                        System.out.println("files called");
                        addMedia(curFile);
                    }
                }
                System.out.println("targetDir done");
            }
        }
    }

    public void addMedia(File curFile){
        MediaMetadataRetriever md = new MediaMetadataRetriever();
        md.setDataSource(curFile.getAbsolutePath());
        int secs = Integer.parseInt(md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        int mins= secs / 60;
        secs =  secs % 60;
        String singer = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if ( singer == null || singer.equals(""))
            singer = "Unknown";
        if(dir_name.equals("Video") || dir_name.equals("Movies") || curFile.getAbsolutePath().contains(".mp4")){
            singer = "";
        }
        String songtitle  = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if ( songtitle == null )
            songtitle = curFile.getName();
        String duration = String.format("%02d:%02d", mins,secs);

        Song s = new Song();
        s.setTitle(songtitle);
        s.setFilename(curFile.getName());
        s.setDuration(duration);
        s.setSinger(singer);
        songsList.add(curFile.getAbsolutePath());
        //songs.add(s);

        Map<String, String> mapobject = convertSongToMap(s);
        songsMap.add(mapobject);

    }

    public Map<String,String> convertSongToMap(Song s) {

        HashMap<String, String> map = new HashMap<String,String>();
        map.put("title", s.getTitle());
        map.put("duration", s.getDuration());
        map.put("singer", s.getSinger());
        return map;

    }

    @Override
    public void onBackPressed()  {
        super.onBackPressed();
        System.out.println("onBackPressed ");
    }


    @Override
    public void onResume()
    {
        System.out.println("on Resume grid ");
        super.onResume();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        System.out.println("onActivityResult Grid called");

    }
}
