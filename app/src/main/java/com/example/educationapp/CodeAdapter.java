package com.example.educationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder> {

    private final List<String> sourceList;
    private final List<String> targetList;
    private final Runnable onDataChanged;

    public CodeAdapter(List<String> sourceList, List<String> targetList, Runnable onDataChanged) {
        this.sourceList = sourceList;
        this.targetList = targetList;
        this.onDataChanged = onDataChanged;
    }

    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        String codeLine = sourceList.get(position);
        holder.textView.setText(codeLine);

        // Обработка нажатий на элемент
        holder.itemView.setOnClickListener(view -> {
            String item = sourceList.remove(position); // Удаляем элемент из текущего списка
            targetList.add(item); // Добавляем его в целевой список
            onDataChanged.run(); // Уведомляем адаптеры об изменении данных
        });
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    static class CodeViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        CodeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
