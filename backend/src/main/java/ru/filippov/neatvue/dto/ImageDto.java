package ru.filippov.neatvue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    //private String name;
    private String data;

    /*@Override
    public String toString() {
        return String.format("Image name = %s, data = %s", name, data);
    }*/
}
