package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mz.ac.isutc.lecc.mt2.testeapppdf.databinding.ActivitySignUpBinding;
import mz.ac.isutc.lecc.mt2.testeapppdf.models.Usuario;
import mz.ac.isutc.lecc.mt2.testeapppdf.models.UsuarioPofessor;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    DatabaseReference databaseReference;

    private String email, password, username, disciplina, numero;

    private Boolean tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.username.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();

                if (binding.disciplina.getVisibility() == View.VISIBLE){
                    disciplina = binding.disciplina.getText().toString();
                    numero = binding.telefone.getText().toString();

                    if(email.trim().equals("") || password.equals("") || username.trim().equals("") || disciplina.trim().equals("") || numero.trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Nao deixe nada em branco", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    signUp(true); //true se for um professor
                }else{
                    if(email.trim().equals("") || password.equals("") || username.trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Nao deixe nada em branco", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    signUp(false); //true se for um aluno
                }

                // startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        binding.tipoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.nao.getVisibility() == View.VISIBLE){
                    binding.nao.setVisibility(View.GONE);
                    binding.sim.setVisibility(View.VISIBLE);

                    binding.disciplina.setVisibility(View.VISIBLE);
                    binding.telefone.setVisibility(View.VISIBLE);

                }else {
                    binding.nao.setVisibility(View.VISIBLE);
                    binding.sim.setVisibility(View.GONE);

                    binding.disciplina.setVisibility(View.GONE);
                    binding.telefone.setVisibility(View.GONE);
                }
            }
        });
    }

    private void signUp(boolean tip) {
        Log.d("testes","Antes do FirebaseAuth");
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(),password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("testes","public void onSuccess");
                        if (tip){
                            Toast.makeText(SignUpActivity.this, "Registrado como professor", Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest);
                            UsuarioPofessor usuarioPofessor = new UsuarioPofessor(FirebaseAuth.getInstance().getUid(), username, email, password, disciplina, numero);
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(usuarioPofessor);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Registrado como aluno", Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest);
                            Usuario usuario = new Usuario(FirebaseAuth.getInstance().getUid(), username, email, password);
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(usuario);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("testes", e.toString());
                    }
                });
    }

}