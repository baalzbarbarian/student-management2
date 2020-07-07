package com.example.ass_quanlysinhvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.ass_quanlysinhvien.Adapter.StudentsAdapter;
import com.example.ass_quanlysinhvien.DataBase.Database;
import com.example.ass_quanlysinhvien.Model.mStudents;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainStudentsManagerActivity extends AppCompatActivity {

    private Spinner spClass;
    private Database database;
    private List<String> classList;
    private StudentsAdapter studentsAdapter;
    private List<mStudents> mStudentsList;
    private ListView lvStudents;
    Button btnAddStudent, btnDate;
    EditText edtName, edtBirth;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_manager);

        edtName = findViewById(R.id.edtName);
        edtBirth = findViewById(R.id.edtBirth);
        lvStudents = findViewById(R.id.lv_students);
        btnDate = findViewById(R.id.btnDate);

        CreateSpinner();

        //Add Student
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = spClass.getSelectedItem().toString();
                String stName = edtName.getText().toString();
                String stBorn = edtBirth.getText().toString();
                mStudents mStudents = new mStudents(code, stName, stBorn);

                if (mStudents != null){
                    if (database.addStudent(mStudents) > 0){
                        studentsAdapter = new StudentsAdapter(
                                MainStudentsManagerActivity.this,
                                database.getStudentsByCode2(code));
                        Toast.makeText(getApplicationContext(), "Add Student Successfully", Toast.LENGTH_SHORT).show();
                        lvStudents.setAdapter(studentsAdapter);
                    }else {
                        studentsAdapter.notifyDataSetChanged();
                        lvStudents.setSelection(studentsAdapter.getCount() - 1);
                    }
                }

                edtName.getText().clear();
                edtBirth.getText().clear();
            }
        });

        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainStudentsManagerActivity.this);
                builder.setMessage("Do you want remove this Student?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(position);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = spClass.getSelectedItem().toString();
                lvStudents = findViewById(R.id.lv_students);
                database = new Database(MainStudentsManagerActivity.this);
                mStudentsList = database.getStudentsByCode2(code);
                studentsAdapter = new StudentsAdapter(MainStudentsManagerActivity.this, mStudentsList);
                lvStudents.setAdapter(studentsAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDateDialog();
            }
        });

    }



    //Get date/month/year
    private void PickDateDialog()
    {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirth.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    //Set spinner
    public void CreateSpinner(){
        spClass = findViewById(R.id.classSpinner);
        database = new Database(this);
        classList = database.getClassByCode();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                classList
        );
        spClass.setAdapter(spinnerAdapter);
        spClass.setPrompt("Lớp");
    }


    private void deleteItem(int i){
        mStudents mStudents = mStudentsList.get(i);
        int result = database.delStudent(mStudents);
        if (result > 0){
            mStudentsList.clear();
            mStudentsList.addAll(database.getStudentsByCode2(code));
            studentsAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),
                    "DELETE SUCCESSFULLY",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(),
                    "DELETE FAIL",Toast.LENGTH_SHORT).show();
        }
    }



}
