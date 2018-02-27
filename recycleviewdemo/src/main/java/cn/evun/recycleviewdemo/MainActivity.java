package cn.evun.recycleviewdemo;

import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private List<String> dates;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

        dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dates.add("和谐" + i);
        }
    }

    private void initView() {

        mList = (RecyclerView) findViewById(R.id.list);
        final MyAdapter adapter = new MyAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mList.setLayoutManager(manager);
        mList.setAdapter(adapter);
        mList.addOnItemTouchListener(new OnRecyclerItemClickListener(mList) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder vh) {

                Toast.makeText(MainActivity.this,"aaaaa",Toast.LENGTH_SHORT).show();
                View itemView = vh.itemView;
            }

            @Override
            public void OnLongItemClick(RecyclerView.ViewHolder vh) {
                Toast.makeText(MainActivity.this, "长按条目" + vh.getLayoutPosition() + "==" + vh.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                if (vh.getLayoutPosition()!=0){
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {


            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }


            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int dargFlags;
                int swipeFlags;
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

                    dargFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                } else {

                    dargFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                            | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags=0;

                }
                return makeMovementFlags(dargFlags, swipeFlags);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {

                    viewHolder.itemView.setBackgroundColor(Color.BLUE);
                    Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Service.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (toPosition==0){
                    return false;
                }
                if (fromPosition < toPosition) {

                    for (int i = fromPosition; i < toPosition; i++) {

                        Collections.swap(dates, i, i + 1);

                    }
                } else {

                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(dates, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);
                dates.remove(position);
            }
        });


        itemTouchHelper.attachToRecyclerView(mList);
    }

    public static final int NORMAL_TYPE = 0;
    public static final int IMAGE_TYPE = 1;

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == NORMAL_TYPE) {
                return new NormalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false));
            } else {
                return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false));

            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof NormalHolder) {
                ((NormalHolder) holder).tv2.setText(dates.get(position));
            } else {
                ((ImageHolder) holder).tv1.setText(dates.get(position));
                ((ImageHolder) holder).ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dates.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 4 == 0) {
                return NORMAL_TYPE;
            } else {
                return IMAGE_TYPE;
            }
        }


    }

    class NormalHolder extends RecyclerView.ViewHolder {

        TextView tv2;

        public NormalHolder(View itemView) {
            super(itemView);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
        }

    }

    class ImageHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        ImageView iv;
        private final LinearLayout ll;

        public ImageHolder(View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
