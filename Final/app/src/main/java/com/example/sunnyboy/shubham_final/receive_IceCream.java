package com.example.sunnyboy.shubham_final;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class receive_IceCream extends AppCompatActivity {

    private String iceCreamShop;
    private String iceCreamShopURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive__ice_cream);

        //get intent
        Intent intent = getIntent();
        iceCreamShop = intent.getStringExtra("IceCreamShopName");
        iceCreamShopURL = intent.getStringExtra("IceCreamShopURL");


        //update text view
        TextView messageView = (TextView) findViewById(R.id.iceCreamShopTextView);
        messageView.setText("You should check out " + iceCreamShop);

        //get image button
        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        //create listener
        View.OnClickListener onclick = new View.OnClickListener(){
            public void onClick(View view){
                loadWebSite(view);
            }
        };
        //add listener to the button
        imageButton.setOnClickListener(onclick);
    }

    public void loadWebSite(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(iceCreamShopURL));
        startActivity(intent);
    }
}