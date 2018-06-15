package hu.ortosch.vikwikileaks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ortosch.vikwikileaks.model.BlockedUserEntity;

@Repository
public interface BanRepository extends JpaRepository<BlockedUserEntity, String> {

}
