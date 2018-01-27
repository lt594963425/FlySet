package com.mvp.lt.flyset.blue.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 */

public class DeviceListAdapter extends RecyclerView.Adapter {
    private List<BluetoothDevice> mDeviceList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    private ItemOnLongClickListener mItemOnLongClickListener;

    public void setOnItemClickListener(DeviceListAdapter.ItemClickListener listener) {
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

    public void clear() {
        mDeviceList.clear();
    }

    public DeviceListAdapter(Context context, List<BluetoothDevice> deviceList) {
        this.mDeviceList = deviceList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BluetoothDevice item = mDeviceList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.deviceInfo.setText(item.getName() + "\n" + item.getAddress());

        Log.e("item", item.getName()+item.getAddress()+item.getType() + ":::" + item.getUuids());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemOnLongClickListener.onItemOnLongClick(position);
                return true;  //为true消耗事件，false不消耗事件 继续传递
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
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDeviceList == null ? 0 : mDeviceList.size();

    }


    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_info)
        TextView deviceInfo;
        @BindView(R.id.device_status)
        TextView deviceStatus;
        View itemView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }


}

