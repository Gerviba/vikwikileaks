package hu.ortosch.vikwikileaks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BlockedUsers")
public class BlockedUserEntity {

    @Id
    @Column
    private String uuid;
    
    @Column
    private String reason;

    public BlockedUserEntity() {}
    
    public BlockedUserEntity(String uuid, String reason) {
        this.uuid = uuid;
        this.reason = reason;
    }

    public String getUuid() {
        return uuid;
    }

    public String getReason() {
        return reason;
    }
    
}
