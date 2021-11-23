package com.uber.leandrolara.cursoandroid.uber.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uber.leandrolara.cursoandroid.uber.R;
import com.uber.leandrolara.cursoandroid.uber.config.ConfiguracaoFirebase;
import com.uber.leandrolara.cursoandroid.uber.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Switch switchTipoUsuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicializar componentes
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);
    }

    public void validarCadastroUsuario(View view) {
        //Recuperar textos dos campos
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoNome.isEmpty() ) {//Verifica nome
            if ( !textoEmail.isEmpty() ) {//verifica e-mail
                if ( !textoSenha.isEmpty() ) {//verifica senha

                    Usuario usuario = new Usuario();
                    usuario.setNome( textoNome );
                    usuario.setEmail( textoEmail );
                    usuario.setSenha( textoSenha );
                    usuario.setTipo( verificaTipoUsuario() );

                    cadastrarUsuario( usuario );

                }else {
                    Toast.makeText( CadastroActivity.this,
                            "Preencha a senha!!",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText( CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
        }

    }

    public void cadastrarUsuario ( Usuario usuario ){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ) {
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar Usuário!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String verificaTipoUsuario() {
        return switchTipoUsuario.isChecked() ? "M" : "P";


    }
}