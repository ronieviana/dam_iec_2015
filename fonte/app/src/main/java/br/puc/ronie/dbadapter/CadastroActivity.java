package br.puc.ronie.dbadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    ContatoDBAdapter db;

    Button buttonSalvar;
    EditText editNome;
    EditText editEMail;

    ContatoModel contato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        editNome = (EditText) findViewById(R.id.edit_nome);
        editEMail = (EditText) findViewById(R.id.edit_email);

        buttonSalvar.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        if (params.size() > 0) {
            contato = (ContatoModel) params.getSerializable("contato");
        }

        if (contato == null) {
            contato = new ContatoModel();
        }
        {
            editNome.setText(contato.getNome());
            editEMail.setText(contato.getEmail());
        }

        db = new ContatoDBAdapter(this);
    }


    private void limpar() {
        editEMail.setText("");
        editNome.setText("");
    }


    private boolean validarContato() {
        if (editNome.getText().toString().isEmpty()
                || editEMail.getText().toString().isEmpty()
                || !editEMail.getText().toString().contains("@")) {

            Toast.makeText(this, "Dados inválidos!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(buttonSalvar)) {
            if (validarContato()) {
                contato.setNome(editNome.getText().toString());
                contato.setEmail(editEMail.getText().toString());

                if (contato.getId() == 0) {
                    db.insertContatoModel(contato);
                } else {
                    db.updateContatoModel(contato);
                }

                this.setResult(1);
                this.finish();
            }
        }

    }
}
