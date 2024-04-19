package com.example.vacationpagebackend.repository;

import com.example.vacationpagebackend.entity.RoomRecord;
import com.example.vacationpagebackend.entity.RoomRecordEntity;
import com.example.vacationpagebackend.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRecordRepository extends JpaRepository<RoomRecord, String> {

    Optional<RoomRecordEntity> findRoomRecordByMonthNum(int monthNum);
    Optional<RoomRecordEntity> findRoomRecordByRoomType(RoomType roomType);
}
