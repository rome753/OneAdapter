package cc.rome753.oneadapter.base;

import android.view.ViewGroup;

/**
 * A listener for: define item view type and create ViewHolder, outside of the adapter
 */
public interface OneListener{

    /**
     * Is the position or the data suits for this OneListener?
     * @param position the data's position int the list
     * @param o the data
     * @return true/false
     */
    boolean isMyItemViewType(int position, Object o);

    /**
     * Create a ViewHolder for this OneListener
     * @param parent RecyclerView
     * @return OneViewHolder
     */
    OneViewHolder getMyViewHolder(ViewGroup parent);
}