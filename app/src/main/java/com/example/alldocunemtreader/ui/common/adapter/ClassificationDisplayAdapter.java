package com.example.alldocunemtreader.ui.common.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.alldocunemtreader.ui.classification.tabs.ALLSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.DOCSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.OTHERSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.PDFSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.PPTSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.TXTSelectedFragment;
import com.example.alldocunemtreader.ui.classification.tabs.XLSSelectedFragment;

/**
 * Author: Eccentric
 * Created on 2024/6/25 14:13.
 * Description: com.example.alldocunemtreader.ui.common.adapter.ClassificationDisplayAdapter
 */
public class ClassificationDisplayAdapter extends FragmentStateAdapter {
    public ClassificationDisplayAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ALLSelectedFragment();
            case 1:
                return new PDFSelectedFragment();
            case 2:
                return new DOCSelectedFragment();
            case 3:
                return new XLSSelectedFragment();
            case 4:
                return new PPTSelectedFragment();
            case 5:
                return new TXTSelectedFragment();
            case 6:
                return new OTHERSelectedFragment();
        }
        return new ALLSelectedFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
