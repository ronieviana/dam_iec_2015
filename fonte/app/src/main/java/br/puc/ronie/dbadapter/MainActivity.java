package br.puc.ronie.dbadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonEntrar;

    EditText editUsuario;
    EditText editSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        editUsuario = (EditText) findViewById(R.id.edit_usuario);
        editSenha = (EditText) findViewById(R.id.edit_senha);

        buttonEntrar.setOnClickListener(this);
    }


    private boolean validarUsuario() {

        if (!editUsuario.getText().toString().equals("usuario")
                || !editSenha.getText().toString().equals("senha")) {

            Toast.makeText(this, "Usuário inválido!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(buttonEntrar)) {
            if (validarUsuario()) {
                Intent intent = new Intent(this, ListaActivity.class);
                startActivity(intent);
            }
        }
    }
}
