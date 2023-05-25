package com.robertlee.broadcastgetcallhistory.view.act;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.robertlee.broadcastgetcallhistory.databinding.GetHistoryCallBinding;
import com.robertlee.broadcastgetcallhistory.model.MyCallLog;
import com.robertlee.broadcastgetcallhistory.view.adapter.CallLogAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CALL_LOG = 1;
    private GetHistoryCallBinding binding;
    private List<MyCallLog> callLogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GetHistoryCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        initData();
        binding.btnGetCall.setOnClickListener(v -> {
            v.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, androidx.appcompat.R.anim.abc_fade_in));
            getCallHis();
        });

    }

    private void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int rs : grantResults) {
            if (rs != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please allow this permissions to get data!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCallHis() {
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CALL_LOG}, 101);
            return;
        } else {
            loadCallHistory();
        }

    }

    private void loadCallHistory() {
        callLogsList = new ArrayList<>();
        // Lấy ContentResolver để truy vấn vào ContentProvider
        ContentResolver contentResolver = getContentResolver();
        // Thực hiện truy vấn và chỉ định các trường cần lấy
        String[] projection = new String[]{CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION};
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

        // Nếu cursor không trả về null thì duyệt cursor để lấy thông tin lịch sử cuộc gọi
        if (cursor != null) {
            int phoneNumberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int callDurationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int callStartTimeIndex = cursor.getColumnIndex(CallLog.Calls.DATE);

            while (cursor.moveToNext()) {
                // Lấy thông tin lịch sử cuộc gọi
                String phoneNumber = cursor.getString(phoneNumberIndex);
                String callDurationString = cursor.getString(callDurationIndex);
                int callDuration = Integer.parseInt(callDurationString);
                long callStartTimeMillis = cursor.getLong(callStartTimeIndex);
                Date callStartTime = new Date(callStartTimeMillis);

                // Tạo đối tượng MyCallLog mới và thêm vào danh sách
                MyCallLog callLog = new MyCallLog(phoneNumber, callStartTime, callDuration);
                callLogsList.add(callLog);
            }
            cursor.close();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.callHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        CallLogAdapter callLogAdapter = new CallLogAdapter(callLogsList);
        binding.callHistoryRecyclerView.setAdapter(callLogAdapter);
    }
}


