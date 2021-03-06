package com.timgroup.tucker.demo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.timgroup.tucker.info.status.StatusPageGenerator;
import com.timgroup.tucker.info.component.ThresholdedGaugeComponent;
import com.timgroup.tucker.info.component.JarVersionComponent;
import com.timgroup.tucker.info.servlet.ApplicationInformationServlet;
import com.yammer.metrics.core.Gauge;

@SuppressWarnings("serial")
public class DemoStatusPageServlet extends ApplicationInformationServlet {
    
    public DemoStatusPageServlet() {
        super(statusPage());
    }
    
    private static StatusPageGenerator statusPage() {
        final StatusPageGenerator statusPage = new StatusPageGenerator("demoApp", new JarVersionComponent(StatusPageGenerator.class));
        statusPage.addComponent(new ThresholdedGaugeComponent<Integer>("timeUsed", "Time used this minute (sec)", timeUsedGauge(), 30, 50));
        return statusPage;
    }
    
    private static Gauge<Integer> timeUsedGauge() {
        return new Gauge<Integer>() {
            @Override
            public Integer value() {
                return (int) (System.currentTimeMillis() / 1000) % 60;
            }
        };
    }
    
}
