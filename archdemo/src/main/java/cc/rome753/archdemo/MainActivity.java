package cc.rome753.archdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cc.rome753.oneadapter.base.OneAdapter;
import cc.rome753.oneadapter.base.OneTemplate;
import cc.rome753.oneadapter.base.OneViewHolder;

public class MainActivity extends AppCompatActivity {

    private OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oneAdapter = new OneAdapter(

                new OneTemplate() {
                    @Override
                    public boolean isMyItemViewType(int position, Object o) {
                        return true;
                    }

                    @Override
                    public OneViewHolder getMyViewHolder(ViewGroup parent) {
                        return new OneViewHolder<String>(parent, android.R.layout.simple_list_item_1) {

                            TextView tvName;
                            @Override
                            protected void init() {
                                tvName = itemView.findViewById(android.R.id.text1);
                            }

                            @Override
                            protected void bindViewCasted(int position, String o) {
                                tvName.setText(o);
                                itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(MainActivity.this, BasicActivity.class));
                                    }
                                });
                            }
                        };
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        List<String> data = Collections.singletonList("basic");
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }
}
