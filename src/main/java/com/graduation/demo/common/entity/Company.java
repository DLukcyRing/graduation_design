package com.graduation.demo.common.entity;

import java.io.Serializable;

public class Company implements Serializable {
    private String companyid;

    private String contact;

    private String telephone;

    private String companyname;

    private String USCC;

    private String address;

    private String registered_capital;

    private String business_scope;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public String getRegisteredCapital() {
        return registered_capital;
    }

    public void setRegisteredCapital(String registered_capital) {
        this.registered_capital = registered_capital;
    }

    public String getBusinessScope() {
        return business_scope;
    }

    public void setBusinessScope(String business_scope) {
        this.business_scope = business_scope;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyid='" + companyid + '\'' +
                ", contact='" + contact + '\'' +
                ", telephone=" + telephone +
                ", companyname='" + companyname + '\'' +
                ", USCC='" + USCC + '\'' +
                ", address='" + address + '\'' +
                ", registered_capital='" + registered_capital + '\'' +
                ", businessScope='" + business_scope + '\'' +
                '}';
    }
}