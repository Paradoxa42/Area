package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActionReaction
{

    public ActionReaction() {}

    public Long id_action;

    public Long id_reaction;

    public Long id_client;
}