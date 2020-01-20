package com.example.subiect1e;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PackageList extends AppCompatActivity {
    private ArrayList<DataPackage> packages= new ArrayList<>();
    ListView lv;
    DataPackage dpC;
    int prevPos;
    Spinner spinnerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        lv=findViewById(R.id.lv_packages);
        spinnerOptions=findViewById(R.id.spinner);
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    PackageAdapter packageAdapter=new PackageAdapter(getApplicationContext(), R.layout.item_layout, packages);
                    lv.setAdapter(packageAdapter);
                }
                if(position==1){
                    ArrayList<DataPackage> dataPackagesPos=new ArrayList<>();
                    for(DataPackage package1 : packages){
                        if(package1.getPackageType().name().equals("state"))
                            dataPackagesPos.add(package1);
                    }
                    PackageAdapter packageAdapter=new PackageAdapter(getApplicationContext(), R.layout.item_layout, dataPackagesPos);
                    lv.setAdapter(packageAdapter);
                }

                if(position==2){
                    ArrayList<DataPackage> dataPackagesPos=new ArrayList<>();
                    for(DataPackage package1 : packages){
                        if(package1.getPackageType().name().equals("position"))
                            dataPackagesPos.add(package1);
                    }
                    PackageAdapter packageAdapter=new PackageAdapter(getApplicationContext(), R.layout.item_layout, dataPackagesPos);
                    lv.setAdapter(packageAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        packages=(ArrayList<DataPackage>)getIntent().getSerializableExtra("packages");
        for(DataPackage dp : packages)
            Toast.makeText(this,dp.toString(), Toast.LENGTH_LONG).show();

        PackageAdapter packageAdapter=new PackageAdapter(getApplicationContext(), R.layout.item_layout, packages);
        lv.setAdapter(packageAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SendPackage.class);

                intent.putExtra("package1", packages.get(position));
                startActivityForResult(intent,1);
                prevPos=position;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                dpC=(DataPackage)data.getSerializableExtra("package");
                packages.get(prevPos).setLatitude(dpC.getLatitude());
                packages.get(prevPos).setLongitude(dpC.getLongitude());
                packages.get(prevPos).setPackageType(dpC.getPackageType());
                packages.get(prevPos).setTimestamp(dpC.getTimestamp());
                PackageAdapter packageAdapter=new PackageAdapter(getApplicationContext(), R.layout.item_layout, packages);
                lv.setAdapter(packageAdapter);
                Toast.makeText(this, dpC.toString(), Toast.LENGTH_LONG).show();
            }
            if(resultCode==RESULT_CANCELED){
                Toast.makeText(this,"Action cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
}
