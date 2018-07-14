package com.example.pengganggui.lvrtest2.module_essay;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.pengganggui.lvrtest2.R;
import com.example.pengganggui.lvrtest2.domain.Resource;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuItemEntity;
import com.example.pengganggui.lvrtest2.module_essay.db.entity.ZhihuStoriesEntity;
import com.example.pengganggui.lvrtest2.module_essay.ui.DividerItemDecoration;
import com.example.pengganggui.lvrtest2.module_essay.ui.EssayListAdapter;
import com.example.pengganggui.lvrtest2.module_essay.viewModel.EssayViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengganggui on 2018/7/14.
 */

public class EssayFragment extends Fragment {
    public static final String TAG="EssayFragment";
    private RecyclerView mListView;
    private EssayListAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private EssayViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.essay_fragment,null);
        mListView=v.findViewById(R.id.products_list);
        refreshLayout=v.findViewById(R.id.refresh);
        initView();
        subscribeUi();
        return v;
    }

    /**
     * 将数据与View绑定
     */
    private void subscribeUi() {
        viewModel.getEssayData().observe(this, new Observer<Resource<ZhihuItemEntity>>() {
            @Override
            public void onChanged(@Nullable Resource<ZhihuItemEntity> zhihuItemEntityResource) {

                if (zhihuItemEntityResource!=null&&zhihuItemEntityResource.data!=null){
                    if (zhihuItemEntityResource.status==Resource.Status.SUCCEED){
                        updateUI(zhihuItemEntityResource.data);
                        Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
                    }else if (zhihuItemEntityResource.status == Resource.Status.LOADING){
                        Toast.makeText(getActivity(), "DB loaded " + zhihuItemEntityResource.message, Toast.LENGTH_SHORT).show();
                    }else if (zhihuItemEntityResource.status == Resource.Status.ERROR) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }
                }
                refreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 更新UI
     * @param data
     */
    private void updateUI(ZhihuItemEntity data) {
        List<EssayListAdapter.MultiItem> list=new ArrayList<>();
        for (ZhihuStoriesEntity entity:data.getStories()){
            EssayListAdapter.MultiItem item=new EssayListAdapter.MultiItem(entity, EssayListAdapter.MultiItem.TYPE_BASE);
            list.add(item);
        }
        for (ZhihuStoriesEntity entity:data.getTop_stories()){
            EssayListAdapter.MultiItem item=new EssayListAdapter.MultiItem(entity, EssayListAdapter.MultiItem.TYPE_BASE);
            list.add(item);
        }
        mAdapter.replaceData(list);
        getLifecycle().addObserver(new LifecycleObserver() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initView() {
        viewModel= ViewModelProviders.of(this).get(EssayViewModel.class);
        View footerView=new View(getActivity());
        footerView.setLayoutParams(new RecyclerView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 68));
        mAdapter=new EssayListAdapter(getActivity(),new ArrayList<EssayListAdapter.MultiItem>());
        mAdapter.bindToRecyclerView(mListView);
        mListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mAdapter.addFooterView(footerView);
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshLayout.setRefreshing(true);
    }
}
