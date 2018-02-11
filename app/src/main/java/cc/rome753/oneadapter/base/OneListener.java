package cc.rome753.oneadapter.base;

import android.view.ViewGroup;

/**
 * 每种ItemViewType对应的Listener，监听OneAdapter判断ItemView类型和获取OneViewHolder
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
     * 获取OneViewHolder
     * @param parent RecyclerView
     * @return OneViewHolder
     */
    OneViewHolder getMyViewHolder(ViewGroup parent);
}