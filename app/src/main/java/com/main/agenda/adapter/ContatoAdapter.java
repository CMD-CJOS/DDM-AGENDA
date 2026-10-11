package com.main.agenda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.agenda.R;
import com.main.agenda.model.Contato;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {
    private List<Contato> contatos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Contato contato);
    }

    public ContatoAdapter(List<Contato> contatos, OnItemClickListener listener) {
        this.contatos = contatos;
        this.listener = listener;
    }

    public void atualizarLista(List<Contato> novaLista) {
        this.contatos = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contato, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.tvNome.setText(contato.getNome());
        holder.tvTelefone.setText(contato.getTelefone());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(contato));
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    static class ContatoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvTelefone;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeItem);
            tvTelefone = itemView.findViewById(R.id.tvTelefoneItem);
        }
    }
}
