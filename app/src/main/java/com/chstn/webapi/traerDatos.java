package com.chstn.webapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class traerDatos extends AppCompatActivity {
    TextView tvRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traer_datos);

        tvRespuesta = findViewById(R.id.lblRespuesta);
        Button btProcesar = findViewById(R.id.btnProcesar);

        btProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcesarAPI();
            }
        });
    }

    public void ProcesarAPI() {
        String URl = "https://webapich.000webhostapp.com/ajax/estudiantes_reporte.php";

        OkHttpClient cliente = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URl)
                .build();

        cliente.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvRespuesta.setText("Error al conectarse al servidor");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String serverResponse = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvRespuesta.setText(serverResponse);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvRespuesta.setText("Error en la respuesta del servidor");
                        }
                    });
                }
            }
        });
    }
}
