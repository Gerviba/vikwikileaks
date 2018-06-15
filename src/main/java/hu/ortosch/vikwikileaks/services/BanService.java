package hu.ortosch.vikwikileaks.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.ortosch.vikwikileaks.dao.BanRepository;
import hu.ortosch.vikwikileaks.model.BlockedUserEntity;

@Service
@Transactional
public class BanService {

    @Autowired
    BanRepository repo;
    
    public String getBanMessage(String uuid) {
        BlockedUserEntity bue = repo.findById(uuid).orElse(null);
        return bue == null ? null : bue.getReason(); 
    }
    
}
