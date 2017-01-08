package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public Client() {}

    public Long id_client;

    public Date last_login;

    public Boolean connected;

    public String token;
}