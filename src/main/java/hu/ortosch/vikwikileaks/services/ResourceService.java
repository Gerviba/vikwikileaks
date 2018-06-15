package hu.ortosch.vikwikileaks.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.ortosch.vikwikileaks.dao.ResourceRepository;
import hu.ortosch.vikwikileaks.model.ResourceEntity;

@Service
@Transactional
public class ResourceService {

    @Autowired
    ResourceRepository repo;
    
    public void save(ResourceEntity resource) {
        repo.save(resource);
    }
    
    public List<ResourceEntity> getAllByCourse(String subject) {
        return repo.findAllBySubjectOrderByDate(subject);
    }
    
    public ResourceEntity getById(Long id) {
        return repo.findById(id).orElse(null);
    }
    
}
