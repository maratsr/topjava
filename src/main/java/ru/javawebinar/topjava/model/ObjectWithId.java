package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicLong;

public class ObjectWithId {
    protected long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
