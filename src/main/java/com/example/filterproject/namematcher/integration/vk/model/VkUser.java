package com.example.filterproject.namematcher.integration.vk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(schema = "namematching", name = "vkuser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VkUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riskcustomerid;

    private Long[] possibleids;

    private Long preferableid;

    private Integer totalfriendscount;

    private Long[] friends;

    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @UpdateTimestamp
    private ZonedDateTime dateModified;
}
