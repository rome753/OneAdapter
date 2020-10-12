package cc.rome753.oneadapter.refresh;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneTemplate;
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

    public FooterAdapter() {
        super();
        mTemplates.add(0, new OneTemplate() {
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
        mData.clear();
        mData.addAll(data);
        mData.add(null); // add null data for footer
    }

    @Override
    public void addData(List<?> data) {
        if(!mData.isEmpty()){  // remove null data for footer
            mData.remove(mData.size() - 1);
        }
        mData.addAll(data);
        mData.add(null); // add null data for footer
    }
}
