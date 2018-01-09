package com.mvp.lt.flyset.simple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.lt.flyset.R;
import com.mvp.lt.flyset.simple.entity.Chat;
import com.mvp.lt.flyset.simple.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VH> {
    private LayoutInflater mInflater;
    private List<Chat> mItems = new ArrayList<>();

    private OnItemClickListener mClickListener;

    public ChatAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Chat> beans) {
        mItems.clear();
        mItems.addAll(beans);
        notifyDataSetChanged();
    }

    public void refreshMsg(Chat bean) {
        int index = mItems.indexOf(bean);
        if (index < 0) return;

        notifyItemChanged(index);
    }

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wechat_chat, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(parent.getContext(),"sds"+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Chat item = mItems.get(position);
        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);
        holder.tvTime.setText("2 days ago");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mClickListener = listener;
    }

    public Chat getMsg(int position) {
        return mItems.get(position);
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName, tvMsg, tvTime;

        public VH(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}