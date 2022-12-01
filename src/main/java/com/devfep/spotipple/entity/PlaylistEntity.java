package com.devfep.spotipple.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlists")
public class PlaylistEntity extends BaseEntity implements Serializable {
//    private static final long serialVersionUID = -8231184047563662735L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UniqueElements
    private String id;

    @Column(name = "playlist_name", nullable = false)
    private String playlistName;

    @Column(name = "number_of_tracks")
    private int NumberOfTracks;

    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = true)
    private User user;

}
