package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.ObjectWithId;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCrudSortedCollection<Element extends ObjectWithId> implements CrudCollection<Element>{
    private final ConcurrentHashMap<Long, Element> elements = new ConcurrentHashMap<>();
    private AtomicLong nextId= new AtomicLong(0);

    @Override
    public Collection<Element> readAll() {
        return elements.values();
    }

    @Override
    public void update(Long key, Element element) {
        if (key != null) {
            element.setId(key);
            elements.put(key, element);
        }
    }

    @Override
    public void delete(Long key) {
        elements.remove(key);
    }

    @Override
    public void create(Element element) { // insert an element into end of map
        element.setId(nextId.incrementAndGet());
        elements.put(element.getId(), element);
    }

    @Override
    public Element read(Long key) {
        return elements.get(key);
    }
}
