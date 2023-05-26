package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import mz.ac.isutc.lecc.mt2.testeapppdf.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
}