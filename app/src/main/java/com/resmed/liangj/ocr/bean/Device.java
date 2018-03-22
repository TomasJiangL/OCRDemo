package com.resmed.liangj.ocr.bean;

/**
 * Created by LiangJ on 9/03/2018.
 */

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Entity mapped to table "DEVICE".
 */
@Entity(indexes = {
        @Index(value = "machineCode, date DESC", unique = true)
})
public class Device {
    @Id
    private Long id;

    @NotNull
    private String machineCode;//机器码
    private String boxCode;// 盒子码
    private String simCode;// sim码
    private Date date;//插入时间
    private String formateTime;

    @Generated(hash = 855233323)
    public Device(Long id, @NotNull String machineCode, String boxCode,
            String simCode, Date date, String formateTime) {
        this.id = id;
        this.machineCode = machineCode;
        this.boxCode = boxCode;
        this.simCode = simCode;
        this.date = date;
        this.formateTime = formateTime;
    }

    @Generated(hash = 1469582394)
    public Device() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineCode() {
        return this.machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getBoxCode() {
        return this.boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getSimCode() {
        return this.simCode;
    }

    public void setSimCode(String simCode) {
        this.simCode = simCode;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormateTime() {
        return this.formateTime;
    }

    public void setFormateTime(String formateTime) {
        this.formateTime = formateTime;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", machineCode='" + machineCode + '\'' +
                ", boxCode='" + boxCode + '\'' +
                ", simCode='" + simCode + '\'' +
                ", date=" + date +
                ", formateTime='" + formateTime + '\'' +
                '}';
    }
}
