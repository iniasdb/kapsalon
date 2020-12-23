package application;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class CustomTemporalAdjuster implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {		
		return temporal.plus(Period.ofDays(this.days));
	}
	
	public void setDays(int days) {
		this.days = days;
	}
	
	public int getDays() {
		return this.days;
	}
	
	private int days = 0;
}
