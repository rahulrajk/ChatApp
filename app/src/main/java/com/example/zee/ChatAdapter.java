package com.example.zee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> message = new ArrayList<>();

    void setList(String n, String m){
        this.name.add(n);
        this.message.add(m);
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_chat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.name.setText(name.get(position));
        holder.message.setText(message.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,message;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);

        }
    }
}
