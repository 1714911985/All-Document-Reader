package com.example.alldocunemtreader.ui.language;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.alldocunemtreader.R;
import com.example.alldocunemtreader.constants.LanguageConstants;
import com.example.alldocunemtreader.constants.MMKVKeyConstants;
import com.example.alldocunemtreader.ui.common.CustomRadioItem;
import com.example.alldocunemtreader.ui.common.RadioGroupView;
import com.example.alldocunemtreader.ui.main.MainActivity;
import com.example.alldocunemtreader.utils.MMKVManager;

import java.util.Objects;

public class ChangeLanguageFragment extends Fragment implements View.OnClickListener{
    private Toolbar tbChangeLanguage;
    private CustomRadioItem criChinese, criEnglish;
    private RadioGroupView rgvChange;
    private LottieAnimationView lavConfirm;
    private ChangeLanguageViewModel changeLanguageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
        setToolbarButton();
        setCheckedCustomRadioItem();
    }

    private void initView(View view) {
        tbChangeLanguage = view.findViewById(R.id.tb_change_language);
        criChinese = view.findViewById(R.id.cri_chinese);
        criEnglish = view.findViewById(R.id.cri_english);
        rgvChange = view.findViewById(R.id.rgv_change);
        lavConfirm = view.findViewById(R.id.lav_confirm);
        lavConfirm.setAnimation(R.raw.checked);
        lavConfirm.playAnimation();
        lavConfirm.setOnClickListener(this);
    }

    private void initData(){
        changeLanguageViewModel = new ViewModelProvider(this).get(ChangeLanguageViewModel.class);
    }

    /**
     * 设置toolbar的返回按钮
     */
    private void setToolbarButton() {
        tbChangeLanguage.setNavigationIcon(R.drawable.ic_back_black);
        tbChangeLanguage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
    }

    /**
     * 选中当前语言
     */
    private void setCheckedCustomRadioItem() {
        String language = MMKVManager.decodeString(MMKVKeyConstants.LANGUAGE, LanguageConstants.ENGLISH);
        switch (language) {
            case LanguageConstants.CHINESE:
                criChinese.setChecked(true);
                rgvChange.check(criChinese);
                criChinese.performClick();
                break;
            case LanguageConstants.ENGLISH:
                criEnglish.setChecked(true);
                rgvChange.check(criEnglish);
                criEnglish.performClick();
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lav_confirm){
            changeLanguage();
        }
    }

    private void changeLanguage() {
        if (criChinese.isChecked()) {
            changeLanguageViewModel.changeAppLanguage(requireActivity(),LanguageConstants.CHINESE);
        } else if (criEnglish.isChecked()) {
            changeLanguageViewModel.changeAppLanguage(requireActivity(),LanguageConstants.ENGLISH);
        }

        changeLanguageViewModel.getLanguageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                requireActivity().finish();
                Intent refresh = new Intent(getActivity(), MainActivity.class);
                startActivity(refresh);
            }
        });
    }
}