package org.springboot.taskmanager.controller;

import org.springboot.taskmanager.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface Controller<T, R> {

    public ResponseEntity<R>  create(T requestBody);
    public ResponseEntity<R> update(Long id, T requestBody);
    public ResponseEntity<R> delete(Long id);
    public ResponseEntity<R> get(Long id);
    public ResponseEntity<List<R>>  getAll();
}
