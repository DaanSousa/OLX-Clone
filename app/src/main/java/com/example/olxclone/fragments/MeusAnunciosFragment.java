package com.example.olxclone.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.olxclone.R;
import com.example.olxclone.activitys.FormAnuncioActivity;
import com.example.olxclone.adapter.AnuncioAdapter;
import com.example.olxclone.helper.FirebaseHelper;
import com.example.olxclone.model.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MeusAnunciosFragment extends Fragment implements AnuncioAdapter.OnclickListener {

    private AnuncioAdapter anuncioAdapter;
    private List<Anuncio> anuncioList = new ArrayList<>();

    private SwipeableRecyclerView rv_anuncios;
    private ProgressBar progressBar;
    private TextView text_info;
    private Button btn_logar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meus_anuncios, container, false);

        iniciaComponentes(view);

        configRV();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        recuperaAnuncio();
    }

    private void recuperaAnuncio() {
        if (FirebaseHelper.getAutenticado()) {
            DatabaseReference anunciosRef = FirebaseHelper.getDatabaseReference()
                    .child("meus_anuncios")
                    .child(FirebaseHelper.getIdFirebase());
            anunciosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        anuncioList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Anuncio anuncio = ds.getValue(Anuncio.class);
                            anuncioList.add(anuncio);
                        }
                        text_info.setText("");
                        Collections.reverse(anuncioList);
                        anuncioAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        text_info.setText("Nenhum anúncio cadastrado");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            btn_logar.setVisibility(View.VISIBLE);
            text_info.setText("");
            progressBar.setVisibility(View.GONE);

        }
    }

    private void configRV() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_anuncios.setHasFixedSize(true);
        anuncioAdapter = new AnuncioAdapter(anuncioList, this);
        rv_anuncios.setAdapter(anuncioAdapter);

        rv_anuncios.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                showDialogDelete(anuncioList.get(position));
            }

            @Override
            public void onSwipedRight(int position) {
                showDialogEdit(anuncioList.get(position));
            }
        });

    }

    private void showDialogDelete(Anuncio anuncio) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle("Deseja remover o anúncio ?");
        alertDialog.setMessage("Clique em sim para remover o anúncio ou clique em fechar");
        alertDialog.setNegativeButton("Fechar", ((dialog, which) -> {
            dialog.dismiss();
            anuncioAdapter.notifyDataSetChanged();
        })).setPositiveButton("Sim", (dialog, which) -> {
            anuncioList.remove(anuncio);
            anuncio.remover();

            anuncioAdapter.notifyDataSetChanged();
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    private void showDialogEdit(Anuncio anuncio) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle("Deseja editar o anúncio ?");
        alertDialog.setMessage("Clique em sim para editar o anúncio ou clique em fechar");
        alertDialog.setNegativeButton("Fechar", ((dialog, which) -> {
            dialog.dismiss();
            anuncioAdapter.notifyDataSetChanged();
        })).setPositiveButton("Sim", (dialog, which) -> {
            Intent intent = new Intent(requireActivity(), FormAnuncioActivity.class);
            intent.putExtra("anuncioSelecionado", anuncio);
            startActivity(intent);

            anuncioAdapter.notifyDataSetChanged();
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    private void iniciaComponentes(View view) {

        rv_anuncios = view.findViewById(R.id.rv_anuncios);
        progressBar = view.findViewById(R.id.progressBar);
        text_info = view.findViewById(R.id.text_info);
        btn_logar = view.findViewById(R.id.btn_logar);
    }

    @Override
    public void Onclick(Anuncio anuncio) {

    }
}