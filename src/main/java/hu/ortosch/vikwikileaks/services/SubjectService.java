package hu.ortosch.vikwikileaks.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.ortosch.vikwikileaks.dao.SubjectRepository;
import hu.ortosch.vikwikileaks.model.SubjectEntity;

@Service
@Transactional
public class SubjectService {

    @Autowired
    SubjectNameGetter subjects;
    
    @Autowired
    SubjectRepository repo;
    
    public SubjectEntity getById(String id) {
        return repo.getOne(id);
    }
    
    public SubjectEntity getByIdOrNull(String id) {
        return repo.findById(id).orElse(null);
    }
    
    public List<SubjectEntity> getAll(Iterable<String> ids) {
        return repo.findAllById(ids);
    }
    
    public void checkIfExists(Iterable<String> ids) {
        for (String id : ids) {
            SubjectEntity subject = repo.findById(id).orElse(null);
            if (subject == null)
                repo.save(new SubjectEntity(id, subjects.getSubjectName(id)));
        }
    }

    public void save(SubjectEntity subjectEntity) {
        repo.save(subjectEntity);
    }
    
}
