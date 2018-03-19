package cc.rome753.oneadapter.refresh;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;

/**
 * Footer for loading more.
 * Created by rome753 on 18-3-16.
 */

public class FooterAdapter extends OneAdapter {

    private View footerView;

    void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public FooterAdapter(OneListener... oneListeners) {
        super(oneListeners);
        mListeners.add(0, new OneListener() {
            @Override
            public boolean isMyItemViewType(int position, Object o) {
                return position == getItemCount() - 1;
            }

            @Override
            public OneViewHolder getMyViewHolder(ViewGroup parent) {
                return new OneViewHolder(footerView) {
                    @Override
                    protected void bindViewCasted(int position, Object o) {
                        //ignore
                    }
                };
            }
        });
    }

    @Override
    public void setData(List<?> data) {
        data.add(null);
        super.setData(data);
    }

    @Override
    public void addData(List<?> data) {
        if(!mData.isEmpty()){
            mData.remove(mData.size() - 1);
        }
        mData.addAll(data);
        mData.add(null);
    }
}
