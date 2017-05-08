package th.co.smk.smkagent;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

import layout.FragmentDetail;

/**
 * Created by smk on 26/4/2560.
 */

public class Item1InsuranceProcessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private ArrayList<Item1InsuranceProcess> itemArrayList;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public Item1InsuranceProcessAdapter(RecyclerView recyclerView,Context context, ArrayList<Item1InsuranceProcess> itemArrayList) {
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

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.from(parent.getContext()).inflate(R.layout.item_cardview_fragment1_insurance_data_1, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }else if (viewType == VIEW_TYPE_LOADING){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            LoadingViewHolder loadingViewHolder = new LoadingViewHolder(view);
            return loadingViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Item1InsuranceProcess item = itemArrayList.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.id_insurance.setText(item.id);
            itemViewHolder.username_insurance.setText(item.name);
            itemViewHolder.day_left.setText(String.valueOf(item.day_left));
            itemViewHolder.day_left_unit.setText(item.day_left_unit);
            itemViewHolder.status_Process.setText(item.status_process);
            itemViewHolder.time_alert.setText(item.time_alert);
            holder.itemView.setOnClickListener(this);
            itemViewHolder.bt_add_alert.setOnClickListener(this);
            itemViewHolder.bt_call.setOnClickListener(this);
            if (item.day_left < 15){
                itemViewHolder.image_day_left.setColorFilter(ContextCompat.getColor(context,R.color.cardview_bg2_red));
            }else if (item.day_left < 45) {
                itemViewHolder.image_day_left.setColorFilter(ContextCompat.getColor(context,R.color.cardview_bg1_orange));
            }else {
                itemViewHolder.image_day_left.setColorFilter(ContextCompat.getColor(context,R.color.cardview_bg0_normal));
            }
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


    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        switch (view.getId()) {
            case R.id.cv_item_InsuranceProcess:
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentDetail myFragment = new FragmentDetail();
                activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,
                                R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.content,myFragment).addToBackStack(null).commit();

                break;
            case R.id.bt_alertAdd:
                new AlertDialog.Builder(context)
                        .setTitle("Add alert time")
                        .setMessage("Example add alert time.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.bt_call:
                new AlertDialog.Builder(context)
                        .setTitle("Call to customer")
                        .setMessage("Exampel Will open contact.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cvItem;
        public TextView id_insurance;
        public TextView username_insurance;
        public TextView status_Process;
        public TextView day_left;
        public TextView day_left_unit;
        public ImageView image_day_left;
        public TextView time_alert;
        public Button bt_call;
        public Button bt_add_alert;
        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_item_InsuranceProcess);
            id_insurance = (TextView) itemView.findViewById(R.id.tv_id);
            username_insurance = (TextView) itemView.findViewById(R.id.tv_name);
            status_Process = (TextView) itemView.findViewById(R.id.tv_status);
            day_left = (TextView) itemView.findViewById(R.id.tv_day_left);
            day_left_unit = (TextView) itemView.findViewById(R.id.tv_day_left_unit);
            image_day_left = (ImageView) itemView.findViewById(R.id.imv_iconTimer);
            time_alert = (TextView) itemView.findViewById(R.id.tv_alert_time);
            bt_call = (Button) itemView.findViewById(R.id.bt_call);
            bt_add_alert = (Button) itemView.findViewById(R.id.bt_alertAdd);
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
