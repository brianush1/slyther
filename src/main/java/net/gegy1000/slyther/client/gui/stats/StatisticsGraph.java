/**
 * 
 */
package net.gegy1000.slyther.client.gui.stats;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.util.Log;

import net.gegy1000.slyther.client.db.Database;
import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.util.Log;

/**
 * @author dick
 *
 */
public class StatisticsGraph {

	public StatisticsGraph() {
	}

	JFreeChart getChart(Database database) {
		List<GameStatistic> gsl = database.getGames();
		//String series1 = "Score";
		//String series2 = "Rank";
		//String series3 = "Kills";
		//String series4 = "Time";

		//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		XYSeries series1 = new XYSeries("Score");
		XYSeries series2 = new XYSeries("Kills");
		XYSeries series3 = new XYSeries("Rank");
		XYSeries series4 = new XYSeries("Duration");
		int xaxis = gsl.size();
		for (GameStatistic gs : gsl) {
			series1.add(xaxis, gs.getLength());
			series2.add(xaxis, gs.getKills());
			series3.add(xaxis, gs.getRank());
			series4.add(gs.getDuration(), gs.getGamedate().getTime());
//			dataset.addValue(gs.getLength(), series1, gs.getGamedate());
//			dataset.addValue(gs.getRank(), series2, gs.getGamedate());
//			dataset.addValue(gs.getKills(), series3, gs.getGamedate());
//			dataset.addValue(gs.getDuration(), series4, gs.getGamedate());
			xaxis--;
		}
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		XYSeriesCollection dataset3 = new XYSeriesCollection();
//		XYSeriesCollection dataset4 = new XYSeriesCollection();
		dataset1.addSeries(series1);
		dataset2.addSeries(series2);
		dataset3.addSeries(series3);
//		dataset4.addSeries(series4);

		Log.debug("Kills low/high = {} / {}", dataset2.getDomainLowerBound(false), dataset2.getDomainUpperBound(false));
		//construct the plot
		XYPlot plot = new XYPlot();
		plot.setDataset(0, dataset1);
		plot.setDataset(1, dataset2);
		plot.setDataset(2, dataset3);
//		plot.setDataset(3, dataset4);

 		//customize the plot with renderers and axis
		XYSplineRenderer splinerenderer = new XYSplineRenderer();
		splinerenderer.setSeriesFillPaint(0, Color.RED);
		plot.setRenderer(0, splinerenderer);
		splinerenderer = new XYSplineRenderer();
		splinerenderer.setSeriesFillPaint(0, Color.BLUE);
		plot.setRenderer(1, splinerenderer);
		splinerenderer = new XYSplineRenderer();
		splinerenderer.setSeriesFillPaint(2, Color.GREEN);
		plot.setRenderer(2, splinerenderer);
//		splinerenderer = new XYSplineRenderer();
//		splinerenderer.setSeriesFillPaint(0, Color.BLACK);
//		plot.setRenderer(3, splinerenderer);
		plot.setRangeAxis(0, new NumberAxis("Score"));
		NumberAxis na;
		na = new NumberAxis("Kills");
		na.configure();
		plot.setRangeAxis(1, na);
		na = new NumberAxis("Rank");
		na.setInverted(true);
		plot.setRangeAxis(2, na);
		plot.setDomainAxis(new NumberAxis());

		//Map the data to the appropriate axis
		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);

		//generate the chart
		JFreeChart chart = new JFreeChart("MyPlot", null, plot, true);
		
		// Create chart
//		JFreeChart chart = ChartFactory.createLineChart(
//				"Site Traffic (WWW.BUCKOSOFT.COM)", // Chart title
//				"Date", // X-Axis Label
//				"Number of Visitor", // Y-Axis Label
//				dataset
//				);

		//    ChartPanel panel = new ChartPanel(chart);
		//    setContentPane(panel);
		return(chart);
	}

//	private DefaultCategoryDataset createDataset(Database database) {
//
//		List<GameStatistic> gsl = database.getGames();
//		String series1 = "Score";
//		String series2 = "Rank";
//		String series3 = "Kills";
//		String series4 = "Time";
//
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//		for (GameStatistic gs : gsl) {
//			dataset.addValue(gs.getLength(), series1, gs.getGamedate());
//			dataset.addValue(gs.getRank(), series2, gs.getGamedate());
//			dataset.addValue(gs.getKills(), series3, gs.getGamedate());
//			dataset.addValue(gs.getDuration(), series4, gs.getGamedate());
//		}
//
//		return dataset;
//	}
}
