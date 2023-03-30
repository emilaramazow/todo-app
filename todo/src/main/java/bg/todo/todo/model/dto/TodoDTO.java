package bg.todo.todo.model.dto;

import bg.todo.todo.enums.Status;
import bg.todo.todo.model.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    @NotNull
    private String name;
    @NotNull
    @Size(min = 5)
    private String description;
    @NotNull
    private boolean completed;
    @NotNull
    private Status status;

    private User user;
}
