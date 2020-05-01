package com.example.parttime.entity.node;

import java.io.Serializable;
/**
 *  Create By  791243928@qq.com
 *
 *
 */
public class AcceptPerson  implements Serializable {
    private String to ;
    private String subject ;
    private String content ;
    private String date ;
    private int id ;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
