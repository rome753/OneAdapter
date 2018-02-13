package cc.rome753.oneadapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cc.rome753.oneadapter.base.OneViewHolder;

/**
 * OneViewHolder's data binding wrapper
 * OneViewHolder的支持数据绑定的包装类
 * @param <D> 数据类型
 * @param <B> ViewDataBinding类型
 */
public abstract class OneViewHolderWrapper<D,B extends ViewDataBinding>{

    private OneViewHolder<D> oneViewHolder;

    protected B binding;

    public OneViewHolderWrapper(ViewGroup parent, int layoutRes){
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutRes, parent, false);
        oneViewHolder = new OneViewHolder<D>(binding.getRoot()) {
            @Override
            protected void bindViewCasted(int position, D d) {
                OneViewHolderWrapper.this.bindViewCasted(position, d);
            }
        };
    }

    public OneViewHolder<D> getOneViewHolder() {
        return oneViewHolder;
    }

    protected abstract void bindViewCasted(int position, D d);
}