package xyz.sandersonsa.kafka_sp.entity;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Mensagem {

    @JsonIgnore
    private Integer id;
    
    private String uuid;

    private String hostName;

    private String partition;

    private LocalDateTime createdAt;

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

}