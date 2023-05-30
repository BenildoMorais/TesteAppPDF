package mz.ac.isutc.lecc.mt2.testeapppdf;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import mz.ac.isutc.lecc.mt2.testeapppdf.models.FileModel;

public class Myadapter extends FirebaseRecyclerAdapter<FileModel, Myadapter.myviewholder> {

    public Myadapter(@NonNull FirebaseRecyclerOptions<FileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull FileModel model) {
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
