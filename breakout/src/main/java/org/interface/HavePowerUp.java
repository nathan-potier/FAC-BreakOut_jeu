package org.openjfx;


public interface HavePowerUp {
    
    public boolean isActive();
    
    public boolean isTimeUp(double timer);

    public double updateTimer(double timer);
}
