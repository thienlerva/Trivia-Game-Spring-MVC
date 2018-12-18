package com.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.beans.game.HighScorePlayerBean;

@Repository("leaderboardRepository")
public interface LeaderboardRepository extends JpaRepository<HighScorePlayerBean, Integer>{

	public List<HighScorePlayerBean> findAll();
}
