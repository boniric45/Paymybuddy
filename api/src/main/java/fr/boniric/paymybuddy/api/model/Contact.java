package fr.boniric.paymybuddy.api.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "contact", schema = "public")
@NoArgsConstructor
public class Contact {

    @Id
    @Column(name = "contact_id")
     Integer contactId;

    @Column(name = "users_id")
     Integer usersId;

    public Contact(int contactId, int usersId) {
        this.contactId = contactId;
        this.usersId = usersId;
    }



}
