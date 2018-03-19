package cc.rome753.oneadapter.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by rome753 on 18-2-9.
 */

public class RecyclerLayout extends SwipeRefreshLayout implements OnRefreshListener, LoadingLayout.OnLoadingListener {

    private RecyclerView recyclerView;
    private LoadingLayout loadingLayout;

    private FooterAdapter oneAdapter;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    private OnRefreshListener onRefreshListener;
    private LoadingLayout.OnLoadingListener onLoadingListener;

    public RecyclerLayout(Context context) {
        this(context, null);
    }

    public RecyclerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);

        loadingLayout = new LoadingLayout(context);

        gridLayoutManager = new GridLayoutManager(context, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == oneAdapter.getItemCount() - 1){
                    return gridLayoutManager.getSpanCount();
                }
                if(spanSizeLookup != null){
                    return spanSizeLookup.getSpanSize(position);
                }
                return 1;
            }
        });
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
                    // load more
                    onLoading();
                }
            }
        });

        addView(recyclerView);
    }

    /**
     * Init the RecyclerLayout, the onRefreshListener and the onLoadingListener can be null.
     * @param footerAdapter adapter for the inner RecyclerView.
     * @param onRefreshListener refresh listener, set null to disable refresh.
     * @param onLoadingListener load more listener, set null to disable load more.
     */
    public void init(@NonNull final FooterAdapter footerAdapter, OnRefreshListener onRefreshListener, LoadingLayout.OnLoadingListener onLoadingListener){
        footerAdapter.setFooterView(loadingLayout);
        this.oneAdapter = footerAdapter;
        this.recyclerView.setAdapter(oneAdapter);

        if(onRefreshListener == null){
            setEnabled(false);
        }
        this.onRefreshListener = onRefreshListener;
        this.onLoadingListener = onLoadingListener;
    }

    /**
     * Set spanCount and spanSizeLookup for the GridLayoutManager, spanSizeLookup can be null.
     * @param spanCount span count
     * @param spanSizeLookup if you don't need, set null.
     */
    public void setSpan(int spanCount, GridLayoutManager.SpanSizeLookup spanSizeLookup){
        gridLayoutManager.setSpanCount(spanCount);
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void onRefresh() {
        if(onRefreshListener != null && !isLoading() ){
            onRefreshListener.onRefresh();
        }
    }

    @Override
    public void onLoading() {
        if(onLoadingListener != null && !isRefreshing() && !isLoading() && !isNoMore()){
            onLoadingListener.onLoading();
            setLoading(true, false);
        }
    }

    public void setData(List<?> data, boolean hasMore){
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();

        setRefreshing(false);
        setLoading(false, !hasMore);
    }

    public void addData(List<?> data, boolean hasMore){
        oneAdapter.addData(data);
        oneAdapter.notifyDataSetChanged();

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
