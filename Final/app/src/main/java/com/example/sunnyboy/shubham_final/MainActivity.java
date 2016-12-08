package com.example.sunnyboy.shubham_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private IceCreamShop myIceCreamShop = new IceCreamShop();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get button
        final Button button = (Button) findViewById(R.id.buttonFindTreat);
        //create listener
        View.OnClickListener onclick = new View.OnClickListener(){
            public void onClick(View view){
                findIceCream(view);
            }
        };
        //add listener to the button
        button.setOnClickListener(onclick);
    }

    public void findIceCream(View view){
        //get spinner
        Spinner flavorSpinner = (Spinner)findViewById(R.id.spinner);
        //get spinner item array position
        Integer flavor = flavorSpinner.getSelectedItemPosition();

        myIceCreamShop.setIceCreamShop(flavor);

        String suggestedIceCreamShop = myIceCreamShop.getIceCreamShop();

        String suggestedIceCreamShopURL = myIceCreamShop.getIceCreamShopURL();

        //System.out.println(suggestedIceCreamShop);
        //System.out.println(suggestedIceCreamShopURL);

        //create an intent
        Intent intent = new Intent(this, receive_IceCream.class);

        //pass data
        intent.putExtra("IceCreamShopName", suggestedIceCreamShop);
        intent.putExtra("IceCreamShopURL", suggestedIceCreamShopURL);

        //start the intent
        startActivity(intent);
    }

    public void getIceCream(View view) {
        EditText name = (EditText)findViewById(R.id.editText);
        String nameValue = name.getText().toString();

        //spinner
        Spinner flavorSpinner = (Spinner)findViewById(R.id.spinner);
        String flavorValue= String.valueOf(flavorSpinner.getSelectedItem());

        //toggle button
        ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleButton);
        boolean checkToggle = toggle.isChecked();
        String shape;
        if(checkToggle){
            shape = "cup";
        }
        else{
            shape= "cone";
        }

        // meditate switch
        Switch dairyFree_switch = (Switch)findViewById(R.id.switch1);
        String dairyFree_string = "";
        boolean dairyFree = dairyFree_switch.isChecked();
        if(dairyFree){
            dairyFree_string = " dairy-free";
        }

        //Yoga radio buttons
        RadioGroup iceCreamType = (RadioGroup)findViewById(R.id.radioGroup);
        String iceCreamType_string = "";
        int iceCreamType_id = iceCreamType.getCheckedRadioButtonId();
        switch(iceCreamType_id){
            case -1:
                iceCreamType_string = "";
                break;
            case R.id.radioButton1:
                iceCreamType_string = "ice cream";
                break;
            case R.id.radioButton2:
                iceCreamType_string = "frozen yogurt";
                break;
            case R.id.radioButton3:
                iceCreamType_string = "gelato";
                break;
            default:
                iceCreamType_string = "";
        }

        //checkbox
        String checkbox_string = "";
        CheckBox check1 =(CheckBox)findViewById(R.id.checkBox1);
        boolean checked1 = check1.isChecked();
        if(checked1){
            checkbox_string += " sprinkles";
        }
        CheckBox check2 =(CheckBox)findViewById(R.id.checkBox2);
        boolean checked2 = check2.isChecked();
        if(checked2){
            checkbox_string += " hot fudge";
        }
        CheckBox check3 =(CheckBox)findViewById(R.id.checkBox3);
        boolean checked3 = check3.isChecked();
        if(checked3){
            checkbox_string += " nuts";
        }


        // Image view
        ImageView image = (ImageView)findViewById(R.id.imageView);

        switch(flavorValue) {
            case "Death by Chocolate": image.setImageResource(R.drawable.chocolate);
                break;
            case "Cookies and Cream": image.setImageResource(R.drawable.cookiescream);
                break;
            case "Salted Caramel": image.setImageResource(R.drawable.caramel);
                break;
            default:
        }

        //text view
        TextView iceCreamDescription = (TextView)findViewById(R.id.descriptionIceCreamTextView);
        iceCreamDescription.setText("My " + nameValue + " is a " + dairyFree_string + " " + flavorValue + " " + iceCreamType_string + " " + shape +
                " with " + checkbox_string);
    }
}
