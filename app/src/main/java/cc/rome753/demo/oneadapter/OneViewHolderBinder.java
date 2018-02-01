package cc.rome753.demo.oneadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * OneViewHolder's data binding wrapper
 * OneViewHolder的支持数据绑定的包装类
 * @param <D> 数据类型
 * @param <B> ViewDataBinding类型
 */
public abstract class OneViewHolderBinder<D,B extends ViewDataBinding>{

    private OneViewHolder<D> oneViewHolder;

    protected B binding;

    public OneViewHolderBinder(ViewGroup parent, int layoutRes){
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutRes, parent, false);
        oneViewHolder = new OneViewHolder<D>(binding.getRoot()) {
            @Override
            public void bindView(int position, D d) {
                bingingView(position, d);
            }
        };
    }

    public OneViewHolder<D> getOneViewHolder() {
        return oneViewHolder;
    }

    protected abstract void bingingView(int position, D d);
}