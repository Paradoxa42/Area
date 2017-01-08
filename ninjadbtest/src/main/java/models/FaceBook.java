package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FaceBook
{

    public FaceBook() {}

    public Long id_facebook;

    public Long id_client;
}