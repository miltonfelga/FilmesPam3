package com.example.filmepam3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Filme_Activity extends AppCompatActivity {
    List<Filmes> filmesList;
    FilmesAdapter filmesAdapter;
    SQLiteDatabase meuBancoDeDados;
    ListView listViewFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filme_layout);

        listViewFilmes = findViewById(R.id.listarFilmeView);
        filmesList = new ArrayList<>();

        meuBancoDeDados = openOrCreateDatabase(MainActivity.NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        visualizarFilmesDatabase();
    }

    private void visualizarFilmesDatabase() {
        Cursor cursorFilmes = meuBancoDeDados.rawQuery("SELECT * FROM filme", null);


        if (cursorFilmes.moveToFirst()) {
            do {
                filmesList.add(new Filmes(
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

        //Verificar o layout
        filmesAdapter = new FilmesAdapter(this,R.layout.lista_view_filme, filmesList,meuBancoDeDados);

        listViewFilmes.setAdapter(filmesAdapter);
    }
}

