package com.mvp.lt.flyset.task.entity;

import java.util.List;

/**
 * 任务列表-任务详情
 *
 * @author ${LiuTao}
 * @date 2017/12/12/012
 */

public class TaskDetails {
    private String code;

    private Var var;

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setVar(Var var){
        this.var = var;
    }
    public Var getVar(){
        return this.var;
    }

    public class Var {
        private List<Assigner> assigner;

        private String content;

        private String create_time;

        private String deadline;

        private String finish_time;

        private String founder;

        private String founder_name;

        private int id;

        private List<Images> images;

        private String name;

        private int position_type;

        private String positions;

        private String recevier;

        private String recevier_name;

        private String result;

        private int status;

        private String type;

        private String update_time;

        public void setAssigner(List<Assigner> assigner){
            this.assigner = assigner;
        }
        public List<Assigner> getAssigner(){
            return this.assigner;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setCreate_time(String create_time){
            this.create_time = create_time;
        }
        public String getCreate_time(){
            return this.create_time;
        }
        public void setDeadline(String deadline){
            this.deadline = deadline;
        }
        public String getDeadline(){
            return this.deadline;
        }
        public void setFinish_time(String finish_time){
            this.finish_time = finish_time;
        }
        public String getFinish_time(){
            return this.finish_time;
        }
        public void setFounder(String founder){
            this.founder = founder;
        }
        public String getFounder(){
            return this.founder;
        }
        public void setFounder_name(String founder_name){
            this.founder_name = founder_name;
        }
        public String getFounder_name(){
            return this.founder_name;
        }
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setImages(List<Images> images){
            this.images = images;
        }
        public List<Images> getImages(){
            return this.images;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setPosition_type(int position_type){
            this.position_type = position_type;
        }
        public int getPosition_type(){
            return this.position_type;
        }
        public void setPositions(String positions){
            this.positions = positions;
        }
        public String getPositions(){
            return this.positions;
        }
        public void setRecevier(String recevier){
            this.recevier = recevier;
        }
        public String getRecevier(){
            return this.recevier;
        }
        public void setRecevier_name(String recevier_name){
            this.recevier_name = recevier_name;
        }
        public String getRecevier_name(){
            return this.recevier_name;
        }
        public void setResult(String result){
            this.result = result;
        }
        public String getResult(){
            return this.result;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setUpdate_time(String update_time){
            this.update_time = update_time;
        }
        public String getUpdate_time(){
            return this.update_time;
        }




    }

    //任务接受人
    public class Assigner {
        private String name;

        private String uid;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setUid(String uid){
            this.uid = uid;
        }
        public String getUid(){
            return this.uid;
        }
    }

    public class Images {
        private int id;

        private String path;

        private int tid;

        private int use;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setPath(String path){
            this.path = path;
        }
        public String getPath(){
            return this.path;
        }
        public void setTid(int tid){
            this.tid = tid;
        }
        public int getTid(){
            return this.tid;
        }
        public void setUse(int use){
            this.use = use;
        }
        public int getUse(){
            return this.use;
        }
    }
}
