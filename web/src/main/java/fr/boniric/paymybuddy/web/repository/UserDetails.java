package fr.boniric.paymybuddy.web.repository;

import java.io.Serializable;

public interface UserDetails extends Serializable {

    String getUsername();

    String getPassword();
}
