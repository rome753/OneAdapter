package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneTemplate;
import cc.rome753.oneadapter.base.OneViewHolder;

public class HeaderFooterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OneAdapter oneAdapter;
    View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter();
        oneAdapter.register(
                new OneTemplate() {
                    @Override
                    public boolean isMatch(int position, Object o) {
                        return position == 0;
                    }

                    @Override
                    public OneViewHolder getViewHolder(ViewGroup parent) {
                        return new OneViewHolder<Object>(parent, R.layout.item_text) {

                            @Override
                            protected void bindViewCasted(int position, Object o) {
                                TextView text = itemView.findViewById(R.id.text);
                                text.setText("This is header");
                            }
                        };
                    }
                })
                .register(
                        new OneTemplate() {
                            @Override
                            public boolean isMatch(int position, Object o) {
                                return o instanceof String;
                            }

                            @Override
                            public OneViewHolder getViewHolder(ViewGroup parent) {
                                return new OneViewHolder<String>(parent, android.R.layout.simple_list_item_1) {

                                    @Override
                                    protected void bindViewCasted(int position, String s) {
                                        TextView text = itemView.findViewById(android.R.id.text1);
                                        text.setText(s);
                                    }
                                };
                            }
                        })
                .register(
                        new OneTemplate() {
                            @Override
                            public boolean isMatch(int position, Object o) {
                                return position == oneAdapter.getItemCount() - 1;
                            }

                            @Override
                            public OneViewHolder getViewHolder(ViewGroup parent) {
                                return new OneViewHolder<Object>(footerView) {

                                    @Override
                                    protected void bindViewCasted(int position, Object o) {
                                    }
                                };
                            }
                        }
                );

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        initFooterView();
        requestData();
    }

    private void initFooterView() {
        footerView = LayoutInflater.from(this).inflate(R.layout.item_text, recyclerView, false);
        ((TextView) footerView.findViewById(R.id.text)).setText("This is footer");
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        data.add(null);
        for (int i = 'A'; i <= 'Z'; i++) {
            data.add(" " + (char) i);
        }
        data.add(null);
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }
}
