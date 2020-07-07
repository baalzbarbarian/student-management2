package com.example.ass_quanlysinhvien.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ass_quanlysinhvien.Model.mStudents;
import com.example.ass_quanlysinhvien.R;

import java.util.List;

public class StudentsAdapter extends BaseAdapter {
    Activity context;
    List<mStudents>sinhVienList;

    public StudentsAdapter(Activity context, List<mStudents> sinhVienList) {
        super();
        this.context = context;
        this.sinhVienList = sinhVienList;

    }
    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return sinhVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) (context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.students_list_custom, null);
            viewHolder.id = convertView.findViewById(R.id.txt1);
            viewHolder.name = convertView.findViewById(R.id.txt2);
            viewHolder.born = convertView.findViewById(R.id.txt3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mStudents mStudents = sinhVienList.get(position);
        viewHolder.id.setText(String.valueOf(position+1));
        viewHolder.name.setText(mStudents.getStudentName());
        viewHolder.born.setText(mStudents.getStudentBirthday());
        return convertView;
    }
    public class ViewHolder {
        TextView id, name, born;
    }
}
