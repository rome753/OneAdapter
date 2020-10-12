package cc.rome753.oneadapter.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cc.rome753.oneadapter.base.OneViewHolder;

/**
 * A wrapper of OneViewHolder, supports data binding
 * @param <D> the type of the data
 * @param <B> the type of the ViewDataBinding
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

    public OneViewHolder<D> asOneViewHolder() {
        return oneViewHolder;
    }

    protected abstract void bindViewCasted(int position, D d);
}