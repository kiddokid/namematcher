package com.example.filterproject.namematcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "system_property", schema = "namematching")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemProperty {

    @Id
    private String property;
    private String value;
}
