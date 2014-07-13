package com.kchodorow.gamegame.game;

import java.util.ArrayList;

import android.content.Context;
import android.widget.RelativeLayout;

public class GameView extends RelativeLayout {
	public GameView(Context context) {
		super(context);
		
	}

	private ArrayList<RandomGenerator> randomGenerators = new ArrayList<RandomGenerator>();
	
	public void addRandomGenerator(RandomGenerator generator) {
		randomGenerators.add(generator);
	}
}
