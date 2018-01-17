package com.mvp.lt.flyset.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp.lt.flyset.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 任务适配器
 *
 * @author LiuTao
 *         <p>
 *         status 状态，0：已发布1：正在执行2：已完成
 */

public class TaskCompleteAdapter extends RecyclerView.Adapter {

    private List<String> mList;

    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    private ItemOnLongClickListener mItemOnLongClickListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setItemOnLongClickListener(ItemOnLongClickListener itemOnLongClickListener) {
        mItemOnLongClickListener = itemOnLongClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemOnLongClickListener {
        void onItemOnLongClick(int position);
    }

    public TaskCompleteAdapter(Context context, List<String> stepList) {
        this.mList = stepList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.tvName.setText(mList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mItemClickListener.onItemClick(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemOnLongClickListener.onItemOnLongClick(position);
                return true;  //为true消耗事件，false不消耗事件 继续传递
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();

    }


    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_item)
        TextView tvName;
        View itemView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }


}

