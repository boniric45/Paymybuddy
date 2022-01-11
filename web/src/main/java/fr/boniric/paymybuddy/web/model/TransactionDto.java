package fr.boniric.paymybuddy.web.model;

public class TransactionDto {

    String connections;
    String description;
    String amount;

    public TransactionDto(String connections, String description, String amount) {
        this.connections = connections;
        this.description = description;
        this.amount = amount;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
