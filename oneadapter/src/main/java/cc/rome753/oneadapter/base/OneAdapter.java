package cc.rome753.oneadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom adapter, supports multi-ItemViewType
 * <p>
 * Created by rome753 on 2018/2/1.
 */
public class OneAdapter extends RecyclerView.Adapter<OneViewHolder> {

    protected final List<Object> mData;
    protected final List<OneTemplate> mTemplates;

    public OneAdapter() {
        mData = new ArrayList<>();
        mTemplates = new ArrayList<>();
    }

    public OneAdapter register(OneTemplate oneTemplate) {
        mTemplates.add(oneTemplate);
        return this;
    }

    public void setData(List<?> data) {
        mData.clear();
        mData.addAll(data);
    }

    public void addData(List<?> data) {
        mData.addAll(data);
    }

    public List<Object> getData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mData.get(position);
        for (int i = 0; i < mTemplates.size(); i++) {
            OneTemplate listener = mTemplates.get(i);
            if (listener.isMyItemViewType(position, o)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTemplates.get(viewType).getMyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        Object o = mData.get(position);
        holder.bindView(position, o);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
