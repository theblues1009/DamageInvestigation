package jp.techacademy.sayoko.kobayashi.damageinvestigation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvestigationAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
    private List<Investigation> mInvestigationList;

    public InvestigationAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setInvestigationList(List<Investigation> investigationList) {
        mInvestigationList = investigationList;
    }

    @Override
    public int getCount() {
        return mInvestigationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvestigationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mInvestigationList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(jp.techacademy.sayoko.kobayashi.damageinvestigation.R.layout.list_item, null);

        }

        TextView textView1 = (TextView) convertView.findViewById(R.id.text1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.text2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.text3);
        Date date = mInvestigationList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE);
        String dateStr = sdf.format(date);
        textView1.setText(mInvestigationList.get(position).getRinpan() + "林班" + mInvestigationList.get(position).getSyohan() + "小班");


        textView2.setText("被害率：" + mInvestigationList.get(position).getRateOfInvestigation() + "%");
        textView3.setText("調査日時：" + dateStr);

        return convertView;
    }
}


