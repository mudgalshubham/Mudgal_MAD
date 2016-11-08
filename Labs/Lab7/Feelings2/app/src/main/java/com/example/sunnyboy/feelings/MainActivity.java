package com.example.sunnyboy.feelings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findMood(View view) {
        EditText name = (EditText)findViewById(R.id.nameEditText);
        String nameValue = name.getText().toString();

        //spinner
        Spinner moodSpinner = (Spinner)findViewById(R.id.spinner);
        String moodValue= String.valueOf(moodSpinner.getSelectedItem());

        //toggle button
        ToggleButton toggle = (ToggleButton)findViewById(R.id.energyToggleButton);
        boolean energy = toggle.isChecked();
        String energyString;
        if(energy){
            energyString = "positive";
        }
        else{
            energyString= "negative";
        }

        // meditate switch
        Switch meditate_switch = (Switch)findViewById(R.id.switch1);
        String meditate_string = "";
        boolean meditate = meditate_switch.isChecked();
        if(meditate){
            meditate_string = " who meditates";
        }

        //Yoga radio buttons
        RadioGroup yoga = (RadioGroup)findViewById(R.id.yogaRadioGroup);
        String yoga_string = "";
        int yoga_id = yoga.getCheckedRadioButtonId();
        switch(yoga_id){
            case -1:
                yoga_string = "no";
                break;
            case R.id.radioButton1:
                yoga_string = "Yin";
                break;
            case R.id.radioButton2:
                yoga_string = "Bikram";
                break;
            case R.id.radioButton3:
                yoga_string = "Hatha";
                break;
            default:
                yoga_string = "no";
        }

        //checkbox
        String checkbox_string = "";
        CheckBox check1 =(CheckBox)findViewById(R.id.checkBox1);
        boolean checked1 = check1.isChecked();
        if(checked1){
            checkbox_string += " conservative";
        }
        CheckBox check2 =(CheckBox)findViewById(R.id.checkBox2);
        boolean checked2 = check2.isChecked();
        if(checked2){
            checkbox_string += " sarcastic";
        }
        CheckBox check3 =(CheckBox)findViewById(R.id.checkBox3);
        boolean checked3 = check3.isChecked();
        if(checked3){
            checkbox_string += " secretive";
        }
        CheckBox check4 =(CheckBox)findViewById(R.id.checkBox4);
        boolean checked4 = check4.isChecked();
        if(checked4){
            checkbox_string += " enlightened";
        }

        // Image view
        ImageView image = (ImageView)findViewById(R.id.imageView);

        switch(moodValue) {
            case "happy": image.setImageResource(R.drawable.happy);
                            break;
            case "sad": image.setImageResource(R.drawable.sad);
                            break;
            case "angry": image.setImageResource(R.drawable.angry);
                            break;
            case "confused": image.setImageResource(R.drawable.confused);
                            break;
            default:
        }

        //text view
        TextView feeling = (TextView)findViewById(R.id.feelingTextView);
        feeling.setText(nameValue + " is a " + energyString + " person in a " + moodValue +" mood"+ meditate_string +" and does "+ yoga_string + " yoga "+ checkbox_string);
    }
}
