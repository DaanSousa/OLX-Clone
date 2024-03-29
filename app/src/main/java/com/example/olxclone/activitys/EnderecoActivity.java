package com.example.olxclone.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olxclone.R;
import com.example.olxclone.helper.FirebaseHelper;
import com.example.olxclone.model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;

public class EnderecoActivity extends AppCompatActivity {

    private MaskEditText edt_cep;
    private EditText edt_uf;
    private EditText edt_municipio;
    private EditText edt_bairro;
    private ProgressBar progressBar;

    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        iniciaComponentes();

        recuperaEndereco();

        configCliques();
    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    public void validaDados(View view){
        String cep = edt_cep.getMasked();
        String uf = edt_uf.getText().toString();
        String municipio = edt_municipio.getText().toString();
        String bairro = edt_bairro.getText().toString();


        if(!cep.isEmpty()){
            if (cep.length() == 9){
                if(!uf.isEmpty()){
                    if(!municipio.isEmpty()){
                        if(!bairro.isEmpty()){

                            progressBar.setVisibility(View.VISIBLE);

                            if(endereco == null) endereco = new Endereco();
                            endereco.setCep(cep);
                            endereco.setUf(uf);
                            endereco.setMunicipio(municipio);
                            endereco.setBairro(bairro);
                            endereco.salvar(FirebaseHelper.getIdFirebase(), getBaseContext(), progressBar);

                        }else{
                            edt_bairro.requestFocus();
                            edt_bairro.setError("Preencha o bairro");
                        }
                    }else{
                        edt_municipio.requestFocus();
                        edt_municipio.setError("Preencha o município");
                    }
                }else{
                    edt_uf.requestFocus();
                    edt_uf.setError("Preencha a UF");
                }
            }else{
                edt_cep.requestFocus();
                edt_cep.setError("Preencha um CEP valido");
            }
        }else{
            edt_cep.requestFocus();
            edt_cep.setError("Preencha o CEP");
        }

    }

    private void recuperaEndereco(){

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    endereco = snapshot.getValue(Endereco.class);
                    configEndereco(endereco);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configEndereco(Endereco endereco){
        edt_cep.setText(endereco.getCep());
        edt_uf.setText(endereco.getUf());
        edt_municipio.setText(endereco.getMunicipio());
        edt_bairro.setText(endereco.getBairro());

        progressBar.setVisibility(View.GONE);
    }

    private void iniciaComponentes(){
        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Endereço");

        edt_cep = findViewById(R.id.edt_cep);
        edt_uf = findViewById(R.id.edt_uf);
        edt_municipio = findViewById(R.id.edt_municipio);
        edt_bairro = findViewById(R.id.edt_bairro);
        progressBar = findViewById(R.id.progressBar);
    }

}