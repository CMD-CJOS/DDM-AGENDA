package com.main.agenda.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.main.agenda.R;
import com.main.agenda.database.ContatoDAO;
import com.main.agenda.model.Contato;

public class FormActivity extends AppCompatActivity {
    private EditText etNome, etTelefone, etEmail, etEndereco;
    private Button btnSalvar;
    private ContatoDAO contatoDAO;
    private int contatoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        etEmail = findViewById(R.id.etEmail);
        etEndereco = findViewById(R.id.etEndereco);
        btnSalvar = findViewById(R.id.btnSalvar);

        contatoDAO = new ContatoDAO(this);

        if (getIntent().hasExtra("contato_id")) {
            contatoId = getIntent().getIntExtra("contato_id", -1);
            Contato contato = contatoDAO.buscarPorId(contatoId);
            if (contato != null) {
                etNome.setText(contato.getNome());
                etTelefone.setText(contato.getTelefone());
                etEmail.setText(contato.getEmail());
                etEndereco.setText(contato.getEndereco());
                setTitle("Editar Contato");
            }
        } else {
            setTitle("Novo Contato");
        }

        btnSalvar.setOnClickListener(v -> {
            String nome = etNome.getText().toString().trim();
            String telefone = etTelefone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String endereco = etEndereco.getText().toString().trim();

            if (nome.isEmpty()) {
                etNome.setError("Preencha o nome do contato");
                Toast.makeText(this, "Por favor, preencha o Nome do contato!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contatoId == -1) {
                Contato novoContato = new Contato(nome, telefone, email, endereco);
                long id = contatoDAO.inserir(novoContato);
                if (id != -1) {
                    Toast.makeText(this, "Contato cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao cadastrar contato", Toast.LENGTH_SHORT).show();
                }
            } else {
                Contato contatoEditado = new Contato(contatoId, nome, telefone, email, endereco);
                int rows = contatoDAO.atualizar(contatoEditado);
                if (rows > 0) {
                    Toast.makeText(this, "Contato atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao atualizar contato", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
