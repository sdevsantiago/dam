import java.util.List;

import clients.Client;
import clients.parameters.ClientStatus;
import clients.repository.ClientRepository;
import products.Product;
import products.repository.ProductRepository;

public class Main {
    public static void main(String[] args) {
        final ClientRepository clientRepository = ClientRepository.getInstance("data/clients.csv");
        final ProductRepository productRepository = ProductRepository.getInstance("data/products.csv");
        final List<Client> clients = clientRepository.findAllToList();
        final List<Product> products = productRepository.findAllToList();

        clients.get(0).setStatus(ClientStatus.ACTIVE);
        clientRepository.save(clients.get(0));

        System.out.println("Clients in repository:");
        for (Client client : clients) {
            System.out.println("- " + client.getUsername() + " (" + client.getEmail() + ") - Status: " + client.getStatus());
        }

        System.out.println();

        System.out.println("Products in repository:");
        for (Product product : products) {
            System.out.println("- " + product.getName() + " - Price: $" + product.getCurrentPrice() + " - Stock: " + product.getStock());
        }
    }

    private static void addOrUpdateSampleClients() {
        ClientRepository clientRepository = ClientRepository.getInstance("data/clients.csv");

        List<Client> newClients = List.of(
            new Client("frank", "frank@example.com", "frank1*", ClientStatus.ACTIVE),
            new Client("grace", "grace@example.com", "grace1*", ClientStatus.ACTIVE),
            new Client("henry", "henry@example.com", "henry1*", ClientStatus.ACTIVE)
        );

        newClients.forEach(clientRepository::save);

        // update if already exists or add a new one
        Client newClient = new Client("isabel", "isabel@example.com", "isabel1*", ClientStatus.ACTIVE);
        clientRepository.save(newClient);
    }

    private static void addExistingClient() throws IllegalStateException, IllegalArgumentException {
        ClientRepository clientRepository = ClientRepository.getInstance();

        // attempt to add an existing client
        Client existingClient = clientRepository.findAllToList().get(0);
        clientRepository.add(existingClient);
    }


    private static void addSampleProducts() {
        ProductRepository productRepository = ProductRepository.getInstance("data/products.csv");

        List<Product> newProducts = List.of(
            new Product(1006L, "Tablet", 299.99, "Electronics", 15),
            new Product(1007L, "Smartwatch", 199.99, "Electronics", 25),
            new Product(1008L, "Wireless Earbuds", 149.99, "Electronics", 30)
        );

        newProducts.forEach(productRepository::save);
    }
}
