package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.MedicalRecord;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Adapter which manages the Medical Record shown on its lists
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 * @see MedicalRecord
 */
public class MedicalRecord_Adapter extends ArrayAdapter<MedicalRecord> {
    private Context context;

    public MedicalRecord_Adapter(Context context) {
        super(context, R.layout.item_record, Repository.getInstance().getRecords());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        RecordHolder holder;

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            item = inflater.inflate(R.layout.item_record, null);
            holder = new RecordHolder();

            holder.cbxHospitalised = (CheckBox) item.findViewById(R.id.cbxHospitalised);
            holder.txvAntecents = (TextView) item.findViewById(R.id.txvAntecedents);
            holder.txvDate = (TextView) item.findViewById(R.id.txvDate);
            holder.txvReason = (TextView) item.findViewById(R.id.txvReason);

            item.setTag(holder);
        } else {
            holder = (RecordHolder) item.getTag();
        }
        MedicalRecord currentRecord = getItem(position);

        holder.txvReason.setText(currentRecord.getReason());
        holder.txvDate.setText(currentRecord.getFormattedDate());
        holder.txvAntecents.setText(currentRecord.getAntecedents());
        holder.cbxHospitalised.setChecked(currentRecord.isHospitalised());
        holder.cbxHospitalised.setEnabled(false);

        return item;
    }

    class RecordHolder {
        TextView txvReason, txvAntecents, txvDate;
        CheckBox cbxHospitalised;
    }
}
