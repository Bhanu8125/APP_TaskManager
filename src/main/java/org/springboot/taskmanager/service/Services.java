package org.springboot.taskmanager.service;

import org.springboot.taskmanager.model.User;
import org.springboot.taskmanager.requestbody.UserRequestBody;

import java.util.List;

public interface Services<T, R> {

    public R create(T requestbody);
    public R update(Long id, T requestbody);


    public void delete(Long id);
    public R get(Long id);
    public List<R> getAll();
}
