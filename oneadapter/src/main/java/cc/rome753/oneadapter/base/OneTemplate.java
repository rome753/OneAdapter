package cc.rome753.oneadapter.base;

import android.view.ViewGroup;

/**
 * A template for: define item view type and create ViewHolder, outside of the adapter
 */
public interface OneTemplate {

    /**
     * Is the position or the data matches this OneTemplate?
     * @param position the data's position int the list
     * @param o the data
     * @return true/false
     */
    boolean isMatch(int position, Object o);

    /**
     * Create a ViewHolder for this OneTemplate
     * @param parent RecyclerView
     * @return OneViewHolder
     */
    OneViewHolder getViewHolder(ViewGroup parent);
}