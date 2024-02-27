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
import com.example.olxclone.adapter.RegiaoAdapter;
import com.example.olxclone.helper.RegioesList;
import com.example.olxclone.helper.SPFiltro;

import java.util.ArrayList;
import java.util.List;

public class RegioesActivity extends AppCompatActivity implements RegiaoAdapter.OnClickListener {

    private RegiaoAdapter regiaoAdapter;
    private RecyclerView rv_regioes;
    private Boolean acesso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regioes);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            acesso = bundle.getBoolean("filtros");
        }

        iniciaComponentes();

        configCliques();

        configRv();

    }
    private void configRv(){

        rv_regioes.setLayoutManager(new LinearLayoutManager(this));
        rv_regioes.setHasFixedSize(true);
        regiaoAdapter = new RegiaoAdapter(RegioesList.getList(SPFiltro.getFiltro(this).getEstado().getUf()), this);
        rv_regioes.setAdapter(regiaoAdapter);


    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes(){

        TextView text_toobar = findViewById(R.id.text_toolbar);
        text_toobar.setText("Selecione a região");

        rv_regioes = findViewById(R.id.rv_regioes);
    }

    @Override
    public void Onclick(String regiao) {

        if (!regiao.equals("Todas as regiões")){
            SPFiltro.setFiltro(this, "ddd",regiao.substring(4,6));
            SPFiltro.setFiltro(this,"regiao",regiao);
        }else{
            SPFiltro.setFiltro(this, "ddd","");
            SPFiltro.setFiltro(this,"regiao","");
        }

        if (acesso){
            finish();
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}