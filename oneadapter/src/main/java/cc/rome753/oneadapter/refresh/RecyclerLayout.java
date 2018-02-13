package cc.rome753.oneadapter.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;

/**
 * Created by rome753 on 18-2-9.
 */

public class RecyclerLayout extends SwipeRefreshLayout implements OnRefreshListener, LoadingLayout.OnLoadingListener {

    private RecyclerView recyclerView;
    private LoadingLayout loadingLayout;

    private OneAdapter oneAdapter;
    private GridLayoutManager gridLayoutManager;

    private OnRefreshListener onRefreshListener;
    private LoadingLayout.OnLoadingListener onLoadingListener;

    public RecyclerLayout(Context context) {
        this(context, null);
    }

    public RecyclerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setColorSchemeResources(R.color.colorPrimary);
        setOnRefreshListener(this);

        loadingLayout = new LoadingLayout(context);

        gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastVisibleItemPosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == oneAdapter.getItemCount() - 1 - 1) {
                    //加载更多
                    onLoading();
                }
            }
        });

        addView(recyclerView);
    }

    public void init(final OneAdapter oneAdapter, OnRefreshListener onRefreshListener, LoadingLayout.OnLoadingListener onLoadingListener){
        this.recyclerView.setAdapter(oneAdapter);
        this.oneAdapter = oneAdapter;
        this.oneAdapter.getListeners().add(0, new OneListener() {
            @Override
            public boolean isMyItemViewType(int position, Object o) {
                return position == oneAdapter.getItemCount() - 1;
            }

            @Override
            public OneViewHolder getMyViewHolder(ViewGroup parent) {
                return new OneViewHolder(loadingLayout) {
                    @Override
                    protected void bindViewCasted(int position, Object o) {
                        //ignore
                    }
                };
            }
        });

        if(onRefreshListener == null){
            setEnabled(false);
        }
        this.onRefreshListener = onRefreshListener;
        this.onLoadingListener = onLoadingListener;
    }

    @Override
    public void onRefresh() {
        if(onRefreshListener != null){
            onRefreshListener.onRefresh();
        }
    }

    @Override
    public void onLoading() {
        if(onLoadingListener != null && !isRefreshing() && !isLoading() && !isNoMore()){
            onLoadingListener.onLoading();
            setLoading(true, isNoMore());
        }
    }

    public void setData(List<?> data, boolean hasMore){
        data.add(null);
        oneAdapter.setData(data);

        setRefreshing(false);
        setLoading(false, !hasMore);
    }

    public void addData(List<?> data, boolean hasMore){
        List<Object> cur = oneAdapter.getData();
        if(!cur.isEmpty()){
            cur.remove(cur.size() - 1);
        }
        data.add(null);
        oneAdapter.addData(data);

        setLoading(false, !hasMore);
    }

    private boolean isNoMore(){
        return loadingLayout.isNoMore();
    }

    private boolean isLoading(){
        return loadingLayout.isLoading();
    }

    private void setLoading(boolean loading, boolean isNoMore){
        loadingLayout.setLoading(loading, isNoMore);
    }
}
