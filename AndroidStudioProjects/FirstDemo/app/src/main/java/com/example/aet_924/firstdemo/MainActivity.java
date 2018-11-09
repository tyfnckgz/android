package com.example.aet_924.firstdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText firstName,lastName;
    Button   helloBtn;
    TextView greetingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName       = (EditText)  findViewById(R.id.firstName);
        lastName        = (EditText)  findViewById(R.id.lastName);
        helloBtn        = (Button)    findViewById(R.id.helloBtn);
        greetingText    = (TextView)  findViewById(R.id.greetingsText);

    }


    public  void sayHello(View v){

        this.greetingText.setText("Hello ,"+firstName.getText()+" "+lastName.getText());

    }
}
