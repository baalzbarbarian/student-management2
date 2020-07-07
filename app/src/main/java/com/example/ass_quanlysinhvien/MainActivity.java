package com.example.ass_quanlysinhvien;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ass_quanlysinhvien.DataBase.Database;


public class MainActivity extends Activity {

    private EditText edtName, edtCode;
    private Button btnClear, btnAdd, btnAddClass, btnList, btnStudents, btnLogout, btnExit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddClass = findViewById(R.id.btnAddClass);
        btnList = findViewById(R.id.btnList);
        btnStudents = findViewById(R.id.btnStudents);
        btnLogout = findViewById(R.id.btnLogOut);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, MainStudentsManagerActivity.class);
                startActivity(intent);
            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, MainClassManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_class);
        dialog.show();

        edtName = dialog.findViewById(R.id.edtName);
        edtCode = dialog.findViewById(R.id.edtCode);
        btnAdd = dialog.findViewById(R.id.btnAdd);
        btnClear = dialog.findViewById(R.id.btnClear);
        btnExit = dialog.findViewById(R.id.btnExit);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String code = edtCode.getText().toString();

                if (!name.isEmpty() && !code.isEmpty()){
                    Database db = new Database(MainActivity.this);
                    db.addFpolyClass(code, name);

                    edtName.getText().clear();
                    edtCode.getText().clear();

                    Toast.makeText(getApplicationContext(), "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.getText().clear();
                edtCode.getText().clear();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

}
