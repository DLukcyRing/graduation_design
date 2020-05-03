package com.graduation.demo.common.entity;

import java.io.Serializable;

public class Company implements Serializable {
    private String companyid;

    private String contact;

    private Integer telephone;

    private String companyname;

    private String USCC;

    private String address;

    private String registeredCapital;

    private String businessScope;

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
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
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
                ", registeredCapital='" + registeredCapital + '\'' +
                ", businessScope='" + businessScope + '\'' +
                '}';
    }
}