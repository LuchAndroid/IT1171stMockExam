package com.example.it117firstmockexam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button btnCheck, btnClear, btnClose;
    private EditText txtPlate;
    private TextView lblRegDate, lblVehicleType, lblPlateType;
    private String plateNum, plateNumDigits, monthName, dateRange;
    private int monthNum, dayNum;
    private static final  String new4Wheel = "^[a-zA-Z]{3}[- ]*\\d{4}";
    private static final  String new2Wheel = "^[a-zA-Z]{2}[- ]*\\d{5}";
    private static final String old4Wheel = "^[a-zA-Z]{3}[- ]*\\d{3}";
    private static final String old2Wheel1 = "^[a-zA-Z]{2}[- ]*\\d{4}";
    private static final String old2Wheel2 = "^\\d{4}[- ]*[a-zA-Z]{2}";
    private static final String specialVehicle = "^[a-zA-Z]{3}[- ]*\\d{2}";
    private boolean isValidPlate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initiate
        btnCheck = findViewById(R.id.btnCheck);
        btnClear = findViewById(R.id.btnClear);
        btnClose = findViewById(R.id.btnClose);
        txtPlate = findViewById(R.id.txtPlate);
        lblVehicleType = findViewById(R.id.lblVehicleType);
        lblPlateType = findViewById(R.id.lblPlateType);
        lblRegDate = findViewById(R.id.lblRegDate);
        //Action Listeners
        btnCheck.setOnClickListener(v->check());
        btnClear.setOnClickListener(v->{
           clearInputs();
           clearOutputs();
        });
        txtPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearOutputs();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnClose.setOnClickListener(v->{
            finish();
            System.exit(0);
        });
    }

    private void check() {
        plateNum = txtPlate.getText().toString().trim();
        validatePlateNum();
        computeRegistrationDate();
        checkPlateType();
        checkVehicleType();
        hideSoftKeyboard(this);
    }

    private void validatePlateNum(){
        Pattern validPattern = Pattern.compile(new4Wheel+"|"+new2Wheel+"|"+old4Wheel+"|"+old2Wheel1+"|"+old2Wheel2+"|"+specialVehicle);
        isValidPlate = validPattern.matcher(plateNum).matches();
        if(!isValidPlate){
            Toast.makeText(this, "Error: Invalid Plate Number.", Toast.LENGTH_LONG).show();
            lblPlateType.setText("");
            lblVehicleType.setText("");
            lblRegDate.setText("");
        }
    }

    private void checkPlateType() {
        if (!isValidPlate) return;
        Pattern newPattern = Pattern.compile(new4Wheel+"|"+new2Wheel);
        Pattern oldPattern = Pattern.compile(old4Wheel+"|"+old2Wheel1+"|"+old2Wheel2);
        Pattern specialPattern = Pattern.compile(specialVehicle);
        if(newPattern.matcher(plateNum).matches()){
            lblPlateType.setText("New Plate Number");
        }
        else if(oldPattern.matcher(plateNum).matches()){
            lblPlateType.setText("Old Plate Number");
        }
        else if(specialPattern.matcher(plateNum).matches()){
            lblPlateType.setText("Special Plate Number");
        }
    }

    private void checkVehicleType() {
        if(!isValidPlate) return;
        Pattern fourWheelPattern = Pattern.compile(new4Wheel+"|"+old4Wheel);
        Pattern twoWheelPattern = Pattern.compile(new2Wheel+"|"+old2Wheel1+"|"+old2Wheel2);
        Pattern specialPattern = Pattern.compile(specialVehicle);
        if(fourWheelPattern.matcher(plateNum).matches()){
            lblVehicleType.setText("4-Wheeled");
        }
        else if(twoWheelPattern.matcher(plateNum).matches()){
            lblVehicleType.setText("2-Wheel");
        }
        else if(specialPattern.matcher(plateNum).matches()){
            lblVehicleType.setText("Special Motor Vehicle");
        }
    }

    private void computeRegistrationDate() {
        if(!isValidPlate) return;
        Pattern numbersPattern = Pattern.compile("\\d+");
        Matcher matcher = numbersPattern.matcher(plateNum);
        if(matcher.find()){
            plateNumDigits = matcher.group();
            monthNum = Integer.parseInt(plateNumDigits.charAt(plateNumDigits.length()-1)+"");
            switch (monthNum){
                case 1:
                    monthName = "January";
                    break;
                case 2:
                    monthName = "February";
                    break;
                case 3:
                    monthName = "March";
                    break;
                case 4:
                    monthName = "April";
                    break;
                case 5:
                    monthName = "May";
                    break;
                case 6:
                    monthName = "June";
                    break;
                case 7:
                    monthName = "July";
                    break;
                case 8:
                    monthName = "August";
                    break;
                case 9:
                    monthName = "September";
                    break;
                case 0:
                    monthName = "October";
                    break;
            }
            dayNum = Integer.parseInt(plateNumDigits.charAt(plateNumDigits.length()-2)+"");
            if(dayNum >= 1 && dayNum <= 3) dateRange = "1st and 7th working day";
            else if(dayNum >= 4 && dayNum <= 6) dateRange = "8th and 14th working day";
            else if(dayNum >= 7 && dayNum <= 8) dateRange = "15th and 21st working day";
            else dateRange = "22nd and last working day";
            //Display Registration Date
            lblRegDate.setText("On or before "+monthName+", between "+dateRange);
        }
    }

    private void clearInputs(){
        txtPlate.setText("");
    }

    private void clearOutputs(){
        lblVehicleType.setText("");
        lblPlateType.setText("");
        lblRegDate.setText("");
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}