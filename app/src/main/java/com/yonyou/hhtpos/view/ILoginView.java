package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.db.entity.UserEntity;

/**
 * Created by ybing on 2017/7/5.
 * 登录
 */

public interface ILoginView extends BaseView{
    void login(UserEntity dataBean);
}
