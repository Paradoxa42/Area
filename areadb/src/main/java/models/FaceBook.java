package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FaceBook
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public FaceBook() {}

    public Long id_facebook;

    public Long id_client;
}