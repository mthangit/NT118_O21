package com.example.lab3_2_3_v2;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        studentList = databaseHelper.getAllStudents();
        adapter = new StudentAdapter(this, studentList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddStudentDialog();
            }
        });
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a student");

        // Set up the input fields
        final EditText inputName = new EditText(this);
        inputName.setHint("Tên");
        final EditText inputMssv = new EditText(this);
        inputMssv.setHint("Mã số sinh viên");
        final EditText inputClassName = new EditText(this);
        inputClassName.setHint("Lớp");

        // Set up the layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputMssv);
        layout.addView(inputClassName);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = inputName.getText().toString();
                String mssv = inputMssv.getText().toString();
                String className = inputClassName.getText().toString();

                // Create a new student and add it to the database
                Student student = new Student(name, mssv, className);
                databaseHelper.addStudent(student);

                // Update the RecyclerView
                studentList = databaseHelper.getAllStudents();
                adapter = new StudentAdapter(MainActivity.this, studentList);
                recyclerView.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("Thoát", null);

        // Show the dialog
        builder.show();
    }
    public void updateStudent(Student student) {
        // Update the student in the database and update the RecyclerView
        databaseHelper.updateStudent(student);
        studentList = databaseHelper.getAllStudents();
        adapter.notifyDataSetChanged();
    }
    public void deleteStudent(Student student) {
        // Delete the student from the database and update the RecyclerView
        databaseHelper.deleteStudent(student.getId());
        studentList.remove(student);
        adapter.notifyDataSetChanged();
    }
    public List<Student> getAllStudents() {
        return studentList;
    }
}