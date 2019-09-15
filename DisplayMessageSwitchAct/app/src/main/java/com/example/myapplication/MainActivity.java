package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
                        @Override
                public void onClick(View v){
                    //Do Something
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            EditText editText = (EditText)findViewById(R.id.edit_message);
                            String message = editText.getText().toString();
                            intent.putExtra("extra_data",message);
                            startActivity(intent);
        }
        });
    }


}
