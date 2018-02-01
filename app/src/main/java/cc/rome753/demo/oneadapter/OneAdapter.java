package cc.rome753.demo.oneadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView.Adapter适配器的封装，支持多种数据类型
 *
 * Created by chao on 2018/2/1.
 */

public class  OneAdapter extends RecyclerView.Adapter<OneViewHolder> {

    private List<Object> data;
    private final List<OneListener> listeners;

    public OneAdapter(OneListener... listeners){
        this.listeners = Arrays.asList(listeners);
    }

    public void setData(List<Object> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<Object> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<Object> getData(){
        return data;
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
        Object t = data.get(position);
        holder.bindViewCast(position, t);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
