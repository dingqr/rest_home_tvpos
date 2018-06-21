package com.smart.framework.library.adapter;

/**
 * 作者：addison on 15/12/15 11:14
 * 邮箱：lsf@yonyou.com
 */
public interface ViewHolderCreator<ItemDataType> {
    public ViewHolderBase<ItemDataType> createViewHolder(int position);
}
