package com.graduation.demo.common.entity;

import java.io.Serializable;

public class Company implements Serializable {
    private String companyid;

    private String contact;

    private Integer telephone;

    private String name;

    private String USCC;

    private String address;

    private String registered;

    private String business;

    private static final long serialVersionUID = 1L;

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUSCC() {
        return USCC;
    }

    public void setUSCC(String USCC) {
        this.USCC = USCC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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
        sb.append(", name=").append(name);
        sb.append(", USCC=").append(USCC);
        sb.append(", address=").append(address);
        sb.append(", registered=").append(registered);
        sb.append(", business=").append(business);
        sb.append("]");
        return sb.toString();
    }
}