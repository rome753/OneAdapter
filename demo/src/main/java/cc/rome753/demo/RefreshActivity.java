package cc.rome753.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.oneadapter.R;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;
import cc.rome753.oneadapter.refresh.FooterAdapter;
import cc.rome753.oneadapter.refresh.LoadingLayout;
import cc.rome753.oneadapter.refresh.RecyclerLayout;

public class RefreshActivity extends AppCompatActivity {

    RecyclerLayout recyclerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerLayout = new RecyclerLayout(this);
        setContentView(recyclerLayout);

        FooterAdapter oneAdapter = new FooterAdapter(
                new OneListener() {

                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return true;
                    }

                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {

                        return new OneViewHolder<String>(parent, R.layout.item_text) {
                            @Override
                            protected void bindViewCasted(int position, String s) {
                                TextView text = itemView.findViewById(R.id.text);
                                text.setText(s);
                            }
                        };
                    }
                }
        );

        recyclerLayout.init(oneAdapter,
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        requestData();
                    }
                },
                new LoadingLayout.OnLoadingListener() {
                    @Override
                    public void onLoading() {
                        requestMoreData();
                    }
                }
        );
        recyclerLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerLayout.setSpan(2, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });

        recyclerLayout.setRefreshing(true);
        requestData();
    }

    int page;

    private void requestData() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<Object> data = new ArrayList<>();
                for (int i = 'A'; i <= 'Z'; i++) {
                    String s = (char) i + " " + System.nanoTime();
                    data.add(s);
                }

                page = 0;
                recyclerLayout.setData(data, page++ < 2);

            }
        }, 1000);
    }

    private void requestMoreData() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<Object> data = new ArrayList<>();
                for (int i = 'A'; i <= 'Z'; i++) {
                    String s = (char) i + " " + System.nanoTime();
                    data.add(s);
                }

                recyclerLayout.addData(data, page++ < 2);

            }
        }, 1000);
    }

}
