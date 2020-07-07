package com.example.ass_quanlysinhvien;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.ass_quanlysinhvien.Adapter.ClassAdapter;
import com.example.ass_quanlysinhvien.DataBase.Database;
import com.example.ass_quanlysinhvien.Model.mClass;

import java.util.List;

public class MainClassManagerActivity extends Activity {

    ListView lv_class;
    private Database database;
    private List<mClass> classList;
    private mClass mClass;
    private ClassAdapter adapter = null;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class_list);

        lv_class = findViewById(R.id.lv_class);
        database = new Database(this);
        classList = database.getAllClass();
        adapter = new ClassAdapter(MainClassManagerActivity.this, classList);
        lv_class.setAdapter(adapter);

        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                Update();
            }
        });

    }

    

    public void Update(){
        final Dialog dialog = new Dialog(MainClassManagerActivity.this);
        dialog.setContentView(R.layout.dialog_edit_class);
        dialog.show();

        final EditText updateId = dialog.findViewById(R.id.updateId);
        final EditText updateName = dialog.findViewById(R.id.updateName);
        final EditText updateCode = dialog.findViewById(R.id.updateCode);

        Button btnCLear = dialog.findViewById(R.id.btnClear);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);

        mClass = classList.get(pos);
        updateId.setText(String.valueOf(mClass.getField1()));
        updateName.setText(mClass.getField2());
        updateCode.setText(mClass.getField3());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClass.setField1(Integer.parseInt(String.valueOf(updateId.getText())));
                mClass.setField2(updateName.getText()+"");
                mClass.setField3(updateCode.getText()+"");

                int result = database.updateClass(mClass);
                if (result > 0){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Update Successfully", Toast.LENGTH_SHORT).show();
                }

                updateName.getText().clear();
                updateCode.getText().clear();

                dialog.dismiss();
                dialog.cancel();
            }
        });

        btnCLear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName.getText().clear();
                updateCode.getText().clear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainClassManagerActivity.this);
                builder.setMessage("Do you want remove this class?");
                builder.setCancelable(false);

                //Say Yes =))
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(pos);
                    }
                });

                //Say No
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                dialog.dismiss();

            }
        });
    }

    private void deleteItem(int i){
        mClass mClass = classList.get(i);
        int result = database.delClass(mClass.getField1());
        if (result > 0){
            classList.clear();
            classList.addAll(database.getAllClass());
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"DELETE SUCCESSFULLY",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(),"DELETE FAIL",Toast.LENGTH_SHORT).show();
        }
    }

}
