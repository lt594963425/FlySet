package com.mvp.lt.flyset.task.entity;

import java.util.List;

/**
 * 任务列表
 * Created by caoyu on 2017/12/8/008.
 */

public  class Task {
    private int total;

    private int per_page;

    private int current_page;

    private int last_page;

    private List<Data> data;

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPer_page() {
        return this.per_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCurrent_page() {
        return this.current_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getLast_page() {
        return this.last_page;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }

    public static class Data {
        private int id;

        private String recevier_name;

        private String name;

        private String type;

        private String content;

        private String position;

        private String recevier;

        private String create_time;

        private String deadline;
        private String region;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setRecevier_name(String recevier_name) {
            this.recevier_name = recevier_name;
        }

        public String getRecevier_name() {
            return this.recevier_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPosition() {
            return this.position;
        }


        public void setRecevier(String recevier) {
            this.recevier = recevier;
        }

        public String getRecevier() {
            return this.recevier;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getDeadline() {
            return this.deadline;
        }


    }


}
