package fr.boniric.paymybuddy.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "users_id")
    private Long usersId;

}
