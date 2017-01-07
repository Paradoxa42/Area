package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Gabriel on 06/01/2017.
 */

@Entity
public class Client
{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public Date last_login;

    public Boolean connected;

    public String token;

    public Client ()  {}
}