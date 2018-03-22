package com.resmed.liangj.ocr.bean.eventbus;

import com.resmed.liangj.ocr.bean.Device;

import java.util.List;

/**
 * Created by LiangJ on 14/03/2018.
 */

public class DataBaseEvent {
    List<Device> devices;
    public DataBaseEvent(List<Device> list){
        this.devices = list;
    }
    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
