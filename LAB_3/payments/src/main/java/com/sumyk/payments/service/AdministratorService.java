package com.sumyk.payments.service;

import com.sumyk.payments.model.Administrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class AdministratorService {

    private static final Map<Integer, Administrator> ADMIN_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger ADMIN_ID_HOLDER = new AtomicInteger();

    public void create(Administrator admin) {
        final int adminId = ADMIN_ID_HOLDER.incrementAndGet();
        admin.setId(adminId);

        ADMIN_REPOSITORY_MAP.put(adminId, admin);
    }

    public List<Administrator> readAll() {
        return new ArrayList<>(ADMIN_REPOSITORY_MAP.values());
    }

    public Administrator read(int adminId) {
        return ADMIN_REPOSITORY_MAP.get(adminId);
    }

    public boolean update(Administrator admin, int adminId) {
        if (ADMIN_REPOSITORY_MAP.containsKey(adminId)) {
            admin.setId(adminId);
            ADMIN_REPOSITORY_MAP.put(adminId, admin);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        return ADMIN_REPOSITORY_MAP.remove(id) != null;
    }

}
