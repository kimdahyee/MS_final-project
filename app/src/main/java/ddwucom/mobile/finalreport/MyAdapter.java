package ddwucom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context; //inflater 객체 생성 시 필요
    private int layout; //AdapterView 항목에 대한 layout
    private ArrayList<Movie> myDataList; //원본 데이터 리스트
    private LayoutInflater layoutInflater; //inflater 객체

    public MyAdapter(Context context, int layout, ArrayList<Movie> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView textVIewNo;
        ImageView imageView;
        TextView textVIewTitle;
        TextView textVIewDir;
        TextView textVIewDay;
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return myDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myDataList.get(position).get_id();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        ViewHolder holder;

        if (view == null) { //이미 만들어진 것 재사용하겠다는 의미
            view  = layoutInflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView)view.findViewById(R.id.imageView);
            holder.textVIewNo = (TextView)view.findViewById(R.id.textViewNo);
            holder.textVIewTitle = (TextView)view.findViewById(R.id.textViewTitle);
            holder.textVIewDir = (TextView)view.findViewById(R.id.textViewDir);
            holder.textVIewDay = (TextView)view.findViewById(R.id.textViewDay);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }

        holder.textVIewNo.setText(String.valueOf(myDataList.get(pos).get_id()));

        if (myDataList.get(pos).get_id() % 5 == 1) {
            holder.imageView.setImageResource(R.mipmap.aladin);
        }
        else if (myDataList.get(pos).get_id() % 5 == 2) {
            holder.imageView.setImageResource(R.mipmap.toy);
        }
        else if (myDataList.get(pos).get_id() % 5 == 3) {
            holder.imageView.setImageResource(R.mipmap.gi);
        }
        else if (myDataList.get(pos).get_id() % 5 == 4) {
            holder.imageView.setImageResource(R.mipmap.man);
        }
        else {
            holder.imageView.setImageResource(R.mipmap.satan);
        }


        holder.textVIewTitle.setText(myDataList.get(pos).getTitle());
        holder.textVIewDir.setText(myDataList.get(pos).getDirector());
        holder.textVIewDay.setText(myDataList.get(pos).getDay());

        return view;
    }
}
