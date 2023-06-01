package mz.ac.isutc.lecc.mt2.testeapppdf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import mz.ac.isutc.lecc.mt2.testeapppdf.models.FileModel;

public class Myadapter extends FirebaseRecyclerAdapter<FileModel, Myadapter.myviewholder> {

    StorageReference storageReference;

    DatabaseReference databaseReference;

    private String id;

    public Myadapter(@NonNull FirebaseRecyclerOptions<FileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull FileModel model) {

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        id = model.getId();

        holder.titulo.setText(model.getFileName());
        holder.disciplina.setText(model.getDisciplina());
        holder.username.setText(model.getUsername());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imagem.getContext(), ViewpdfActivity.class);
                intent.putExtra("filename",model.getFileName());
                intent.putExtra("fileurl",model.getFileUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.imagem.getContext().startActivity(intent);

            }
        });
        
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public boolean onLongClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetStyle);
                View sheetView = LayoutInflater.from(view.getContext())
                        .inflate(R.layout.bottomdialog, (LinearLayout)bottomSheetDialog.findViewById(R.id.dialog_container));
                sheetView.findViewById(R.id.confirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MainActivity.root){
                            if (model.getUsername().equals(MainActivity.controle.getUserName())){
                                databaseReference.child("MyDocments").child(id).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        storageReference.child("uploads/"+id+".pdf").delete();
                                        Toast.makeText(view.getContext(), "Apagado com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(view.getContext(), "Não tem permissão para apagar esse ficheiro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();

                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_row,parent,false);

        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView imagem;
        TextView titulo, disciplina, username;
        LinearLayout layout;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            imagem = itemView.findViewById(R.id.imagem);
            layout = itemView.findViewById(R.id.row);
            disciplina = itemView.findViewById(R.id.disciplina);
            username = itemView.findViewById(R.id.username);
        }
    }
}
