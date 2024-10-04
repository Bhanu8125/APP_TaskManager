package org.springboot.taskmanager.service;


import org.springboot.taskmanager.exception.TaskNotFoundException;
import org.springboot.taskmanager.exception.IllegalArgumentException;
import org.springboot.taskmanager.model.Task;
import org.springboot.taskmanager.repository.TaskRepository;
import org.springboot.taskmanager.requestbody.TaskRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements Services<TaskRequestBody, Task> {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Task create(TaskRequestBody taskRequestBody) {
        if(!taskDescriptionSizeValidation(taskRequestBody.getDescription())) throw new IllegalArgumentException("Task Description Size Should be less than 255 Characters");

        if(!dueDateValidation(taskRequestBody.getDueDate())) {
            Optional.ofNullable(taskRequestBody.getDueDate())
                    .orElseThrow(() -> new IllegalArgumentException("Task Due Date Should Not Be Null"));

            throw new IllegalArgumentException("Task Due Date Should be a Future Date");
        }

        Task createdTask = new Task(
                taskRequestBody.getName(),
                taskRequestBody.getDescription(),
                taskRequestBody.getPriority(),
                taskRequestBody.getStatus(),
                taskRequestBody.getDueDate()
        );

        return taskRepository.save(createdTask);
    }

    @Override
    public Task update(Long id, TaskRequestBody taskRequestBody) {
        return taskRepository.findById(id).map((task)-> {
            task.setName(taskRequestBody.getName());
            if(taskRequestBody.getDescription()!=null) taskRequestBody.setDescription(taskRequestBody.getDescription());
            task.setPriority(taskRequestBody.getPriority());
            task.setDueDate(taskRequestBody.getDueDate());
            task.setStatus(taskRequestBody.getStatus());
            return taskRepository.save(task);
        }).orElseThrow(()->  new TaskNotFoundException("Task with Id " + id +" not found"));
    }



    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task get(Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("Task with Id " + id + " not found"));
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    private boolean taskDescriptionSizeValidation(String description) {
        return (description == null) || (description.length() <= 255);
    }

    private boolean dueDateValidation(LocalDate dueDate) {
        return dueDate != null && !dueDate.isBefore(LocalDate.now()) && !dueDate.isEqual(LocalDate.now());
    }
}
