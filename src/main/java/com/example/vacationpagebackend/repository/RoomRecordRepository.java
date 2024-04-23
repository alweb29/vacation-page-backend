package com.example.vacationpagebackend.repository;

import com.example.vacationpagebackend.entity.RoomRecordEntity;
import com.example.vacationpagebackend.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRecordRepository extends JpaRepository<RoomRecordEntity, Long> {

    Optional<RoomRecordEntity> findRoomRecordByMonthNum(int monthNum);
    Optional<RoomRecordEntity> findRoomRecordByRoomType(RoomType roomType);
    Optional<List<RoomRecordEntity>> findRoomRecordEntitiesByMonthNumAndRoomType(int monthNum, RoomType roomType);
}
