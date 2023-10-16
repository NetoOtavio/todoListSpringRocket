package com.todolist.todolistAPI.tasks;

import com.todolist.todolistAPI.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){

        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var currentData = LocalDateTime.now();

        if(currentData.isAfter(taskModel.getStartAt())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início da tarefa deve ser anterior a data atual!");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início da tarefa deve ser anterior a data de término!");
        }

        var taskCreated = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){

        var userId = request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId((UUID)userId);

        return tasks;
    }

    //http://localhosto:8080/taks/exemple
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){

        var task = this.taskRepository.findById(id).orElse(null);
        var userId = request.getAttribute("userId");

        if(task == null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tarefa inexistente!");
        }

        if(!task.getUserId().equals(userId)){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sem autoriação para editar essa tarefa!");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }
}
