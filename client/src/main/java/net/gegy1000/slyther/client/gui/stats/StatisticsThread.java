/**
 * 
 */
package net.gegy1000.slyther.client.gui.stats;

import org.jfree.chart.JFreeChart;

import net.gegy1000.slyther.client.db.Database;

/**
 * @author dick
 *
 */
public class StatisticsThread extends Thread {

	private	boolean chartReady = false;
	private JFreeChart chart;
	private Database database;

	public StatisticsThread(Database database) {
		this.database = database;
	}
	
	/**
	 * @return the chartReady
	 */
	public synchronized boolean isChartReady() {
		return chartReady;
	}
	/**
	 * @param chartReady the chartReady to set
	 */
	private synchronized void setChartReady(boolean chartReady) {
		this.chartReady = chartReady;
	}
	
	
	/**
	 * @return the chart
	 */
	public synchronized JFreeChart getChart() {
		return chart;
	}

	@Override
	public void run() {
		StatisticsGraph sg = new StatisticsGraph();
		chart = sg.getChart(database);
		setChartReady(true);
		
	}
}
