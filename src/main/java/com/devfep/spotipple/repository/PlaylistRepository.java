package com.devfep.spotipple.repository;

import com.devfep.spotipple.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //annotation not needed because JPArepo has it already
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
}
