package com.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.beans.game.HighScorePlayerBean;
import com.ex.beans.game.PlayerBean;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<HighScorePlayerBean, Integer> {
	
	public PlayerBean findByUsernameLikeIgnoreCase(String username);
	
	//@Query("SELECT * FROM GLOBALSTATS")
	//public List<HighScoreBean> oddQuery();

	public List<HighScorePlayerBean> findAll();
	
	public HighScorePlayerBean save(HighScorePlayerBean persisted);
	
}
