package com.nstanogias.usagecounter.models;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;
    private Long usage = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    private String fromDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String toDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    private String product = "analytics";

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, License.class);
    }
}
