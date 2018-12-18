package com.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ex.beans.game.Dual;
import com.ex.beans.game.HighScorePlayerBean;



@Repository("HighScoreBeanRepository")
public interface HighScoreBeanRepository extends JpaRepository<HighScorePlayerBean, Integer> {

	//public List <HighScorePlayerBean> findAll();
	//<S extends T> Iterable<S> saveAll(Iterable<S> entities)
	//<S extends T> S save(S entity)

	HighScorePlayerBean save(HighScorePlayerBean persisted);

 //   public int modifyingQueryInsertPerson(@Param("id")Long id, @Param("name")String name, @Param("age")Integer age);
  //  @Modifying
   // @Query("insert into HighScorePlayerBean (id,max_streak,number_correct,score,username) select :id,:max_streak,:number_correct,:score,:username from Dual")
	
	
    
    @Modifying
    @Query("insert into HighScorePlayerBean (id,max_streak,number_correct,score,username) select :id,:max_streak,:number_correct,:score,:username from Dual")
    public int modifyingQueryInsertScore(@Param("id")int id, @Param("max_streak")int max_streak, @Param("number_correct")int number_correct, @Param("score")int score, @Param("username")String username);
	
	
}
