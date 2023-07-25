package com.example.a5_siqlelighte_internalstorage_sheredepreferance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY="key" , DATA_KEY2="key2";
    public static final String FILE_NAME1 = "Name1" , FILE_NAME2 = "Name2" ;

    EditText editTextPhone , editTextMessage ;
    TextView textViewPhone , textViewMessage ;
//            , textViewShare , textViewInternal , textViewSql
    Button loadShare, LoadInternal , LoadSql , SaveShare , SaveInternal , SaveSql ;

    boolean storageState = false;

    DatabaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();



        SaveShare.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            SharedPreferences prefs =getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            String data1 =editTextPhone.getText().toString();
            String data2 =editTextMessage.getText().toString();
            editor.putString(DATA_KEY,data1);
            editor.putString(DATA_KEY2,data2);
            editor.commit();}});
        loadShare.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            SharedPreferences prefs =getPreferences(MODE_PRIVATE);
            String data1 = prefs.getString(DATA_KEY,"No Data Found");
            String data2 = prefs.getString(DATA_KEY2,"No Data Found");
            textViewPhone.setText(data1);
            textViewMessage.setText(data2);}});




        SaveInternal.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            FileOutputStream fos1 = null ;
            FileOutputStream fos2 = null;
            try {fos1 = openFileOutput(FILE_NAME1,MODE_PRIVATE);
                fos2 = openFileOutput(FILE_NAME2,MODE_PRIVATE);
                String data1 = editTextPhone.getText().toString();
                String data2 = editTextMessage.getText().toString();
                fos1.write(data1.getBytes(StandardCharsets.UTF_8));
                fos2.write(data2.getBytes(StandardCharsets.UTF_8));}
            catch (FileNotFoundException e) {throw new RuntimeException(e);}
            catch (IOException e) {throw new RuntimeException(e);}finally
            {try{fos1.close();}catch (IOException e) {throw new RuntimeException(e);}
                try{fos2.close();}catch (IOException e) {throw new RuntimeException(e);
                }}}});
        LoadInternal.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            String phone = editTextPhone.getText().toString();
            String message = editTextMessage.getText().toString();
            if(phone.isEmpty() || message.isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Data", Toast.LENGTH_SHORT).show();
            }else{FileInputStream fis1 = null;
                FileInputStream fis2 = null;
                try{fis1 = openFileInput(FILE_NAME1);
                    fis2 = openFileInput(FILE_NAME2);
                    byte[] bytes1 = new byte[fis1.available()];
                    fis1.read(bytes1);
                    byte[] bytes2 = new byte[fis2.available()];
                    fis2.read(bytes2);
                    textViewPhone.setText(new String(bytes1));
                    textViewMessage.setText(new String(bytes2));}
                catch (FileNotFoundException e) {throw new RuntimeException(e);}
                catch (IOException e) {throw new RuntimeException(e);}finally {try {fis1.close();}
                catch (IOException e) {throw new RuntimeException(e);
                }}} }});




        SaveSql.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            String phone = editTextPhone.getText().toString();
            String message = editTextMessage.getText().toString();
            if(phone.isEmpty() || message.isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Data", Toast.LENGTH_SHORT).show();
            }else{
                User user = new User(phone,message);
                long rowId = adapter.insertUser(user);
                Toast.makeText(MainActivity.this, "Row ID: " + rowId, Toast.LENGTH_SHORT).show();
            }}});
        LoadSql.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
                User[] users = adapter.getAllUsers();
                StringBuilder data = new StringBuilder();
                for (User user : users){
                    data.append(user.toString()).append("\n");}
            textViewPhone.setText(data);
            textViewMessage.setText("");
        }});

    }



    private void initComponent() {
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);

        textViewPhone = findViewById(R.id.textViewPhone);
        textViewMessage = findViewById(R.id.textViewMessage);

//        textViewShare = findViewById(R.id.textViewShare);
//        textViewInternal = findViewById(R.id.textViewInternal);
//        textViewSql = findViewById(R.id.textViewSql);

        SaveShare = findViewById(R.id.SaveShare);
        loadShare = findViewById(R.id.loadShare);

        SaveInternal = findViewById(R.id.SaveInternal);
        LoadInternal = findViewById(R.id.LoadInternal);

        SaveSql = findViewById(R.id.SaveSql);
        LoadSql = findViewById(R.id.LoadSql);

        adapter = new DatabaseAdapter(MainActivity.this);

    }

}