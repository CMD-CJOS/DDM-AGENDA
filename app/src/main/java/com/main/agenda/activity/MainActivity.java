package com.main.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.agenda.R;
import com.main.agenda.adapter.ContatoAdapter;
import com.main.agenda.database.ContatoDAO;
import com.main.agenda.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerContatos;
    private FloatingActionButton fabAdicionar;
    private SearchView searchView;
    private ContatoDAO contatoDAO;
    private ContatoAdapter adapter;
    private List<Contato> listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerContatos = findViewById(R.id.recyclerContatos);
        fabAdicionar = findViewById(R.id.fabAdicionar);
        searchView = findViewById(R.id.searchView);

        contatoDAO = new ContatoDAO(this);

        recyclerContatos.setLayoutManager(new LinearLayoutManager(this));
        listaContatos = contatoDAO.listarTodos();

        adapter = new ContatoAdapter(listaContatos, contato -> {
            Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
            intent.putExtra("contato_id", contato.getId());
            startActivity(intent);
        });

        recyclerContatos.setAdapter(adapter);

        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrarContatos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarContatos(newText);
                return true;
            }
        });
    }

    private void filtrarContatos(String query) {
        List<Contato> filtrados = new ArrayList<>();
        for (Contato c : contatoDAO.listarTodos()) {
            if (c.getNome().toLowerCase().contains(query.toLowerCase()) ||
                    (c.getTelefone() != null && c.getTelefone().contains(query))) {
                filtrados.add(c);
            }
        }
        adapter.atualizarLista(filtrados);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaContatos = contatoDAO.listarTodos();
        adapter.atualizarLista(listaContatos);
    }
}
