package com.example.alldocunemtreader.ui.main;

import android.Manifest;
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
import com.example.alldocunemtreader.utils.NotificationHelper;
import com.example.alldocunemtreader.utils.ScreenshotObserver;
import com.example.alldocunemtreader.viewmodelfactory.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String PACKAGE_PREFIX = "package:";

    private MainViewModel mainViewModel;
    private ScreenshotObserver screenshotObserver;
    private AlertDialog goSettingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        //注册截屏监听器
        screenshotObserver = new ScreenshotObserver(this, new Handler(Looper.getMainLooper()));
        ScreenshotObserver.registerScreenshotObserver(this);

        //发送永久通知
        new NotificationHelper(this).sendPermanentNotification();

        //检查请求权限后扫描
        checkAndRequestPermissionThenScan();
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
            mainViewModel.startScan();
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
                    .setPositiveButton(getResources().getText(R.string.authorize), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse(PACKAGE_PREFIX + getPackageName());
                            Intent intent = new Intent(action, uri);
                            startActivityForResult(intent, requestCode);
                        }
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
                    mainViewModel.startScan();
                }
            } else {
                checkAndRequestStoragePermission();
            }
        }
    }

    private void checkAndRequestStoragePermission() {
        try {
            if (checkStoragePermission()) {
                mainViewModel.startScan();
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
        return ContextCompat.
                checkSelfPermission(this,
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
                mainViewModel.startScan();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAndRequestPermissionThenScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenshotObserver.unregisterScreenshotObserver(this, screenshotObserver);
    }
}