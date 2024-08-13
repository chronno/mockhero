package com.chronno.mockhero.persistence.repository;

import com.chronno.mockhero.persistence.model.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockRepository extends JpaRepository<Mock, Long> {

}
