package com.resmed.liangj.ocr.bean.httpbean;

import java.util.List;

/**
 * Created by LiangJ on 28/03/2018.
 */

public class TvProgram {

    /**
     * tvid : 435
     * name : CCTV-3（综艺）
     * date : 2015-08-09
     * program : [{"name":"综艺喜乐汇","starttime":"01:18"},{"name":"综艺喜乐汇","starttime":"02:36"},{"name":"2014中国梦-我梦最美","starttime":"03:55"}]
     */

    private String tvid;
    private String name;
    private String date;
    private List<Program> program;

    public String getTvid() {
        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Program> getProgram() {
        return program;
    }

    public void setProgram(List<Program> program) {
        this.program = program;
    }

    public static class Program {
        /**
         * name : 综艺喜乐汇
         * starttime : 01:18
         */

        private String name;
        private String starttime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }
    }
}
