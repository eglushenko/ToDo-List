package com.todolist.ToDo.List.repo;

import com.todolist.ToDo.List.enums.TaskStatusEnum;
import com.todolist.ToDo.List.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface TaskRepo extends CrudRepository<Task,Long> {
    List<Task> findByName(String name);
    Set<Task> getTasksByStatus(TaskStatusEnum status);
}
