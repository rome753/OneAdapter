package cc.rome753.oneadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动进行类型转换的ViewHolder
 * @param <D> 数据类型
 */
public abstract class OneViewHolder<D> extends RecyclerView.ViewHolder {

    public OneViewHolder(View itemView) {
        super(itemView);
    }

    public OneViewHolder(ViewGroup parent, int layoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    /**
     * bindView对数据进行类型转换
     * @param position 数据位置
     * @param o 数据
     * @throws ClassCastException 类型转换异常
     */
    void bindView(int position, Object o) throws ClassCastException{
        bindViewCasted(position, (D) o);
    }

    /**
     * 绑定类型转换后数据到itemView上
     * @param position 位置
     * @param d 数据
     */
    protected abstract void bindViewCasted(int position, D d);
}