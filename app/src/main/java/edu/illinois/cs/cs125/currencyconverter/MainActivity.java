package edu.illinois.cs.cs125.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText currency;
    private Spinner fromcurr;
    private Spinner tocurr;
    private Button convert;
    private TextView fromNum;
    private TextView toNum;
    private String base_url = "http://api.fixer.io/latest?base=";

    private static RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
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

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAPICall();
            }
        });
    }



    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    base_url + String.valueOf(fromcurr.getSelectedItem()),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            String outputCurrency = String.valueOf(tocurr.getSelectedItem());
                            double inputValue = Double.valueOf(currency.getText().toString());
                            JsonParser parser = new JsonParser();
                            JsonObject result = parser.parse(response.toString()).getAsJsonObject();
                            JsonObject rates = result.getAsJsonObject("rates");
                            if (rates.has(outputCurrency)) {
                                inputValue *= rates.getAsJsonPrimitive(outputCurrency).getAsDouble();
                            }
                            toNum.setText(Double.toString(inputValue));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            toNum.setText(e.toString());
            e.printStackTrace();
        }
    }
}
