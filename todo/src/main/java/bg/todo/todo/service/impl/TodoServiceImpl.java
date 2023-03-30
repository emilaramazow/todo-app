package bg.todo.todo.service.impl;

import bg.todo.todo.model.dto.TodoDTO;
import bg.todo.todo.model.entity.Todo;
import bg.todo.todo.repository.TodoRepository;
import bg.todo.todo.service.TodoService;
import bg.todo.todo.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public List<TodoDTO> getAllTodos() {
        return todoRepository
                .findAll()
                .stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("TODO with with id: %d does not exists!", id));
    }

    @Override
    public Todo createTodo(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);

        todo.setUser(userService.findById(todoDTO.getUser().getId()));
        todo.setStatus(todoDTO.getStatus());

        return todoRepository.save(todo);
    }

    @Override
    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Todo with id: %d was not found!", id));

        todo.setName(todoDTO.getName());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());
        todo.setStatus(todoDTO.getStatus());

        todoRepository.save(todo);

        return new TodoDTO(todo.getName(), todo.getDescription(), todo.isCompleted(), todo.getStatus(), todo.getUser());
    }

    @Override
    public boolean deleteTodoById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            todoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
