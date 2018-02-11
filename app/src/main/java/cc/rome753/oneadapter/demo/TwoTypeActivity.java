package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.oneadapter.R;
import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneListener;
import cc.rome753.oneadapter.base.OneViewHolder;

public class TwoTypeActivity extends AppCompatActivity {

    OneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new OneAdapter(
                new OneListener() {

                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return o instanceof String;
                    }

                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {

                        return new OneViewHolder<String>(parent, android.R.layout.activity_list_item) {
                            @Override
                            protected void bindViewCasted(int position, String s) {
                                TextView text1 = itemView.findViewById(android.R.id.text1);
                                text1.setText(s);
                                ImageView icon = itemView.findViewById(android.R.id.icon);
                                icon.setImageResource(R.mipmap.ic_launcher);
                            }
                        };
                    }
                },
                new OneListener() {

                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return o instanceof Long;
                    }

                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {

                        return new OneViewHolder<Long>(parent, R.layout.item_text) {
                            @Override
                            protected void bindViewCasted(int position, Long l) {
                                TextView text = itemView.findViewById(R.id.text);
                                text.setText("time: " + l);
                            }
                        };
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        requestData();
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        for(int i = 'A'; i <= 'z'; i++){
            data.add(String.valueOf((char)i));
        }
        for(int i = 3; i < 50; i += 6){
            data.add(i, System.nanoTime());
        }
        adapter.setData(data);
    }

}
