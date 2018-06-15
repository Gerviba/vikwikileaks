package hu.ortosch.vikwikileaks.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class UserEntity implements Serializable {

    private final String name;
    private final String ownershipId;
    private final List<String> courses;
    
    public UserEntity(String name, String ownershipId, List<String> courses) {
        this.name = name;
        this.ownershipId = ownershipId;
        this.courses = courses.stream()
                .map(id -> id = id.startsWith("BME") ? id.substring(3) : id)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getOwnershipId() {
        return ownershipId;
    }

    public List<String> getCourses() {
        return courses;
    }

}
