package org.springboot.taskmanager.controller;



import org.springboot.taskmanager.exception.TaskNotFoundException;
import org.springboot.taskmanager.exception.IllegalArgumentException;
import org.springboot.taskmanager.model.Task;
import org.springboot.taskmanager.requestbody.TaskRequestBody;
import org.springboot.taskmanager.responsebody.TaskResponseBody;
import org.springboot.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/task")
public class TaskController implements Controller<TaskRequestBody, TaskResponseBody> {

    private  final TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("")
    @Override
    public ResponseEntity<TaskResponseBody> create(@RequestBody TaskRequestBody taskRequestBody) {
        Task createdTask = taskService.create(taskRequestBody);

        TaskResponseBody taskResponseBody = new TaskResponseBody(
                createdTask.getId(),
                createdTask.getName(),
                createdTask.getDescription(),
                createdTask.getPriority(),
                createdTask.getStatus(),
                createdTask.getDueDate(),
                createdTask.getCreatedDate()
        );

        return ResponseEntity.ok(taskResponseBody);
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<TaskResponseBody> update(@PathVariable Long id, @RequestBody TaskRequestBody taskRequestBody) {
        Task updatedTask = taskService.update(id,taskRequestBody);

        TaskResponseBody taskResponseBody = new TaskResponseBody(
                updatedTask.getId(),
                updatedTask.getName(),
                updatedTask.getDescription(),
                updatedTask.getPriority(),
                updatedTask.getStatus(),
                updatedTask.getDueDate(),
                updatedTask.getCreatedDate()
        );
        return ResponseEntity.ok(taskResponseBody);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<TaskResponseBody> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById")
    @Override
    public ResponseEntity<TaskResponseBody> get(@RequestParam("id") Long id) {
        Task task = taskService.get(id);
        TaskResponseBody taskResponseBody = new TaskResponseBody(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedDate()
        );
        return ResponseEntity.ok(taskResponseBody);
    }

    @GetMapping("/getAll")
    @Override
    public ResponseEntity<List<TaskResponseBody>> getAll() {
        List<Task> tasks =  taskService.getAll();
        List<TaskResponseBody> taskResponseBody = tasks.stream().
                map((task) -> new TaskResponseBody(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getPriority(),
                        task.getStatus(),
                        task.getDueDate(),
                        task.getCreatedDate()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(taskResponseBody);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
