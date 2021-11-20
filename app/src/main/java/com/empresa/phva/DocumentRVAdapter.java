package com.empresa.phva;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empresa.phva.db.Document;

import java.util.ArrayList;



public class DocumentRVAdapter extends RecyclerView.Adapter<DocumentRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Document> documentArrayList;
    private Context context;
    private View view;

    // constructor
    public DocumentRVAdapter(ArrayList<Document> documentArrayList, Context context) {
        this.documentArrayList = documentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_document_item, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        Document modal = documentArrayList.get(position);
        holder.tvDocumentType.setText(modal.getTipoDoc());
        holder.tvDocumentUrl.setText(modal.getUrl());
        holder.tvDocumentDescription.setText(modal.getDescription());
        holder.tvDocumentStatus.setText(modal.getEstado());

        Log.e("ErrorFG", "onBindViewHolder: " + holder.tvDocumentType.getText().toString() );

        if (holder.tvDocumentType.getText().toString().equals("Tipo1")  ){
            holder.lyPpal.setBackgroundColor(Color.GREEN);
        }else{
            holder.lyPpal.setBackgroundColor(Color.RED);
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Mensaje: "+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return documentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tvDocumentType, tvDocumentUrl, tvDocumentDescription, tvDocumentStatus;
        private LinearLayout lyPpal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tvDocumentType = itemView.findViewById(R.id.tvDocumentType);
            tvDocumentUrl = itemView.findViewById(R.id.tvDocumentUrl);
            tvDocumentDescription = itemView.findViewById(R.id.tvDocumentDescription);
            tvDocumentStatus = itemView.findViewById(R.id.tvDocumentStatus);

            lyPpal = itemView.findViewById(R.id.lyPpal);


        }
    }
}

