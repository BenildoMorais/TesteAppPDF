package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mz.ac.isutc.lecc.mt2.testeapppdf.databinding.ActivityMainBinding;
import mz.ac.isutc.lecc.mt2.testeapppdf.models.FileModel;
import mz.ac.isutc.lecc.mt2.testeapppdf.models.Usuario;
import mz.ac.isutc.lecc.mt2.testeapppdf.models.UsuarioPofessor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
     private Myadapter adapter;
     private UsuarioPofessor controle;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference();

        verification();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.AdicionarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                intent.putExtra("disciplina", controle.getDisciplina());
                intent.putExtra("telefone", controle.getTelefone());
                intent.putExtra("username", controle.getUserName());
                startActivity(intent);
            }
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FileModel> options = new FirebaseRecyclerOptions.Builder<FileModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("MyDocments"),FileModel.class)
                .build();

        adapter = new Myadapter(options);
        binding.recyclerView.setAdapter(adapter);
    }

    private void verification() {
        databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    controle = task.getResult().getValue(UsuarioPofessor.class);
                    try {
                        controle.getDisciplina().equals(""); // controla o tipo de usuario e o tipo de privilegios
                        binding.AdicionarPDF.setVisibility(View.VISIBLE);
                    }catch (NullPointerException e){
                        Usuario usuario = (Usuario) controle;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}