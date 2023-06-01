package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
     public static UsuarioPofessor controle;

     private Usuario usuario;
    private DatabaseReference databaseReference;

    public static Boolean root; // Varialvel que controla o nivel de acesso

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
                        root = true;
                    }catch (NullPointerException e){
                        usuario = (Usuario) controle;
                        root = false;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        if (id == R.id.sobre){
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetStyle);
            View sheetView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sobre_o_projecto, null);
            bottomSheetDialog.setContentView(sheetView);

            LinearLayout dialogContainerInformacoes = sheetView.findViewById(R.id.dialog_container_informacoes);
            bottomSheetDialog.show();
        }

        if (id == R.id.perfil){
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetStyle);
            View sheetView = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottomdialog_perfil, null);
            bottomSheetDialog.setContentView(sheetView);

            @SuppressLint("MissingInflatedId")
            LinearLayout dialogContainerInformacoes = sheetView.findViewById(R.id.dialog_container_perfil);
            TextView username = sheetView.findViewById(R.id.username);
            @SuppressLint("MissingInflatedId")
            TextView email = sheetView.findViewById(R.id.email);
            @SuppressLint("MissingInflatedId") TextView diciplina = sheetView.findViewById(R.id.disciplina);
            @SuppressLint("MissingInflatedId") TextView telefone = sheetView.findViewById(R.id.telefone);
            if (root){
                sheetView.findViewById(R.id.layout_disciplina).setVisibility(View.VISIBLE);
                sheetView.findViewById(R.id.layout_telefone).setVisibility(View.VISIBLE);
                username.setText(controle.getUserName());
                email.setText(controle.getEmail());
                diciplina.setText(controle.getDisciplina());
                telefone.setText(controle.getTelefone());
            }else{
                username.setText(usuario.getUserName());
                email.setText(usuario.getEmail());
            }
            bottomSheetDialog.show();
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