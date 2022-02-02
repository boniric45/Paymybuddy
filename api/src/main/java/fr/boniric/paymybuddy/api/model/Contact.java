package fr.boniric.paymybuddy.api.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "contact", schema = "public")
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    Integer contactId;

    @Column(name = "users_id")
    Integer usersId;

    @Column(name = "buddy_id")
    Integer buddyId;

}
