package fr.boniric.paymybuddy.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Contact {

    private Integer contactId;
    private Integer usersId;
    private Integer buddyId;
    private String buddyName;

    public Contact(Integer usersId, Integer buddyId, String buddyName) {
        this.usersId = usersId;
        this.buddyId = buddyId;
        this.buddyName = buddyName;
    }

    public Contact() {
    }
}
