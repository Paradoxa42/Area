package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Google
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public Google() {}

    public Long id_google;

    public Long id_client;
}