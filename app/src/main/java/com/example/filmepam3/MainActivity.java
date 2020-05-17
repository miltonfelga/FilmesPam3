package com.example.filmepam3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOME_BANCO_DE_DADOS = "bdFilmes";

    TextView lblFilmes;
    EditText txtNomeFilmes, txtLançamentoFilmes , txtNotaFilmes, txtSinopseFilmes;
    Spinner spnGenero;

    Button btnAdicionaFilme;

    SQLiteDatabase meuBancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblFilmes = findViewById(R.id.lblVisualizaFilme);
        txtNomeFilmes = findViewById(R.id.txtNomeNovoFilme);
        txtLançamentoFilmes = findViewById(R.id.txtNovoLançamentoFilme);
        txtNotaFilmes = findViewById(R.id.txtNovoNotaFilme);
        spnGenero = findViewById(R.id.spnGeneros);
        txtSinopseFilmes = findViewById(R.id.txtNovoSinopseFilme);

        btnAdicionaFilme = findViewById(R.id.btnAdicionarfilme);

        btnAdicionaFilme.setOnClickListener(this);

        lblFilmes.setOnClickListener(this);


        meuBancoDeDados = openOrCreateDatabase(NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        criarTabelaFilmes();
    }

    private boolean verificarEntrada(String filme, String lançamento, String nota, String sinopse) {
        if (filme.isEmpty()) {
            txtNomeFilmes.setError("Por favor entre com o nome");
            txtNomeFilmes.requestFocus();
            return false;
        }

        if (lançamento.isEmpty()) {
            txtLançamentoFilmes.setError("Por favor entre com o lançamento");
            txtLançamentoFilmes.requestFocus();
            return false;
        }

        if (nota.isEmpty()) {
            txtNotaFilmes.setError("Por favor entre com a nota");
            txtNotaFilmes.requestFocus();
            return false;
        }

        if (sinopse.isEmpty()) {
            txtSinopseFilmes.setError("Por favor entre com a sinopse");
            txtSinopseFilmes.requestFocus();
            return false;
        }
        return true;
    }

    private void adicionarFilmes() {

        String nomeFilm = txtNomeFilmes.getText().toString().trim();
        String lançamentoFilm = txtLançamentoFilmes.getText().toString().trim();
        String notaFilm = txtNotaFilmes.getText().toString().trim();
        String generoFilm = spnGenero.getSelectedItem().toString();
        String sinopseFilm = txtSinopseFilmes.getText().toString().trim();


        if (verificarEntrada(nomeFilm, lançamentoFilm, notaFilm, sinopseFilm)) {

            String insertSQL = "INSERT INTO filme (" +
                    "nome, " +
                    "genero, " +
                    "nota," +
                    "lançamento," +
                    "sinopse)" +
                    "VALUES(?, ?, ?, ?, ?);";

            meuBancoDeDados.execSQL(insertSQL, new String[]{nomeFilm, generoFilm, notaFilm, lançamentoFilm, sinopseFilm});

            Toast.makeText(getApplicationContext(), "Filme adicionado com sucesso!!!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdicionarfilme:

                adicionarFilmes();
                break;
            case R.id.lblVisualizaFilme:
                startActivity(new Intent(getApplicationContext(), Filme_Activity.class));
                break;
        }

    }

    private void criarTabelaFilmes() {
        meuBancoDeDados.execSQL(
                "CREATE TABLE IF NOT EXISTS filme (" +
                        "id integer PRIMARY KEY AUTOINCREMENT," +
                        "nome varchar(200) NOT NULL," +
                        "genero varchar(200) NOT NULL," +
                        "nota varchar(5) NOT NULL," +
                        "lançamento varchar NOT NULL," +
                        "sinopse varchar NOT NULL );"
        );
    }

}
