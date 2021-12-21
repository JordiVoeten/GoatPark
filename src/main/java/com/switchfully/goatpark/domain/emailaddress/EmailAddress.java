package com.switchfully.goatpark.domain.emailaddress;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "EMAILADDRESS")
public class EmailAddress {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "DOMAIN")
    private String domain;

    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public EmailAddress(String username, String domain) {
        if(!isValidEmail(username + "@" + domain)) { throw new IllegalArgumentException("This email address is not valid!");
        }
        this.username = username;
        this.domain = domain;
    }

    protected EmailAddress() {
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return username + "@" + domain;
    }
}
