package cc.rome753.demo.oneadapter;

import android.view.ViewGroup;

/**
 * 每种数据类型的Listener，OneAdapter处理本类型数据时回调
 */
public interface OneListener{

    /**
     * 根据位置或数据判断是否创建本类型的OneViewHolder
     * @param position 位置
     * @param o 数据
     * @return true/false
     */
    boolean isMyItemViewType(int position, Object o);

    /**
     * 创建OneViewHolder
     * @param parent RecyclerView
     * @return OneViewHolder
     */
    OneViewHolder getMyViewHolder(ViewGroup parent);
}