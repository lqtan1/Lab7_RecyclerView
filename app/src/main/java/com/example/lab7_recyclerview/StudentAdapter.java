package com.example.lab7_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private final OnStudentActionListener listener;

    public interface OnStudentActionListener {
        void onEditClick(Student student);
        void onDeleteClick(Student student);
    }

    public StudentAdapter(List<Student> studentList, OnStudentActionListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail;
        ImageButton btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvName.setText(student.getName());
        holder.tvEmail.setText(student.getEmail());

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(student));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(student));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateData(List<Student> newList) {
        this.studentList = newList;
        notifyDataSetChanged();
    }
}
