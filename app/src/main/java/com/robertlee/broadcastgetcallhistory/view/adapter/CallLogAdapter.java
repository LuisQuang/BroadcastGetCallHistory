package com.robertlee.broadcastgetcallhistory.view.adapter;

import android.annotation.SuppressLint;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlee.broadcastgetcallhistory.R;
import com.robertlee.broadcastgetcallhistory.model.MyCallLog;

import java.text.SimpleDateFormat;
import java.util.List;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogHolder> {
    private List<MyCallLog> callLogsList;

    public CallLogAdapter(List<MyCallLog> callLogsList) {
        this.callLogsList = callLogsList;
    }

    @NonNull
    @Override
    public CallLogAdapter.CallLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_item, parent, false);
        return new CallLogHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(CallLogAdapter.CallLogHolder holder, int position) {
        MyCallLog call = callLogsList.get(position);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        holder.phoneNumberTextView.setText(call.getPhoneNumber());
        holder.callStartTimeTextView.setText(formatter.format(call.getCallStartTime()));
        holder.callDurationTextView.setText(String.format("%ds", call.getCallDuration()));
    }

    @Override
    public int getItemCount() {
        return callLogsList.size();
    }

    public static class CallLogHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberTextView, callStartTimeTextView, callDurationTextView;

        public CallLogHolder(View view) {
            super(view);
            phoneNumberTextView = view.findViewById(R.id.phone_number_text_view);
            callStartTimeTextView = view.findViewById(R.id.call_start_time_text_view);
            callDurationTextView = view.findViewById(R.id.call_duration_text_view);
        }
    }
}
