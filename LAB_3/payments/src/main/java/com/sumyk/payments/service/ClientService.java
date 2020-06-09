package com.sumyk.payments.service;

import com.sumyk.payments.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class ClientService {

    // Сховище клієнтів
    private static final Map<Integer, Client> CLIENT_REPOSITORY_MAP = new HashMap<>();

    // Змінна для генерації ID клієнта
    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();


    public void create(Client client) {
        final int clientId = CLIENT_ID_HOLDER.incrementAndGet();
        client.setId(clientId);

        CLIENT_REPOSITORY_MAP.put(clientId, client);
    }

    public List<Client> readAll() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    public Client read(int id) {
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    public boolean update(Client client, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {
            client.setId(id);
            CLIENT_REPOSITORY_MAP.put(id, client);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}
