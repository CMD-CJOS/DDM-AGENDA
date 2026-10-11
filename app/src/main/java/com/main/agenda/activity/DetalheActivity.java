package com.main.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.agenda.R;
import com.main.agenda.database.ContatoDAO;
import com.main.agenda.model.Contato;

public class DetalheActivity extends AppCompatActivity {
    private TextView tvNome, tvTelefone, tvEmail, tvEndereco;
    private Button btnEditar, btnExcluir;
    private ContatoDAO contatoDAO;
    private int contatoId = -1;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        setTitle("Detalhes do Contato");

        tvNome = findViewById(R.id.tvDetalheNome);
        tvTelefone = findViewById(R.id.tvDetalheTelefone);
        tvEmail = findViewById(R.id.tvDetalheEmail);
        tvEndereco = findViewById(R.id.tvDetalheEndereco);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        contatoDAO = new ContatoDAO(this);

        if (getIntent().hasExtra("contato_id")) {
            contatoId = getIntent().getIntExtra("contato_id", -1);
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalheActivity.this, FormActivity.class);
            intent.putExtra("contato_id", contatoId);
            startActivity(intent);
        });

        btnExcluir.setOnClickListener(v -> {
            if (contatoId != -1) {
                contatoDAO.deletar(contatoId);
                Toast.makeText(this, "Contato excluído com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (contatoId != -1) {
            contato = contatoDAO.buscarPorId(contatoId);
            if (contato != null) {
                tvNome.setText(contato.getNome());
                tvTelefone.setText(contato.getTelefone() != null && !contato.getTelefone().isEmpty() ? contato.getTelefone() : "-");
                tvEmail.setText(contato.getEmail() != null && !contato.getEmail().isEmpty() ? contato.getEmail() : "-");
                tvEndereco.setText(contato.getEndereco() != null && !contato.getEndereco().isEmpty() ? contato.getEndereco() : "-");
            } else {
                finish();
            }
        }
    }
}
