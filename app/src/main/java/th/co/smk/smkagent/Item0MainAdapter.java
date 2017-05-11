package th.co.smk.smkagent;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by smk on 9/5/2560.
 */

public class Item0MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Item0Main> itemArrayList;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public Item0MainAdapter(RecyclerView recyclerView,Context context, ArrayList<Item0Main> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
        Toast.makeText(this.context, "Item0MainAdapter - "+itemArrayList.size(), Toast.LENGTH_SHORT).show();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext()).inflate(R.layout.item_cardview_fragment0_main_menu1, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        Toast.makeText(this.context, "onCreateViewHolder", Toast.LENGTH_SHORT).show();
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item0Main item = itemArrayList.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.menu_name.setText(item.menu_name);
        itemViewHolder.menu_img_icon.setImageResource(item.menu_path_icon);
        Toast.makeText(this.context, "onBindViewHolder", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        Toast.makeText(this.context, "Count : "+itemArrayList.size(), Toast.LENGTH_SHORT).show();
        if (itemArrayList.size() != 0){
            return itemArrayList.size();
        }
        return 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        public TextView menu_name;
        public ImageView menu_img_icon;
        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_item_mainMenu);
            menu_name = (TextView) itemView.findViewById(R.id.tv_main_name);
            menu_img_icon = (ImageView) itemView.findViewById(R.id.img_main_icon);
        }
    }
}
