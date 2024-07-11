package com.example.alldocunemtreader.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.example.alldocunemtreader.constants.RequestCodeConstants;
import com.example.alldocunemtreader.data.model.EventBusMessage;
import com.example.alldocunemtreader.ui.common.BottomDialogRadioButton;
import com.example.alldocunemtreader.ui.common.BottomDialogRadioGroup;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/25 15:55.
 * Description: com.example.alldocunemtreader.utils.ArrangementHelper
 */
public class ArrangementHelper {
    public static List<Integer> getViewSettings() {
        int viewMethodId = MMKVManager.decodeInt(MMKVKeyConstants.VIEW_METHOD_ID, R.id.bdrb_list);
        int sortMethodId = MMKVManager.decodeInt(MMKVKeyConstants.SORT_METHOD_ID, R.id.bdrb_date);
        int orderMethodId = MMKVManager.decodeInt(MMKVKeyConstants.ORDER_METHOD_ID, R.id.bdrb_desc);
        return new ArrayList<>(Arrays.asList(viewMethodId, sortMethodId, orderMethodId));
    }

    public static void setViewSettings(int viewMethodId, int sortMethodId, int orderMethodId) {
        MMKVManager.encode(MMKVKeyConstants.VIEW_METHOD_ID, viewMethodId);
        MMKVManager.encode(MMKVKeyConstants.SORT_METHOD_ID, sortMethodId);
        MMKVManager.encode(MMKVKeyConstants.ORDER_METHOD_ID, orderMethodId);
    }

    /**
     * 展示修改排列方式和顺序的BottomSheetDialog
     *
     * @param context
     */
    public static void showBottomDialog(Context context) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_arrangement, null);
        bottomSheetDialog.setContentView(view);

        List<Integer> viewSettings = ArrangementHelper.getViewSettings();
        BottomDialogRadioGroup bdrgViewMethod = view.findViewById(R.id.bdrg_view_method);
        BottomDialogRadioGroup bdrgSortMethod = view.findViewById(R.id.bdrg_sort_method);
        BottomDialogRadioGroup bdrgOrderMethod = view.findViewById(R.id.bdrg_order_method);
        bdrgViewMethod.check(view.findViewById(viewSettings.get(0)));
        bdrgSortMethod.check(view.findViewById(viewSettings.get(1)));
        bdrgOrderMethod.check(view.findViewById(viewSettings.get(2)));

        ImageView ivBottomDialogCancel = view.findViewById(R.id.iv_bottom_dialog_cancel);
        ivBottomDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        Button btnBottomDialogApply = view.findViewById(R.id.btn_bottom_dialog_apply);
        btnBottomDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewMethodId = bdrgViewMethod.getChecked().getId();
                int sortMethodId = bdrgSortMethod.getChecked().getId();
                int orderMethodId = bdrgOrderMethod.getChecked().getId();
                ArrangementHelper.setViewSettings(viewMethodId, sortMethodId, orderMethodId);
                //刷新
                EventBusUtils.post(new EventBusMessage<>(RequestCodeConstants.REQUEST_REFRESH,
                        ArrangementHelper.getViewSettings()));
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

}
