
package com.explore.exploreeasternindia.guide.repository;

import com.explore.exploreeasternindia.guide.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findByDistrictIgnoreCase(String district);
    List<Guide> findByLanguagesContainingIgnoreCase(String language);
    List<Guide> findBySpecializationContainingIgnoreCase(String specialization);
    List<Guide> findByVerifiedTrue();
    List<Guide> findByDistrictIgnoreCaseAndVerifiedTrue(String district);
}
