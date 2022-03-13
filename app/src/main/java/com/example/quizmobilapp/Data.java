package com.example.quizmobilapp;

public class Data {
    String id,title,slug,finished_at;
    public Data(){

    }
    public Data(String id,String title,String slug,String finished_at){
        this.id=id;
        this.title=title;
        this.slug=slug;
        this.finished_at=finished_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }
}
