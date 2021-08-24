package fr.boniric.paymybuddy.web;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
class WebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getConnectionBdd(){

				try
				{
					//étape 1: charger la classe de driver
					Class.forName("org.postgresql.Driver");

					//étape 2: créer l'objet de connexion
					Connection conn = DriverManager.getConnection(
							"jdbc:postgresql://localhost:5432/postgres","postgres","root");

					//étape 3: créer l'objet statement
					Statement stmt = conn.createStatement();
					ResultSet res = stmt.executeQuery("SELECT * FROM users");

					//étape 4: exécuter la requête
					while(res.next())
						System.out.println(res.getInt(1)+"  "+res.getString(2)
								+"  "+res.getString(3));

					//étape 5: fermez l'objet de connexion
					conn.close();
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
		}
