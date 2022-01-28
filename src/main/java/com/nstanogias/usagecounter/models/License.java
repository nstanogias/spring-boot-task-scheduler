package com.nstanogias.usagecounter.models;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class License {

  @Version
  private Long version;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long key;
  private LocalDateTime lockedUntil;
  private Long currentUsage;
  private String fromDate = LocalDateTime.now()
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  private String toDate = LocalDateTime.now()
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  private String product = "analytics";

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this, License.class);
  }
}
