package dev.mvc.newscategrp;

import java.sql.Date;

public class NewscategrpVO {
    private int newscategrpno;
    private String name;
    private int seqno;
    private String visible;
    private Date rdate;

    // Getters and Setters
    public int getNewscategrpno() {
        return newscategrpno;
    }

    public void setNewscategrpno(int newscategrpno) {
        this.newscategrpno = newscategrpno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }
}
