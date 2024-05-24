package com.example.lab3_2_3_v2;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> studentList;
    private Context context;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvStudentName.setText(student.getName());
        holder.tvStudentId.setText("Mã số sinh viên: " + student.getMssv());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvStudentName, tvStudentId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Student student = studentList.get(position);
            showStudentDetails(student);
        }
    }
    private void showStudentDetails(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Student Details");

        // Set up the input fields
        final EditText inputName = new EditText(context);
        inputName.setText(student.getName());
        final EditText inputMssv = new EditText(context);
        inputMssv.setText(student.getMssv());
        final EditText inputClassName = new EditText(context);
        inputClassName.setText(student.getClassName());

        // Set up the layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputMssv);
        layout.addView(inputClassName);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = inputName.getText().toString();
                String mssv = inputMssv.getText().toString();
                String className = inputClassName.getText().toString();

                // Update the student's details and update the database
                student.setName(name);
                student.setMssv(mssv);
                student.setClassName(className);
                ((MainActivity) context).updateStudent(student);

                // Update the RecyclerView
                studentList = ((MainActivity) context).getAllStudents();
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the student from the database and update the RecyclerView
                ((MainActivity) context).deleteStudent(student);
                studentList.remove(student);
                notifyDataSetChanged();
            }
        });

        // Show the dialog
        builder.show();
    }}