package view;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------
 * GanttDemo1.java
 * ---------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: GanttDemo1.java,v 1.12 2004/04/26 19:11:54 taqua Exp $
 *
 * Changes
 * -------
 * 06-Jun-2002 : Version 1 (DG);
 * 10-Oct-2002 : Modified to use DemoDatasetFactory (DG);
 * 10-Jan-2003 : Renamed GanttDemo --> GanttDemo1 (DG);
 * 16-Oct-2003 : Shifted dataset from DemoDatasetFactory to this class (DG);
 *
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;

import model.Activity;
import model.Project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a Gantt chart.
 * <P>
 * This demo is intended to show the conceptual approach rather than being a polished
 * implementation.
 *
 *
 */
public class GanttView extends JFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
	Project proj;
	
    public GanttView(final String title, Project parentProject) {

        super(title);
        setProj(parentProject);
        
        final IntervalCategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset, title);
        
        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(950, 500)); //changes size of window
        setContentPane(chartPanel);
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a sample dataset for a Gantt chart.
     *
     * @return The dataset.
     */
    public IntervalCategoryDataset createDataset() {
    	
    	
  		/*Task is polymorphic : Task(java.lang.String description, java.util.Date start, java.util.Date end) 	 
    	Creates a new task.
    	Task(java.lang.String description, TimePeriod duration)
    	Creates a new task. */
    	
    	ArrayList<Activity> projActs =  getProj().getProjectActivities();
    	

        final TaskSeries s1 = new TaskSeries("Scheduled");
        
    	for(Activity act: projActs){
    		Calendar start = getStartDate(act.getStartDate());
    		int year = start.get(Calendar.YEAR);
    		int month = start.get(Calendar.MONTH);
    		int day = start.get(Calendar.DAY_OF_MONTH);
    		
    		Calendar end = getStartDate(act.getFinishDate());
    		int endyear = end.get(Calendar.YEAR);
    		int endmonth = end.get(Calendar.MONTH);
    		int endday = end.get(Calendar.DAY_OF_MONTH);
    		
    		s1.add(new Task(act.getName(),
    	               new SimpleTimePeriod(date(day, month, year),
    	            		   date(endday, endmonth, endyear))));
    	}
 
    	TaskSeriesCollection collection = new TaskSeriesCollection();
        
        collection.add(s1);
        
        return collection;
    }

    /**
     * Utility method for creating <code>Date</code> objects.
     *
     * @param day  the date.
     * @param month  the month.
     * @param year  the year.
     *
     * @return a date.
     */
    private Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;

    }
        
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final IntervalCategoryDataset dataset, String title) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            title,  // chart title
            "ACTIVITY",              // domain axis label
            "DATE",              // range axis label
            dataset,             // data
            true,                // include legend
            false,                // tooltips
            false                // urls
        );    
        return chart;    
    }
    
    public Calendar getStartDate(String startDate){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.CANADA);
		try {
			cal.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done

		return cal;
	}

	public Project getProj() {
		return proj;
	}

	public void setProj(Project proj) {
		this.proj = proj;
	}
    
    
}

           
       