package hu.ortosch.vikwikileaks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ortosch.vikwikileaks.model.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    public List<CommentEntity> findAllBySubjectOrderByParentIdDescDateAsc(String subject);
    
}
