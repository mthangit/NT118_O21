package com.example.lab3_3;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);
        dbHelper.copyDatabaseFromAssets();

        adapter = new StudentAdapter(this, dbHelper.getAllStudents(), new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student) {
                showEditStudentDialog(student);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddStudentDialog();
            }
        });
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Student");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
        final EditText inputName = dialogView.findViewById(R.id.editTextName);
        final EditText inputMssv = dialogView.findViewById(R.id.editTextMssv);
        final EditText inputClassName = dialogView.findViewById(R.id.editTextClass);

        builder.setView(dialogView);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = inputName.getText().toString();
                String mssv = inputMssv.getText().toString();
                String className = inputClassName.getText().toString();

                if (name.isEmpty() || mssv.isEmpty() || className.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.addStudent(new Student(mssv, name, className));
                adapter.setStudentList(dbHelper.getAllStudents());
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void showEditStudentDialog(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Student");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null);
        final EditText inputName = dialogView.findViewById(R.id.editTextName);
        final EditText inputMssv = dialogView.findViewById(R.id.editTextMssv);
        final EditText inputClassName = dialogView.findViewById(R.id.editTextClass);

        inputName.setText(student.getName());
        inputMssv.setText(student.getMssv());
        inputClassName.setText(student.getClassName());

        builder.setView(dialogView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = inputName.getText().toString();
                String mssv = inputMssv.getText().toString();
                String className = inputClassName.getText().toString();

                if (name.isEmpty() || mssv.isEmpty() || className.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                student.setName(name);
                student.setMssv(mssv);
                student.setClassName(className);
                System.out.println("TEsting");
                dbHelper.updateStudent(student);
                System.out.println("TEsting 2");
                adapter.setStudentList(dbHelper.getAllStudents());
                System.out.println("TEsting 3");
                adapter.notifyDataSetChanged();
                System.out.println("TEsting 4");
                System.out.println("Updated student: " + student.getName() + " " + student.getMssv() + " " + student.getClassName());
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteStudent(student);
                adapter.setStudentList(dbHelper.getAllStudents());
                adapter.notifyDataSetChanged();
            }
        });
        refreshList();
        builder.create().show();
    }
    // refresh the list of students when the activity is resumed
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        adapter.setStudentList(dbHelper.getAllStudents());
        adapter.notifyDataSetChanged();
    }

    // refresh the recycle view them press save or delete button in dialog
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.setStudentList(dbHelper.getAllStudents());
        adapter.notifyDataSetChanged();
    }

    // clear the list and refresh the recycle view
    @SuppressLint("NotifyDataSetChanged")
    private void refreshList() {
        adapter.setStudentList(dbHelper.getAllStudents());
        adapter.notifyDataSetChanged();
    }
}
