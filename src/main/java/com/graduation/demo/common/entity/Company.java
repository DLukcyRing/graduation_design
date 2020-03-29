package com.graduation.demo.common.entity;

import java.io.Serializable;

public class Company implements Serializable {
    private Integer companyid;

    private String contact;

    private Integer telephone;

    private static final long serialVersionUID = 1L;

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", companyid=").append(companyid);
        sb.append(", contact=").append(contact);
        sb.append(", telephone=").append(telephone);
        sb.append("]");
        return sb.toString();
    }
}