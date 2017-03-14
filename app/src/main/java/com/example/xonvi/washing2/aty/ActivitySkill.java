package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xonvi.washing2.Entity.SkillEntity;
import com.example.xonvi.washing2.Entity.SkillResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.SkillRVAdp;
import com.example.xonvi.washing2.presenter.SkillPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/17.
 */

//洗衣小常识
public class ActivitySkill extends BaseActivity {
    @BindView(R.id.rv_aty_skill)RecyclerView rv_aty_skill;
    @OnClick({R.id.iv_aty_skill_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_skill_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        ButterKnife.bind(this);

        initSkillList();
    }

    //初始化列表
    private void initSkillList() {
        new SkillPresenter().getSkillList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SkillResEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("skilllsiteror",e.toString());
                    }

                    @Override
                    public void onNext(SkillResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){
                            loadSkillList(entity.getData());
                        }
                    }
                });

    }

    //加载小技巧列表
    private void loadSkillList(List<SkillEntity> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_aty_skill.setLayoutManager(manager);
        rv_aty_skill.setAdapter(new SkillRVAdp(list));
    }
}


