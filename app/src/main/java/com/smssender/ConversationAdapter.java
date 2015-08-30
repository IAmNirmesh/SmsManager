package com.smssender;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import io.realm.RealmResults;

public class ConversationAdapter extends ArrayAdapter<SmsDataModel> {

    private RealmResults<SmsDataModel> data;
    private Context mContext;

    public ConversationAdapter(Context context, RealmResults<SmsDataModel> data) {
        super(context, R.layout.conversation_item, data);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public SmsDataModel getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        SmsSenderApplication.getRealmDb().beginTransaction();
        data.get(position).removeFromRealm();
        SmsSenderApplication.getRealmDb().commitTransaction();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.conversation_item, parent, false);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.smsMessage = (TextView) convertView.findViewById(R.id.message);
            holder.dateTime = (TextView) convertView.findViewById(R.id.dateTime);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SmsDataModel _objModel = data.get(position);

        if(!FileUtils.contactExists(mContext, _objModel.getNumber()))
            convertView.setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.unknown_bg_color)));
        else
            convertView.setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.known_bg_color)));

        if(_objModel.isRead())
            setNormalFont(holder);

        holder.number.setText(_objModel.getNumber());
        holder.smsMessage.setText(_objModel.getText());
        holder.dateTime.setText(_objModel.getDateTime());

        return convertView;
    }

    private void setNormalFont(ViewHolder holder) {
        holder.number.setTypeface(null, Typeface.NORMAL);
        holder.smsMessage.setTypeface(null, Typeface.NORMAL);
        holder.dateTime.setTypeface(null, Typeface.NORMAL);
    }

    class ViewHolder {
        TextView number;
        TextView smsMessage;
        TextView dateTime;
    }
}
