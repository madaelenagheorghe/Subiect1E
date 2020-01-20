package com.example.subiect1e;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@TargetApi(23)
public class SendPackage extends AppCompatActivity {
    EditText id;
    EditText lat;
    EditText longit;
    EditText timestamp;

    RadioGroup rg;
    RadioButton rb;
    DatePicker datePicker;
    TimePicker timePicker;

    int p_id;
    float p_lat;
    float p_long;
    Date p_timestamp;
    String type;
    int ok=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);
        id=findViewById(R.id.et_id);
        lat=findViewById(R.id.et_lat);
        longit=findViewById(R.id.et_long);
        datePicker=findViewById(R.id.dp);
        timePicker=findViewById(R.id.tp);
        rg=findViewById(R.id.rg_type);
        RadioButton rbState=findViewById(R.id.rb_packageType2);
        RadioButton rbPosition=findViewById(R.id.rb_packageType1);

        Intent intent=getIntent();
        if(intent.hasExtra("package1")){
            DataPackage dp=(DataPackage)intent.getSerializableExtra("package1");
            id.setText(String.valueOf(dp.getPackageId()));
            id.setFocusable(false);
            lat.setText(String.valueOf(dp.getLatitude()));
            longit.setText(String.valueOf(dp.getLongitude()));
            Calendar timestamp = Calendar.getInstance();
            timestamp.setTimeInMillis(dp.getTimestamp().getTime());
            datePicker.updateDate(timestamp.get(Calendar.YEAR), timestamp.get(Calendar.MONTH), timestamp.get(Calendar.DATE));
            timePicker.setHour(timestamp.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(timestamp.get(Calendar.MINUTE));
            if(dp.getPackageType().toString().equals("state"))
                rbState.setChecked(true);
            else
                rbPosition.setChecked(true);
        }
    }

    public void button_add(View view) {
            ok=1;
            try {
                p_id = Integer.parseInt(id.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Id should be a number.", Toast.LENGTH_LONG).show();
                ok=0;
            }

            try {
                p_lat = Float.parseFloat(lat.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Latitude should be a two decimals number.", Toast.LENGTH_LONG).show();
                ok=0;
            }

            try {
                p_long = Float.parseFloat(longit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Longitude should be a two decimals number.", Toast.LENGTH_LONG).show();
                ok=0;
            }

            Calendar p_timestamp=Calendar.getInstance();
            p_timestamp.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
            try{
                rb=findViewById(rg.getCheckedRadioButtonId());
                String type=rb.getText().toString();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "You must select a type.",Toast.LENGTH_LONG).show();
                ok=0;
            }
            if(ok==1){
                DataPackage.packageType p_type= DataPackage.packageType.position;
                if(rb.getText().toString().equals("state"))
                    p_type= DataPackage.packageType.state;
                DataPackage dp=new DataPackage(p_id, p_type, p_lat, p_long, p_timestamp.getTime());
                Log.e("DP", dp.toString());
                Intent returnIntent=new Intent();
                returnIntent.putExtra("package", dp);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

    }

    public void button_cancel(View view) {
        Intent returnIntent=new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }
}
