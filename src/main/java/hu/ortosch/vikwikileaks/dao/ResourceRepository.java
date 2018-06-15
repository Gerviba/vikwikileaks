package hu.ortosch.vikwikileaks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ortosch.vikwikileaks.model.ResourceEntity;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    public List<ResourceEntity> findAllBySubjectOrderByDate(String subject);

}
