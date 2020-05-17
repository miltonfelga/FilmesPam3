package com.example.filmepam3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FilmesAdapter extends ArrayAdapter<Filmes> {

    Context mCtx;
    int listaLayoutRes;
    List<Filmes> listaFilmes;
    SQLiteDatabase meuBancoDeDados;


    public FilmesAdapter(Context mCtx, int listaLayoutRes, List<Filmes> listaFilmes, SQLiteDatabase meuBancoDeDados) {
        super(mCtx, listaLayoutRes, listaFilmes);

        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.listaFilmes = listaFilmes;
        this.meuBancoDeDados = meuBancoDeDados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        final Filmes filme = listaFilmes.get(position);

        TextView txtViewNome = view.findViewById(R.id.txtNomeViewFilme);
        TextView txttViewGenero = view.findViewById(R.id.txtGeneroViewfilme);
        TextView txtViewLançamento = view.findViewById(R.id.txtlançamentoViewFilme);
        TextView txtViewNota = view.findViewById(R.id.txtNotaviewFilme);
        TextView txtViewSinopse = view.findViewById(R.id.txtSinopseviewFilme);

        txtViewNome.setText(filme.getNome());
        txttViewGenero.setText(filme.getGenero());
        txtViewLançamento.setText(String.valueOf(filme.getLançamento()));
        txtViewNota.setText(filme.getNota());
        txtViewSinopse.setText(filme.getSinopse());

        Button btnExcluir = view.findViewById(R.id.btnExcluirViewFilme);
        Button btnEditar = view.findViewById(R.id.btnEditarViewFilme);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarFilmes(filme);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Deseja excluir?");
                builder.setIcon(android.R.drawable.ic_notification_clear_all);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM filme WHERE id = ?";
                        meuBancoDeDados.execSQL(sql, new Integer[]{filme.getId()});
                        recarregarFilmesDB();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;

    }

    public void alterarFilmes(final Filmes filme) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.caixa_alterar_filmes, null);
        builder.setView(view);

        final EditText txtEditarFilme = view.findViewById(R.id.txtEditarFilme);
        final EditText txtEditarLançamento = view.findViewById(R.id.txtEditarLançamento);
        final Spinner spnGeneros = view.findViewById(R.id.spnGeneros);
        final EditText txtEditarNota = view.findViewById(R.id.txtEditarNota);
        final EditText txtEditarSinopse = view.findViewById(R.id.txtEditarSinopse);

        txtEditarFilme.setText(filme.getNome());
        txtEditarLançamento.setText(String.valueOf(filme.getLançamento()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarFilme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtEditarFilme.getText().toString().trim();
                String lançamento = txtEditarLançamento.getText().toString().trim();
                String genero = spnGeneros.getSelectedItem().toString().trim();
                String nota = txtEditarNota.getText().toString().trim();
                String sinopse = txtEditarSinopse.getText().toString().trim();

                if (nome.isEmpty()) {
                    txtEditarFilme.setError("Nome está em branco");
                    txtEditarFilme.requestFocus();
                    return;
                }
                if (lançamento.isEmpty()) {
                    txtEditarLançamento.setError("Lançamento está em branco");
                    txtEditarLançamento.requestFocus();
                    return;
                }
                if (nota.isEmpty()) {
                    txtEditarNota.setError("nota está em branco");
                    txtEditarNota.requestFocus();
                    return;
                }
                if (sinopse.isEmpty()) {
                    txtEditarSinopse.setError("Sinopse está em branco");
                    txtEditarSinopse.requestFocus();
                    return;
                }

                String sql = "UPDATE filme SET nome = ?, genero = ?, lançamento = ?, nota = ?, sinopse = ? WHERE id = ?";
                meuBancoDeDados.execSQL(sql,
                        new String[]{nome, genero, lançamento, nota, sinopse, String.valueOf(filme.getId())});
                Toast.makeText(mCtx, "Filme alterado com sucesso!!!", Toast.LENGTH_LONG).show();

                recarregarFilmesDB();

                dialog.dismiss();
            }
        });

    }

    public void recarregarFilmesDB() {
        Cursor cursorFilmes = meuBancoDeDados.rawQuery("SELECT * FROM filme", null);
        if (cursorFilmes.moveToFirst()) {
            listaFilmes.clear();
            do {
                listaFilmes.add(new Filmes(
                        cursorFilmes.getInt(0),
                        cursorFilmes.getString(1),
                        cursorFilmes.getString(2),
                        cursorFilmes.getString(3),
                        cursorFilmes.getString(4),
                        cursorFilmes.getString(5)

                ));
            } while (cursorFilmes.moveToNext());
        }
        cursorFilmes.close();
        notifyDataSetChanged();
    }
}


