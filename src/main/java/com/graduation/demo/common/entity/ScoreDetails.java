package com.graduation.demo.common.entity;

import java.io.Serializable;

public class ScoreDetails implements Serializable {
    private Integer identifier;

    private String userid;

    private Integer addReduce;

    private Double score;

    private String checkerid;

    private String registrarid;

    private String plead;

    private String checkerid2;

    private String approvalid;

    private static final long serialVersionUID = 1L;

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getAddReduce() {
        return addReduce;
    }

    public void setAddReduce(Integer addReduce) {
        this.addReduce = addReduce;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCheckerid() {
        return checkerid;
    }

    public void setCheckerid(String checkerid) {
        this.checkerid = checkerid;
    }

    public String getRegistrarid() {
        return registrarid;
    }

    public void setRegistrarid(String registrarid) {
        this.registrarid = registrarid;
    }

    public String getPlead() {
        return plead;
    }

    public void setPlead(String plead) {
        this.plead = plead;
    }

    public String getCheckerid2() {
        return checkerid2;
    }

    public void setCheckerid2(String checkerid2) {
        this.checkerid2 = checkerid2;
    }

    public String getApprovalid() {
        return approvalid;
    }

    public void setApprovalid(String approvalid) {
        this.approvalid = approvalid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", identifier=").append(identifier);
        sb.append(", userid=").append(userid);
        sb.append(", addReduce=").append(addReduce);
        sb.append(", score=").append(score);
        sb.append(", checkerid=").append(checkerid);
        sb.append(", registrarid=").append(registrarid);
        sb.append(", plead=").append(plead);
        sb.append(", checkerid2=").append(checkerid2);
        sb.append(", approvalid=").append(approvalid);
        sb.append("]");
        return sb.toString();
    }
}