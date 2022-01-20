package fr.boniric.paymybuddy.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Contact {

    private Integer contactId;
    private Integer usersId;

    public Contact(Integer contactId, Integer usersId) {
        this.contactId = contactId;
        this.usersId = usersId;
    }

    public Contact() {
    }
}
