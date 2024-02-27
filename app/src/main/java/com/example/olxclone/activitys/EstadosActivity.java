package com.example.olxclone.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olxclone.R;
import com.example.olxclone.adapter.EstadoAdapter;
import com.example.olxclone.helper.EstadosList;
import com.example.olxclone.helper.SPFiltro;
import com.example.olxclone.model.Estado;

public class EstadosActivity extends AppCompatActivity implements EstadoAdapter.OnClickListener {

    private RecyclerView rv_estados;
    private EstadoAdapter estadoAdapter;
    private Boolean acesso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            acesso = bundle.getBoolean("filtros");
        }

        iniciaComponentes();

        configRV();

        configCliques();

    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void configRV(){
        rv_estados.setLayoutManager(new LinearLayoutManager(this));
        rv_estados.setHasFixedSize(true);
        estadoAdapter = new EstadoAdapter(EstadosList.getList(),this);
        rv_estados.setAdapter(estadoAdapter);
    }

    private void iniciaComponentes(){

        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Estados");

        rv_estados = findViewById(R.id.rv_estados);

    }


    @Override
    public void Onclick(Estado estado) {

        if (!estado.getNome().equals("Brasil")){
            SPFiltro.setFiltro(this,"ufEstado", estado.getUf());
            SPFiltro.setFiltro(this,"nomeEstado", estado.getNome());

            if (acesso) {
                finish();
            }else{
                startActivity(new Intent(this, RegioesActivity.class));
            }

        }else{
            SPFiltro.setFiltro(this,"ufEstado", "");
            SPFiltro.setFiltro(this,"nomeEstado", "");

            finish();
        }

    }
}