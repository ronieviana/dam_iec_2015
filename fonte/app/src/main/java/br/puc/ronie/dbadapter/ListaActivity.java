package br.puc.ronie.dbadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<ContatoModel> listaContatos;

    ListView listContatos;
    TextView textoContatosInexistentes;
    TextView textoEditar;

    ContatoDBAdapter db;

    Button buttonCriarContato;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        buttonCriarContato = (Button) findViewById(R.id.buttonCriarContato);
        listContatos = (ListView) findViewById(R.id.listContatos);
        textoContatosInexistentes = (TextView) findViewById(R.id.text_contatos_inexistentes);
        textoEditar = (TextView) findViewById(R.id.text_editar);

        buttonCriarContato.setOnClickListener(this);
        listContatos.setOnItemClickListener(this);

        db = new ContatoDBAdapter(this);
        this.listarContatos();
    }

    /**
     *
     */
    public void listarContatos() {
        listaContatos = db.getAllContatosModel();

        ContatoAdapter adapter = new ContatoAdapter(this, 0, listaContatos);
        listContatos.setAdapter(adapter);

        if (listaContatos.size() == 0) {
            listContatos.setVisibility(View.GONE);
            textoEditar.setVisibility(View.GONE);
            textoContatosInexistentes.setVisibility(View.VISIBLE);
        } else {
            listContatos.setVisibility(View.VISIBLE);
            textoEditar.setVisibility(View.VISIBLE);
            textoContatosInexistentes.setVisibility(View.GONE);
        }
    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.equals(buttonCriarContato)) {
            Intent intent = new Intent(this, CadastroActivity.class);
            intent.putExtra("contato", new ContatoModel());
            startActivityForResult(intent, 1);
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.listarContatos();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContatoModel contatoModel = listaContatos.get(position);
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra("contato", contatoModel);
        startActivityForResult(intent, 2);
    }

}
