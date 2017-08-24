package br.edu.infnet.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private View btnLogin;
    private View btnSignUp;
    private ProgressDialog progressDialog;
    private TextInputLayout email;
    private TextInputLayout password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        //Pegamos a instancia do Firebase
        auth = FirebaseAuth.getInstance();

        //variaveis de layout
        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.sign_up);
        email = (TextInputLayout) findViewById(R.id.email_field);
        password = (TextInputLayout) findViewById(R.id.password_field);

        //futura tela de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //event para o botao de cadastro
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.temTexto(email)) {
                    Utils.mostraToast(RegisterActivity.this, "Entre com seu email");
                } else if (!Utils.temTexto(password)) {
                    Utils.mostraToast(RegisterActivity.this, "Entre com sua senha");
                } else {
                    //indo para o firebase
                    showProcessDialog();
                    auth.createUserWithEmailAndPassword(Utils.pegaTexto(email), Utils.pegaTexto(password))
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Utils.mostraToast(RegisterActivity.this, "Erro no cadastro!");
                                    } else {
                                        Utils.mostraToast(RegisterActivity.this, "Cadastrado com sucesso!");
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Cadastro");
        progressDialog.setMessage("Cadastre uma nova conta...");
        progressDialog.show();
    }
}