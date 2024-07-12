package com.example.alldocunemtreader.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.utils.EventBusUtils;
import com.example.alldocunemtreader.utils.NotificationHelper;
import com.example.alldocunemtreader.utils.ScreenshotObserver;
import com.example.alldocunemtreader.utils.ThreadPoolManager;
import com.example.alldocunemtreader.viewmodelfactory.MainViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;

public class MainActivity extends AppCompatActivity {
    private static final String PACKAGE_PREFIX = "package:";

    private MainViewModel mainViewModel;
    private ScreenshotObserver screenshotObserver;
    private AlertDialog goSettingDialog;
    private Dialog dlLoading;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EventBusUtils.register(this);
        mainViewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        //注册截屏监听器
        screenshotObserver = new ScreenshotObserver(this, new Handler(Looper.getMainLooper()));
        ScreenshotObserver.registerScreenshotObserver(this);

        //发送永久通知
        new NotificationHelper(this).sendPermanentNotification();

        //检查请求权限后扫描
        checkAndRequestPermissionThenScan();

        //注册Firebase推送通知
        registerFirebaseMessaging();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTime = System.currentTimeMillis();
    }

    private void checkAndRequestPermissionThenScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //30 以上直接去设置页面开启
            checkAndRequestAllFilePermission();
        } else {
            //30 以下请求后自动获取
            checkAndRequestStoragePermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkAndRequestAllFilePermission() {
        if (Environment.isExternalStorageManager()) {
            startScan();
        } else {
            setGoSettingDialog(this,
                    getResources().getText(R.string.need_all_file_permission).toString(),
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    RequestCodeConstants.REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void setGoSettingDialog(Context context, String title, String action, int requestCode) {
        if (goSettingDialog == null) {
            goSettingDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setCancelable(false)
                    .setPositiveButton(getResources().getText(R.string.authorize), (dialog, which) -> {
                        Uri uri = Uri.parse(PACKAGE_PREFIX + getPackageName());
                        Intent intent = new Intent(action, uri);
                        startActivityForResult(intent, requestCode);
                    }).create();
        }
        goSettingDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeConstants.REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    Toast.makeText(this, getResources().getText(R.string.no_permission), Toast.LENGTH_SHORT).show();
                } else {
                    startScan();
                }
            } else {
                checkAndRequestStoragePermission();
            }
        }
    }

    private void checkAndRequestStoragePermission() {
        try {
            if (checkStoragePermission()) {
                startScan();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        RequestCodeConstants.REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestCodeConstants.REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this,
                            getResources().getText(R.string.no_permission),
                            Toast.LENGTH_SHORT).show();
                } else {
                    setGoSettingDialog(this,
                            getResources().getText(R.string.need_storage_permission).toString(),
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            requestCode);
                }
            }
        }
    }

    private void registerFirebaseMessaging() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d("TAG", token);
                });
    }

    private void startScan() {
        if (dlLoading == null) {
            dlLoading = new Dialog(this, R.style.CustomDialogTheme);
            dlLoading.setContentView(R.layout.dialog_loading);
            dlLoading.setCancelable(false);
        }
        dlLoading.show();
        mainViewModel.startScan();
    }

    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(EventBusMessage eventBusMessage) {
        if (Objects.equals(eventBusMessage.getCode(), RequestCodeConstants.REQUEST_SCAN_FINISHED)) {
            long overTime = System.currentTimeMillis();
            if (overTime - startTime < 1000) {
                Completable.timer(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            // 在主线程中执行的操作
                            dlLoading.dismiss();
                        });
            } else {
                dlLoading.dismiss();
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        checkAndRequestPermissionThenScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
        ScreenshotObserver.unregisterScreenshotObserver(this, screenshotObserver);
    }
}