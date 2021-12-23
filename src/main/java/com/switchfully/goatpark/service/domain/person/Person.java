package com.switchfully.goatpark.service.domain.person;

import com.switchfully.goatpark.service.domain.address.Address;
import com.switchfully.goatpark.service.domain.person.emailaddress.EmailAddress;
import com.switchfully.goatpark.service.domain.person.membership.LicensePlate;
import com.switchfully.goatpark.service.domain.person.membership.Membership;
import com.switchfully.goatpark.service.domain.person.phonenumber.PhoneNumber;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(generator = "person_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "FK_PHONE_NUMBER")
    @ManyToOne(cascade = {CascadeType.ALL})
    private PhoneNumber phoneNumber;

    @JoinColumn(name = "FK_MOBILE_NUMBER")
    @ManyToOne(cascade = {CascadeType.ALL})
    private PhoneNumber mobileNumber;

    @JoinColumn(name = "FK_EMAIL_ADDRESS")
    @OneToOne(cascade = {CascadeType.ALL})
    private EmailAddress emailAddress;

    @JoinColumn(name = "FK_ADDRESS")
    @ManyToOne(cascade = {CascadeType.ALL})
    private Address address;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "fk_membership_id")
    private Membership membership;

    public Person(String name, PhoneNumber phoneNumber, PhoneNumber mobileNumber, EmailAddress emailAddress, Address address, Membership membership) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.membership = membership;
    }

    protected Person() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // TODO: this will have to become obligated
    public PhoneNumber getPhoneNumber() {
        if (mobileNumber == null) {
            return new PhoneNumber("null", "null");
        }
        return phoneNumber;
    }

    public PhoneNumber getMobileNumber() {
        if (mobileNumber == null) {
            return new PhoneNumber("null", "null");
        }
        return mobileNumber;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public Address getAddress() {
        return address;
    }

    public Membership getMembership() {
        return membership;
    }
}
