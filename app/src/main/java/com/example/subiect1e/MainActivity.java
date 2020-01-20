package com.example.subiect1e;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<DataPackage> packages = new ArrayList<>();
    private DataPackageDB database;
    int ok=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = DataPackageDB.getInstance(this);
    }

    public void button_send(View view) {
        Intent intent = new Intent(this, SendPackage.class);
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DataPackage dp = (DataPackage) data.getSerializableExtra("package");
                Toast.makeText(this, dp.toString(), Toast.LENGTH_LONG).show();
                packages.add(dp);
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void button_list(View view) {
        Intent intent = new Intent(this, PackageList.class);
        intent.putExtra("packages", packages);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        for (int i = 0; i < packages.size(); i++) {
            outState.putSerializable("key" + i, packages.get(i));
        }
        outState.putInt("listsize", packages.size());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int s = savedInstanceState.getInt("listsize");
        for (int i = 0; i < s; i++) {
            packages.add((DataPackage) savedInstanceState.getSerializable("key" + i));
        }
    }

    public void button_backup(View view) {
        database.getDaoPackages().deleteTable();
        for (DataPackage dp : packages)
            database.getDaoPackages().insert(dp);
        Toast.makeText(getApplicationContext(), "Backup done!", Toast.LENGTH_LONG).show();
        List<DataPackage> packagesR;
        packagesR = database.getDaoPackages().getPackages();
        for (DataPackage dp : packagesR)
            Log.e("PACKAGE", dp.toString());

    }

    public void button_update(View view) {

        if (ok == 0) {
            JsonRead jsonRead = new JsonRead() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    List<DataPackage> packagesJson = parseJson(s);
                    for (DataPackage dpJson : packagesJson)
                        packages.add(dpJson);

                }
            };
            ok = 1;

            try {
                jsonRead.execute(new URL("http://pdm.ase.ro/examen/packages.json.txt"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, "Already updated.", Toast.LENGTH_LONG).show();

    }
}
