# OneAdapter
One is All.


![ComplexList.png](http://upload-images.jianshu.io/upload_images/1896166-3eb6b4ad9d952a60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/240)

## code

OneAdapter

```java
public class OneAdapter extends RecyclerView.Adapter<OneViewHolder> {

    protected final List<Object> mData;
    protected final List<OneTemplate> mTemplates;

    public OneAdapter() {
        mData = new ArrayList<>();
        mTemplates = new ArrayList<>();
    }

    public OneAdapter register(OneTemplate oneTemplate) {
        mTemplates.add(oneTemplate);
        return this;
    }

    public void setData(List<?> data) {
        mData.clear();
        mData.addAll(data);
    }

    public void addData(List<?> data) {
        mData.addAll(data);
    }

    public List<Object> getData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mData.get(position);
        for (int i = 0; i < mTemplates.size(); i++) {
            OneTemplate listener = mTemplates.get(i);
            if (listener.isMyItemViewType(position, o)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTemplates.get(viewType).getMyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        Object o = mData.get(position);
        holder.bindView(position, o);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
```

OneTemplate

```java
public interface OneTemplate {

    /**
     * Is the position or the data matches this OneTemplate?
     * @param position the data's position int the list
     * @param o the data
     * @return true/false
     */
    boolean isMatch(int position, Object o);

    /**
     * Create a ViewHolder for this OneTemplate
     * @param parent RecyclerView
     * @return OneViewHolder
     */
    OneViewHolder getViewHolder(ViewGroup parent);
}
```

OneViewHolder

```java
public abstract class OneViewHolder<D> extends RecyclerView.ViewHolder {

    public OneViewHolder(View itemView) {
        super(itemView);
        init();
    }

    public OneViewHolder(ViewGroup parent, int layoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
        init();
    }

    protected void init(){
        // findViewById if need.
    }

    void bindView(int position, Object o){
        bindViewCasted(position, (D) o);
    }

    protected abstract void bindViewCasted(int position, D d);
}
```

## DataBinding support

OneViewHolderWrapper

```java
public abstract class OneViewHolderWrapper<D,B extends ViewDataBinding>{

    private OneViewHolder<D> oneViewHolder;

    protected B binding;

    public OneViewHolderWrapper(ViewGroup parent, int layoutRes){
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutRes, parent, false);
        oneViewHolder = new OneViewHolder<D>(binding.getRoot()) {
            @Override
            protected void bindViewCasted(int position, D d) {
                OneViewHolderWrapper.this.bindViewCasted(position, d);
            }
        };
    }

    public OneViewHolder<D> asOneViewHolder() {
        return oneViewHolder;
    }

    protected abstract void bindViewCasted(int position, D d);
}
```

## Use

#### A simple list
```java
public class SimpleListActivity extends AppCompatActivity {

    OneAdapter oneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneAdapter = new OneAdapter().register(new OneTemplate() {
            @Override
            public boolean isMatch(int position, Object o) {
                return true;
            }

            @Override
            public OneViewHolder getViewHolder(ViewGroup parent) {
                return new OneViewHolder<String>(parent, R.layout.item_text){

                    @Override
                    protected void bindViewCasted(int position, String s) {
                        TextView text = itemView.findViewById(R.id.text);
                        text.setText(s);
                    }
                };
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(oneAdapter);

        requestData();
    }

    private void requestData() {
        List<String> data = new ArrayList<>();
        for(int i = 'A'; i <= 'z'; i++) {
            data.add(" " + (char)i);
        }
        oneAdapter.setData(data);
        oneAdapter.notifyDataSetChanged();
    }
}
```

#### A complex list
```java
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
```


[中文文档](https://www.jianshu.com/p/7ef4914d0bdf)