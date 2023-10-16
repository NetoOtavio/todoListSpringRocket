package com.todolist.todolistAPI.tasks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    /*
    id
    title
    description
    startAt
    endAt
    createdAt
    id(user)
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    //YYY-MM-DDTHH:MM:SS
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception{

        if(title.length() > 50){

            throw new Exception("O titulo deve ter menos de cinquenta caracteres!");
        }

        this.title = title;
    }
}
