package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.ObjectWithId;

import java.util.Collection;

public interface CrudCollection<Element extends ObjectWithId> {
    Collection<Element> readAll();
    Element read(Long key);
    void update(Long key, Element element);
    void delete(Long key);
    void create(Element element);
}
