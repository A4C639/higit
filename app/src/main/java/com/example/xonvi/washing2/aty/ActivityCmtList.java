package com.example.xonvi.washing2.aty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.xonvi.washing2.Entity.CmtListResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.CmtListAdp;
import com.example.xonvi.washing2.presenter.CmtListPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/14.
 */

//显示评论列表
public class ActivityCmtList extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.lv_aty_cmtlist)ListView lv_aty_cmtlist;
    @BindView(R.id.refresh_aty_comment)SwipeRefreshLayout refresh_aty_comment;
    @OnClick({R.id.iv_aty_cmtlist_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_cmtlist_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmtlist);
        ButterKnife.bind(this);

        initCmtList();

    }


    //初始化评论列表
    private void initCmtList() {

        refresh_aty_comment.setOnRefreshListener(this);
        refresh_aty_comment.setRefreshing(true);
        refresh_aty_comment.setColorSchemeColors(Color.parseColor("#339999"));

        new CmtListPresenter().getCmtList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CmtListResEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cmtlisterro",e.toString());
                        ToastUtil.toast("网络异常");
                        refresh_aty_comment.setRefreshing(false);

                    }

                    @Override
                    public void onNext(CmtListResEntity entity) {

                        if(entity.getCode()==1){
                            lv_aty_cmtlist.setAdapter(new CmtListAdp(entity.getData()));
                        }
                        refresh_aty_comment.setRefreshing(false);
                        ToastUtil.toast("操作成功");

                    }
                });


    }

    //refresh---------------------------------------------
    @Override
    public void onRefresh() {

        initCmtList();
    }
}
