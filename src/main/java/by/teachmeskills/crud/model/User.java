package by.teachmeskills.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends Entity {
    private int id;
    @NonNull private String name;
    @NonNull private String email;
}