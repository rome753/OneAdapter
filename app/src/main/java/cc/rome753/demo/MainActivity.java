package cc.rome753.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.demo.databinding.ItemPersonBinding;
import cc.rome753.demo.oneadapter.OneAdapter;
import cc.rome753.demo.oneadapter.OneListener;
import cc.rome753.demo.oneadapter.OneViewHolder;
import cc.rome753.demo.oneadapter.OneViewHolderBinder;

public class MainActivity extends AppCompatActivity {

    OneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = new TextView(this);
        tv.setText("header");

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
                            public void bindView(int position, String s) {
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

                        return new OneViewHolder<Long>(parent, android.R.layout.simple_list_item_1) {
                            @Override
                            public void bindView(int position, Long o) {
                                TextView tv1 = itemView.findViewById(android.R.id.text1);
                                tv1.setText("time: " + o);
                            }
                        };
                    }
                },
                new OneListener() {

                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return o instanceof Person;
                    }

                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {

                        return new OneViewHolderBinder<Person, ItemPersonBinding>(parent, R.layout.item_person) {
                            @Override
                            protected void bingingView(int position, Person o) {
                                binding.setPerson(o);
                                binding.executePendingBindings();
                            }
                        }.getOneViewHolder();
                    }
                },
                new OneListener() {
                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return position == 0;
                    }

                    @NonNull
                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {
                        return new OneViewHolder(tv) {
                            @Override
                            public void bindView(int position, Object o) {
                                //not need
                            }
                        };
                    }
                }
        );

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(adapter.getData().get(position) instanceof String){
                    return 1;
                }
                return 2;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        requestData();
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        data.add(null);
        for(int i = 'A'; i <= 'z'; i++){
            String s = (char)i + "";
            data.add(s);
        }
        data.add(3, new Person("Bill", 22));
        data.add(10, new Person("Chris", 10));
        data.add(25, new Person("David", 3));
        adapter.setData(data);
    }

}
