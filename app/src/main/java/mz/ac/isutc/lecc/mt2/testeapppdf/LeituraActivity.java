package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import mz.ac.isutc.lecc.mt2.testeapppdf.databinding.ActivityLeituraBinding;

public class LeituraActivity extends AppCompatActivity {

    private ActivityLeituraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeituraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}