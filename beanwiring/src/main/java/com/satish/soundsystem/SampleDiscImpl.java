package com.satish.soundsystem;

import org.springframework.stereotype.Component;

@Component
public class SampleDiscImpl implements CompactDisc{

	@Override
	public void play() {
		System.out.println("Playing some sample songs...!!");
		
	}

}
