package cc.rome753.demo.oneadapter;

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
     * bindView之前对数据进行类型转换。如果当前位置数据不是T类型，会抛出ClassCastException
     * @param position 数据位置
     * @param o 数据
     * @throws ClassCastException 类型转换异常
     */
    void bindViewCast(int position, Object o) throws ClassCastException{
        bindView(position, (D) o);
    }

    public abstract void bindView(int position, D d);
}