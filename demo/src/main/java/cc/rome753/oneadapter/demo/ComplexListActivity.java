package cc.rome753.oneadapter.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.rome753.demo.model.Person;
import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneTemplate;
import cc.rome753.oneadapter.base.OneViewHolder;
import cc.rome753.oneadapter.databinding.OneViewHolderWrapper;
import cc.rome753.oneadapter.demo.databinding.ItemPersonBinding;

public class ComplexListActivity extends AppCompatActivity {

    OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter();
        oneAdapter.register(
                new OneTemplate() {

                    @Override
                    public boolean isMatch(int position, Object o) {
                        return position > 30;
                    }

                    @Override
                    public OneViewHolder getViewHolder(ViewGroup parent) {

                        return new OneViewHolder<String>(parent, R.layout.item_text) {
                            @Override
                            protected void bindViewCasted(int position, String s) {
                                TextView text = itemView.findViewById(R.id.text);
                                text.setText(String.valueOf(System.currentTimeMillis()));
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
                        })
                .register(
                        new OneTemplate() {

                            @Override
                            public boolean isMatch(int position, Object o) {
                                return o instanceof Person;
                            }

                            @Override
                            public OneViewHolder getViewHolder(ViewGroup parent) {

                                return new OneViewHolderWrapper<Person, ItemPersonBinding>(parent, R.layout.item_person) {
                                    @Override
                                    protected void bindViewCasted(int position, Person o) {
                                        binding.setPerson(o);
                                        binding.executePendingBindings();
                                    }
                                }.asOneViewHolder();
                            }
                        }
                );

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 10 || position == 11) {
                    return 3;
                }
                if (position > 10 && position < 26) {
                    return 2;
                }
                if (oneAdapter.getData().get(position) instanceof String) {
                    return 3;
                }
                return 6;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(oneAdapter);

        requestData();
    }

    private void requestData() {
        List<Object> data = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            String s = (char) i + "";
            data.add(s);
        }
        data.add(1, null);
        data.add(3, new Person("Bill", 22));
        data.add(10, new Person("Chris", 10));
        data.add(11, new Person("Tom", 18));
        data.add(26, new Person("David", 3));
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }

}
