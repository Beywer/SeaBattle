package ru.ssau.seabattle.core;

import java.io.Serializable;

public class Shoot implements Serializable {
	
	private static final long serialVersionUID = -8859213211044404244L;
	
	private int x,y;
	private String shooterName;
	private ShootState state;
	
	public Shoot(int x, int y, String shooterName, ShootState state) {
		super();
		this.x = x;
		this.y = y;
		this.shooterName = shooterName;
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getShooterName() {
		return shooterName;
	}

	public ShootState getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return shooterName + " " + (char)(x + 'A') + (y+1) + " " + state;
	}
	
}
