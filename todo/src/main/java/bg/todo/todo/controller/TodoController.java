package bg.todo.todo.controller;

import bg.todo.todo.model.dto.TodoDTO;
import bg.todo.todo.model.entity.Todo;
import bg.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        List<TodoDTO> todoDTOS = todoService
                .getAllTodos();

        return ResponseEntity.ok(todoDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new TodoDTO(todo.getName(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getStatus(),
                todo.getUser()));
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO todoDTO) {
        Todo todo = todoService.createTodo(todoDTO);

        return ResponseEntity.ok(new TodoDTO(todo.getName(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getStatus(),
                todo.getUser()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoDTO todoDTO) {
        TodoDTO updatedTodoDTO = todoService.updateTodo(id, todoDTO);

        if (updatedTodoDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTodoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TodoDTO> deleteTodo(@PathVariable Long id) {

        boolean isDeleted = todoService.deleteTodoById(id);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}

