package com.tzltce.techzephyr.model;

/**
 * Created by Chetan on 13-12-2015.
 */
public class Detail {
    String id;
    String drawable;
    String backdrop;
    String name;
    String head;
    String head_no;
    String fees;
    String prize;
    String description;
    String group;
    public boolean box;
    // constructors
    public Detail() {
    }

    public Detail(String id, String name, String head_no) {
        this.id = id;
        this.name = name;
        this.head_no = head_no;
    }

    public Detail(String name,boolean box) {
        this.name = name;
        this.box = box;
    }

    public Detail(String id, String drawable,String backdrop, String name, String head, String head_no, String fees, String description, String group) {
        this.id = id;
        this.drawable = drawable;
        this.backdrop = backdrop;
        this.name = name;
        this.head = head;
        this.head_no = head_no;
        this.fees = fees;
        this.description = description;
        this.group = group;
    }

    public Detail(String id, String drawable, String backdrop, String name, String head, String head_no, String fees, String prize, String description, String group) {
        this.id = id;
        this.drawable = drawable;
        this.backdrop = backdrop;
        this.name = name;
        this.head = head;
        this.head_no = head_no;
        this.fees = fees;
        this.prize = prize;
        this.description = description;
        this.group = group;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setHeadNo(String head_no){
        this.head_no = head_no;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // getters
    public String getId() {
        return this.id;
    }
    public String getDrawable() {
        return this.drawable;
    }
    public String getBackdrop(){
        return this.backdrop;
    }
    public String getName() {
        return this.name;
    }
    public String getHead() {
        return this.head;
    }
    public String getHeadNo() {
        return this.head_no;
    }
    public String getFees() {
        return this.fees;
    }
    public String getPrize() {
        return this.prize;
    }
    public String getDescription() {
        return this.description;
    }
    public String getGroup() {
        return this.group;
    }

}
