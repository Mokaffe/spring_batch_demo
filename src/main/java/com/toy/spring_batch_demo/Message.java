package com.toy.spring_batch_demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(name = "content")
    private String content;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public Message(String objectId, String content) {
        this.objectId = objectId;
        this.content = content;
    }

    public String getObjectId() {
        return objectId;
    }
}


// 假设我们迁移的数据是Message，那么我们就需要提前创建一个叫Message的数据库映射的数据类。