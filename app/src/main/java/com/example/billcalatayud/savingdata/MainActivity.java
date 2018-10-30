package com.example.billcalatayud.savingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "_tag";
    private EditText etSharePref;
    private TextView tvSharedPref;
    private EditText etPersonGender;
    private EditText etPersonAge;
    private EditText etPersonName;
    private PersonDatabase personDatabase;
    private ListView tvPerson;
    private ArrayAdapter<String> personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        personDatabase = new PersonDatabase(getApplicationContext());

        personAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, new ArrayList<String>());
        tvPerson.setAdapter(personAdapter);
    }

    private void bindViews() {
        //Share preferences
        etSharePref = findViewById(R.id.etSharedPref);
        tvSharedPref = findViewById(R.id.tvSharedPref);

        //database
        etPersonName = findViewById(R.id.etPersonName);
        etPersonAge = findViewById(R.id.etPersonAge);
        etPersonGender = findViewById(R.id.etPersonGender);

        tvPerson = findViewById(R.id.tvPerson);

    }

    public void onSharedPreferences(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        //Context.MODE_PRIVATE not shared with other application.
        //"MySharedPref" is the name that will be use to saving, if it's not created will create a new one.

        SharedPreferences.Editor editor = sharedPreferences.edit();
        //If you wanna make changes to the file you need to create an editor object.


        switch (view.getId()) {
            case R.id.btnSaveData:
                editor.putString("edittext", etSharePref.getText().toString());
                // editor.commit(); //Synchronous (same thread) return a boolean for the state of the saving.

                editor.apply(); //Asynchronous (another thread) does not retrieve if its writen or no
                //editor.clear(); //will clear everything on the editor; meant to reset all values.

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btnGetData:

                String etValue = sharedPreferences.getString("edittext", "Default String");
                //sharedPreferences.getString(name of the key, Default value to retrieve);

                /*
                * working as a hashmap
                * */
                tvSharedPref.setText(etValue);

                break;
        }
    }

    public void onSQLiteDatabase(View view) {
        String personName = etPersonName.getText().toString();
        String personAge = etPersonAge.getText().toString();
        String personGender = etPersonGender.getText().toString();

        Person person = new Person(personName,personAge,personGender);
        switch (view.getId()){
            case R.id.btnSavePerson:
                long rowId = personDatabase.savedPerson(person);
                Toast.makeText(this,String.valueOf(rowId),Toast.LENGTH_SHORT).show();

                break;

            case R.id.btnGetAllPerson:
                for (Person person1 : personDatabase.getPeople()) {
                    //Log.d(TAG, "onSQLiteDatabase: "+person1.toString());
                    personAdapter.add(person1.toString());
                }
                break;
        }
    }
}
