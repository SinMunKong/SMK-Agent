package th.co.smk.smkagent;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by smk on 2/5/2560.
 */

public class Item2RemindRenewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Item2RemindRenew> itemArrayList;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;



    public Item2RemindRenewAdapter(RecyclerView recyclerView ,Context context, ArrayList<Item2RemindRenew> itemArrayList) {
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
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.from(parent.getContext()).inflate(R.layout.item_cardview_fragment2_remind_renew1, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            LoadingViewHolder loadingViewHolder = new LoadingViewHolder(view);
            return loadingViewHolder;
        }
        return null;
        /*
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.from(parent.getContext()).inflate(R.layout.item_cardview_fragment2_remind_renew1, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
         */
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Item2RemindRenew item = itemArrayList.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.id_insurance.setText(item.id);
            itemViewHolder.username_insurance.setText(item.name);
            itemViewHolder.status_Process.setText(item.status_process);
            itemViewHolder.license_no.setText(item.license_no);
        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (itemArrayList.size() != 0){
            return itemArrayList.size();
        }
        return 0;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        public TextView id_insurance;
        public TextView username_insurance;
        public TextView status_Process;
        public TextView license_no;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_item_remindRenew);
            id_insurance = (TextView) itemView.findViewById(R.id.tv_id);
            username_insurance = (TextView) itemView.findViewById(R.id.tv_name);
            status_Process = (TextView) itemView.findViewById(R.id.tv_status);
            license_no = (TextView) itemView.findViewById(R.id.tv_license);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }
}
