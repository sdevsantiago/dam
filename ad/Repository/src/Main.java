import java.util.List;

import clients.Client;
import clients.parameters.AccountStatus;
import clients.repository.ClientRepository;

public class Main {
    public static void main(String[] args) {
        ClientRepository clientRepository = ClientRepository.getInstance("data/clients.csv");
        List<Client> clients = clientRepository.findAllToList();

        for (Client client : clients) {
            System.out.printf("Username: %s\nEmail: %s\nPassword (hashed): %d\n--------------------------------------------------\n",
                client.getUsername(),
                client.getEmail(),
                client.getPassword());
        }

        System.out.println("Total clients: " + clientRepository.count());

        List<Client> newClients = List.of(
            new Client("frank", "frank@example.com", "password6*", AccountStatus.ACTIVE),
            new Client("grace", "grace@example.com", "password7*", AccountStatus.ACTIVE),
            new Client("henry", "henry@example.com", "password8*", AccountStatus.ACTIVE)
        );

        newClients.forEach(clientRepository::save);
    }
}
