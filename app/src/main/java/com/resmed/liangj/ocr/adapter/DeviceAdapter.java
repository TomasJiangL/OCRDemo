package com.resmed.liangj.ocr.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resmed.liangj.ocr.R;
import com.resmed.liangj.ocr.bean.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangJ on 9/03/2018.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.NoteViewHolder> {

    private DeviceClickListener clickListener;
    private List<Device> dataset;

    public interface DeviceClickListener {
        void onDeviceClick(int position);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView index;
        public TextView machineCode;
        public TextView boxCode;
        public TextView simCode;
        public TextView storeTime;

        public NoteViewHolder(View itemView, final DeviceClickListener clickListener) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.text_index);
            machineCode = (TextView) itemView.findViewById(R.id.text_machine);
            boxCode = (TextView) itemView.findViewById(R.id.text_box);
            simCode = (TextView) itemView.findViewById(R.id.text_sim);
            storeTime = (TextView) itemView.findViewById(R.id.text_store_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onDeviceClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public DeviceAdapter(DeviceClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Device>();
    }

    public void setDevices(@NonNull List<Device> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    public Device getDevice(int position) {
        return dataset.get(position);
    }

    @Override
    public DeviceAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new NoteViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.NoteViewHolder holder, int position) {
        Device device = dataset.get(position);
        if (device != null) {
            holder.index.setText("序号: " + (position + 1));
            holder.machineCode.setText("机器码: " + device.getMachineCode());
            holder.boxCode.setText("盒子码: " + device.getBoxCode());
            holder.simCode.setText("sim码: " + device.getSimCode());
            holder.storeTime.setText("入库时间: " + device.getFormateTime());
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
