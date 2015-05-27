package com.satish.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscPlayer {
	
	private CompactDisc compactDisc;

	public CompactDisc getCompactDisc() {
		return compactDisc;
	}
	
	@Autowired
	public void setCompactDisc(CompactDisc compactDisc) {
		this.compactDisc = compactDisc;
	}
	
	public void play(){
		compactDisc.play();
	}
	

}
