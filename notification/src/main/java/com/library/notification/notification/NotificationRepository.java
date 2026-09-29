package com.library.notification.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
     List<Notification> findByMemberId(long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n SET n.read = true WHERE n.id = :id")
    void markAsRead(@Param("id") long id);
}
