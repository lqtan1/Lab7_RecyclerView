package com.example.lab7_recyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAdd: Button
    
    private var selectedStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)

        setupRecyclerView()
        
        // Initial data if empty
        if (dbHelper.getAllStudents().isEmpty()) {
            for (i in 1..10) {
                dbHelper.addStudent(Student(name = "Student $i", email = "student$i@example.com"))
            }
            refreshData()
        }

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                if (selectedStudent == null) {
                    // Create
                    dbHelper.addStudent(Student(name = name, email = email))
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Update
                    val studentToUpdate = selectedStudent!!
                    studentToUpdate.name = name
                    studentToUpdate.email = email
                    dbHelper.updateStudent(studentToUpdate)
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    selectedStudent = null
                    btnAdd.text = "Add Student"
                }
                clearInputs()
                refreshData()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            dbHelper.getAllStudents(),
            onEditClick = { student ->
                selectedStudent = student
                etName.setText(student.name)
                etEmail.setText(student.email)
                btnAdd.text = "Update Student"
            },
            onDeleteClick = { student ->
                dbHelper.deleteStudent(student.id)
                refreshData()
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun refreshData() {
        adapter.updateData(dbHelper.getAllStudents())
    }

    private fun clearInputs() {
        etName.text.clear()
        etEmail.text.clear()
    }
}
