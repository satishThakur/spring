package com.satish.soundsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class DiscPlayerTest {
	
	@Autowired
	private DiscPlayer discPlayer;
	
	
	@Test
	public void testDiscPlayerIsNotNull(){
		assertNotNull(discPlayer);
	}
	
	@Test
	public void testPlay(){
		discPlayer.play();
	}

}
