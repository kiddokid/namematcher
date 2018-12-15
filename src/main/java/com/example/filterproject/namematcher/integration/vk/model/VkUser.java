package com.example.filterproject.namematcher.integration.vk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "vkuser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VkUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long riskcustomerid;

    //private Integer[] possibleids;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> possibleids;

    private Integer preferableid;

    private Integer totalfriendscount;

    //private Integer[] friends;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> friends;

    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @UpdateTimestamp
    private ZonedDateTime dateModified;
}
