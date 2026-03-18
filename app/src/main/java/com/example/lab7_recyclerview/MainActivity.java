package com.example.lab7_recyclerview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private StudentAdapter adapter;
    private RecyclerView recyclerView;
    private EditText etName;
    private EditText etEmail;
    private Button btnAdd;

    private Student selectedStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        setupRecyclerView();

        // Initial data if empty
        if (dbHelper.getAllStudents().isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                dbHelper.addStudent(new Student("Student " + i, "student" + i + "@example.com"));
            }
            refreshData();
        }

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();

            if (!name.isEmpty() && !email.isEmpty()) {
                if (selectedStudent == null) {
                    // Create
                    dbHelper.addStudent(new Student(name, email));
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Update
                    selectedStudent.setName(name);
                    selectedStudent.setEmail(email);
                    dbHelper.updateStudent(selectedStudent);
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    selectedStudent = null;
                    btnAdd.setText("Add Student");
                }
                clearInputs();
                refreshData();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new StudentAdapter(
                dbHelper.getAllStudents(),
                new StudentAdapter.OnStudentActionListener() {
                    @Override
                    public void onEditClick(Student student) {
                        selectedStudent = student;
                        etName.setText(student.getName());
                        etEmail.setText(student.getEmail());
                        btnAdd.setText("Update Student");
                    }

                    @Override
                    public void onDeleteClick(Student student) {
                        dbHelper.deleteStudent(student.getId());
                        refreshData();
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void refreshData() {
        adapter.updateData(dbHelper.getAllStudents());
    }

    private void clearInputs() {
        etName.getText().clear();
        etEmail.getText().clear();
    }
}
