package com.example.alldocunemtreader.ui.preview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.alldocunemtreader.R;
import com.tencent.tbs.reader.ITbsReader;
import com.tencent.tbs.reader.ITbsReaderCallback;
import com.tencent.tbs.reader.TbsFileInterfaceImpl;

public class PreviewFragment extends Fragment {
    private FrameLayout mFlRoot; //内容显示区域
    private RelativeLayout mRl; //自定义标题栏

    private static boolean isInit = false;
    private String fileExt;
    private boolean isDestroyed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFlRoot = view.findViewById(R.id.content);
        mRl = view.findViewById(R.id.reader_top);

        String documentPath = getArguments().getString("documentPath");
        fileExt = getFileExtension(documentPath);

        int ret = initEngine();
        if (ret != 0) {
            //Toast.makeText(requireActivity(), "初始化Engine失败 ret = " + ret, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), "初始化Engine成功", Toast.LENGTH_SHORT).show();
            isInit = true;
        }

        openExternalFilesDirDocument(documentPath);

    }

    public String getFileExtension(String filePath) {
        if (filePath == null) {
            return null;
        }
        int lastDotIndex = filePath.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return ""; // 如果没有找到点号，返回空字符串或其他默认值
        }
        return filePath.substring(lastDotIndex + 1);
    }

    public int initEngine() {
        //设置licenseKey
        TbsFileInterfaceImpl.setLicenseKey("thv/GJBpFD648laghhALjRxomLklQ5T1IgxGivJDlPX9HSzyirSZRIfBygkiP1yD");

        int ret = -1;

        //初始化Engine
        if (!TbsFileInterfaceImpl.isEngineLoaded()) {
            ret = TbsFileInterfaceImpl.initEngine(requireActivity());
        }

        return ret;
    }


    private void openExternalFilesDirDocument(String filePath) {
        if (isInit) {
            if (TbsFileInterfaceImpl.canOpenFileExt(fileExt)) {
                openFile(filePath, fileExt);
            } else {
                // tbs不支持打开的类型
            }
        } else {
            Toast.makeText(requireActivity(), "Engine未初始化成功，无法打开文件", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFile(String filePath, String fileExt) {
        //设置回调
        ITbsReaderCallback callback = new ITbsReaderCallback() {
            @Override
            public void onCallBackAction(Integer actionType, Object args, Object result) {
                Log.i("TAG", "actionType=" + actionType + "，args=" + args + "，result=" + result);
                if (ITbsReader.OPEN_FILEREADER_STATUS_UI_CALLBACK == actionType) {
                    if (args instanceof Bundle) {
                        int id = ((Bundle) args).getInt("typeId");
                        if (ITbsReader.TBS_READER_TYPE_STATUS_UI_SHUTDOWN == id) {
                            // 加密文档弹框取消需关闭activity
                            Navigation.findNavController(getView()).popBackStack();
                        }
                    }
                }
            }
        };

        //设置参数
        Bundle param = new Bundle();
        param.putString("filePath", filePath);
        param.putString("fileExt", fileExt); // 文件后缀名，如文件名为test.pdf，则只需要传入"pdf"
        param.putString("tempPath", requireActivity().getExternalFilesDir("temp").getAbsolutePath());
        //调用openFileReader打开文件
        mFlRoot.post(new Runnable() {
            @Override
            public void run() {
                int height = mFlRoot.getHeight();
                // 自定义layout模式必须设置这个值，否则可能导致文档内容显示不全
                param.putInt("set_content_view_height", height);
                int ret = TbsFileInterfaceImpl.getInstance().openFileReader(
                        requireActivity(), param, callback, mFlRoot);
            }
        });
    }

    // 销毁资源使用onpause + ondestroy的方式。避免onDestroy延迟回调
    private void destroy() {
        if (isDestroyed) {
            return;
        }
        // 回收资源
        mFlRoot.removeAllViews(); //移除内容显示区域layout
        TbsFileInterfaceImpl.getInstance().closeFileReader(); //关闭fileReader
        isDestroyed = true;
    }


    @Override
    public void onPause() {
        super.onPause();
        destroy();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();
    }


}