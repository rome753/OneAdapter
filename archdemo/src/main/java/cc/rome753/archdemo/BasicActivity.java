package cc.rome753.archdemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.rome753.oneadapter.base.OneTemplate;
import cc.rome753.oneadapter.base.OneViewHolder;
import cc.rome753.oneadapter.refresh.FooterAdapter;
import cc.rome753.oneadapter.refresh.LoadingLayout;
import cc.rome753.oneadapter.refresh.RecyclerLayout;

public class BasicActivity extends AppCompatActivity {

    RecyclerLayout recyclerLayout;
    PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerLayout = new RecyclerLayout(this);
        setContentView(recyclerLayout);

        FooterAdapter oneAdapter = new FooterAdapter();
        oneAdapter.register(
                new OneTemplate() {

                    @Override
                    public boolean isMatch(int position, Object o) {
                        return true;
                    }

                    @Override
                    public OneViewHolder getViewHolder(ViewGroup parent) {

                        return new OneViewHolder<Person>(parent, R.layout.item_person) {
                            @Override
                            protected void bindViewCasted(int position, Person p) {
                                ((TextView)itemView.findViewById(R.id.tv_name)).setText(p.getName());
                                ((TextView)itemView.findViewById(R.id.tv_age)).setText(String.valueOf(p.getAge()));
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
        recyclerLayout.setRefreshing(true);

        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personViewModel.getLiveData().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                Log.e("chao", Thread.currentThread().getName() + " onChanged size " + (people == null ? 0 : people.size()));
                if(people != null) {
                    if(page == 0) {
                        recyclerLayout.setData(people, page++ < 2);
                    } else {
                        recyclerLayout.addData(people, page++ < 2);
                    }
                }
            }
        });

        requestData();
    }

    int page;

    private void requestData() {
        page = 0;
        personViewModel.reload();
    }

    private void requestMoreData() {
        personViewModel.reload();
    }
}
