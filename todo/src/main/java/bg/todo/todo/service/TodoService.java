package bg.todo.todo.service;

import bg.todo.todo.model.dto.TodoDTO;
import bg.todo.todo.model.entity.Todo;

import java.util.List;

public interface TodoService {
    List<TodoDTO> getAllTodos();

    Todo getTodoById(Long id);

    Todo createTodo(TodoDTO todoDTO);

    TodoDTO updateTodo(Long id, TodoDTO todoDTO);

    boolean deleteTodoById(Long id);
}
