package hu.ortosch.vikwikileaks.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.ortosch.vikwikileaks.dao.CommentRepository;
import hu.ortosch.vikwikileaks.model.CommentEntity;

@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository repo;
    
    public List<CommentEntity> getAll(String subject) {
        return repo.findAllBySubjectOrderByParentIdDescDateAsc(subject);
    }

    public Long addRootComment(String owner, String message, String subject, long date) {
        CommentEntity comment = new CommentEntity(owner, message, subject, date);
        repo.save(comment);
        comment.setParentId(comment.getId());
        repo.save(comment);
        return comment.getId();
    }

    public void addSubComment(String owner, String message, String subject, long date, Long parentId) {
        CommentEntity comment = new CommentEntity(owner, message, subject, date);
        comment.setParentId(parentId);
        repo.save(comment);
    }
}
