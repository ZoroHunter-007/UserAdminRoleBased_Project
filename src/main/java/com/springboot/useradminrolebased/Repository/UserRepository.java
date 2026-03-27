package com.springboot.useradminrolebased.Repository;

import com.springboot.useradminrolebased.Entity.Users;
import org.springframework.boot.autoconfigure.container.ContainerImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserEmail(String userEmail);
}
