package com.github.cyc.wanandroid.module.main.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.annotation.NonNull;

import com.github.cyc.wanandroid.base.viewmodel.BaseViewModel;
import com.github.cyc.wanandroid.data.DataManager;
import com.github.cyc.wanandroid.enums.LoadState;
import com.github.cyc.wanandroid.http.base.BaseObserver;
import com.github.cyc.wanandroid.http.model.Chapter;
import com.github.cyc.wanandroid.utils.RxUtils;
import com.github.cyc.wanandroid.utils.Utils;

import java.util.List;

/**
 * 项目tab的ViewModel
 */
public class ProjectViewModel extends BaseViewModel {

    public final ObservableList<Chapter> dataList = new ObservableArrayList<>();

    private DataManager mDataManager;

    public ProjectViewModel(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void loadData() {
        loadState.set(LoadState.LOADING);
        addDisposable(mDataManager.getProjectListData()
            .compose(RxUtils.applySchedulers())
            .subscribeWith(new BaseObserver<List<Chapter>>(loadState) {

                @Override
                public void onNextX(List<Chapter> chapterList) {
                    if (!Utils.isListEmpty(chapterList)) {
                        dataList.addAll(chapterList);
                        loadState.set(LoadState.SUCCESS);
                    } else {
                        loadState.set(LoadState.NO_DATA);
                    }
                }
            }));
    }

    @Override
    public void reloadData() {
        loadData();
    }
}
