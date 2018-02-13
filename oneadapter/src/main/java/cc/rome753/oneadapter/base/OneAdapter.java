package cc.rome753.oneadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView.Adapter适配器的封装，支持多种ItemViewType
 *
 * Created by rome753 on 2018/2/1.
 */

public class OneAdapter extends RecyclerView.Adapter<OneViewHolder> {

    private final List<Object> data;
    private final List<OneListener> listeners;

    public OneAdapter(OneListener... listeners){
        this.data = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public void setData(List<?> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<?> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<Object> getData(){
        return data;
    }

    public List<OneListener> getListeners() {
        return listeners;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = data.get(position);
        for(int i = 0; i < listeners.size(); i++){
            OneListener listener = listeners.get(i);
            if(listener.isMyItemViewType(position, o)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return listeners.get(viewType).getMyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        Object o = data.get(position);
        holder.bindView(position, o);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
