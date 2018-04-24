package edu.illinois.cs.cs125.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText currency;
    private Spinner fromcurr;
    private Spinner tocurr;
    private Button convert;
    private TextView fromNum;
    private TextView toNum;
    private String base_url = "https://openexchangerates.org/api/latest.json?app_id=8a0d74b94aa44f089727cce2d2cc396a";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currency = (EditText) findViewById(R.id.currency);
        fromcurr = (Spinner) findViewById(R.id.fromcurr);
        tocurr = (Spinner) findViewById(R.id.tocurr);
        convert = (Button) findViewById(R.id.convert);
        fromNum = (TextView) findViewById(R.id.fromNum);
        toNum = (TextView) findViewById(R.id.toNum);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromcurr.setAdapter(adapter);
        tocurr.setAdapter(adapter);
         

    }
}
