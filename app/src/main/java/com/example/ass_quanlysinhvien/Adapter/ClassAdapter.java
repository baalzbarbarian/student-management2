package com.example.ass_quanlysinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ass_quanlysinhvien.Model.mClass;
import com.example.ass_quanlysinhvien.R;

import java.util.List;

public class ClassAdapter extends BaseAdapter {

    private Context context;
    private List<mClass> ClassList;

    public ClassAdapter(Context context, List<mClass> objects) {
        super();
        this.context = context;
        this.ClassList = objects;
    }

    @Override
    public int getCount() {
        return ClassList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.class_list_custom,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.tv_clId = convertView.findViewById(R.id.txt1);
            viewHolder.tv_clName = convertView.findViewById(R.id.txt2);
            viewHolder.tv_clCode = convertView.findViewById(R.id.txt3);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mClass mClass = ClassList.get(position);
        viewHolder.tv_clId.setText(String.valueOf(position+1));
        viewHolder.tv_clName.setText(mClass.getField2());
        viewHolder.tv_clCode.setText(mClass.getField3());

        return convertView;
    }


    public class ViewHolder{
        private TextView tv_clId;
        private TextView tv_clName;
        private TextView tv_clCode;
    }
}
