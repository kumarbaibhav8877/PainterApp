package com.example.painterapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.painterapp.entity.PointsHistory;

public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Long> {
    List<PointsHistory> findByPainterCodeOrderByDateDesc(String painterCode);
}

