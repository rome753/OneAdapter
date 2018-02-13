package cc.rome753.oneadapter.refresh;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cc.rome753.oneadapter.R;

public class LoadingLayout extends FrameLayout {

    private boolean isLoading;
    private boolean isNoMore;

    private ProgressBar viewLoading;
    private TextView viewNoMore;

    public LoadingLayout(Context context) {
        super(context);
        inflate(context, R.layout.layout_loading, this);
        viewLoading = findViewById(R.id.pb_loading);
        viewNoMore = findViewById(R.id.tv_no_more);
        setLoading(false, true);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isNoMore() {
        return isNoMore;
    }

    public void setLoading(boolean isLoading, boolean isNoMore) {
        this.isLoading = isLoading;
        this.isNoMore = isNoMore;
        viewNoMore.setVisibility(isNoMore ? VISIBLE : GONE);
        viewLoading.setVisibility(!isNoMore && isLoading ? VISIBLE : GONE);
    }

    public interface OnLoadingListener{
        void onLoading();
    }

}