package com.codecool.basictodolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Todo {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isComplete() {
        return this.status == Status.COMPLETE;
    }


}
