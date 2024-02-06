package com.chstn.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class jsonnn extends AppCompatActivity {
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonnn);

        tableLayout = findViewById(R.id.tableLayout);

        obtenerDatos("https://disincentive-swaps.000webhostapp.com/IstP4/ajax/estudiantes_reporte.php?op=listar2&usu_id=1");
    }

    private void obtenerDatos(String URL) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            JSONArray jsonArray = jsonObject.getJSONArray("aaData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray materia = jsonArray.getJSONArray(i);
                                String campo1 = materia.getString(0);
                                String campo2 = materia.getString(1);
                                String campo3 = materia.getString(2);
                                String campo4 = materia.getString(3);
                                String campo5 = materia.getString(4);
                                String campo6 = materia.getString(5);
                                String campo7 = materia.getString(6);
                                agregarFilaTabla(campo1, campo2, campo3, campo4, campo5, campo6, campo7);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error al procesar JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void agregarFilaTabla(String campo1, String campo2, String campo3, String campo4, String campo5, String campo6, String campo7) {
        TableRow row = new TableRow(this);

        TextView tvCampo1 = new TextView(this);
        tvCampo1.setText(campo1);
        row.addView(tvCampo1);

        TextView tvCampo2 = new TextView(this);
        tvCampo2.setText(campo2);
        row.addView(tvCampo2);

        TextView tvCampo3 = new TextView(this);
        tvCampo3.setText(campo3);
        row.addView(tvCampo3);

        TextView tvCampo4 = new TextView(this);
        tvCampo4.setText(campo4);
        row.addView(tvCampo4);

        TextView tvCampo5 = new TextView(this);
        tvCampo5.setText(campo5);
        row.addView(tvCampo5);

        TextView tvCampo6 = new TextView(this);
        tvCampo6.setText(campo6);
        row.addView(tvCampo6);

        TextView tvCampo7 = new TextView(this);
        tvCampo7.setText(campo7);
        row.addView(tvCampo7);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tableLayout.addView(row);
            }
        });
    }
}
