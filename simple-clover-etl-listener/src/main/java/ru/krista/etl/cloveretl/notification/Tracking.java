package ru.krista.etl.cloveretl.notification;

import org.jetel.graph.TransformationGraph;
import org.jetel.graph.runtime.jmx.CloverJMX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationListener;

public class Tracking {
    private static final Logger loggger = LoggerFactory.getLogger(Tracking.class);
    public static void track(CloverJMX cloverJMX, TransformationGraph graph){
        System.out.println("Holy Shit I was instrumented");
        TrackingInternal trackingInternal = new TrackingInternal(cloverJMX, graph);
    }


    private static class TrackingInternal implements NotificationListener {
        private final CloverJMX cloverJMX;
        public TrackingInternal(CloverJMX cloverJMX, TransformationGraph graph) {
            this.cloverJMX = cloverJMX;
            cloverJMX.addNotificationListener(this, null, null);
        }

        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(CloverJMX.GRAPH_STARTED)){
                loggger.info("Notification {}", notification.getType());
            } else if (notification.getType().equals(CloverJMX.GRAPH_ABORTED)){
                loggger.info("Notification {}", notification.getType());
                removeListener(cloverJMX);
            } else if (notification.getType().equals(CloverJMX.GRAPH_ERROR)){
                loggger.info("Notification {}", notification.getType());
                removeListener(cloverJMX);
            } else if (notification.getType().equals(CloverJMX.GRAPH_FINISHED)){
                loggger.info("Notification {}", notification.getType());
                removeListener(cloverJMX);
            }else {
                loggger.info("Notification {}", notification.getType());
                printNotification(notification);
            }
        }

        private void removeListener(CloverJMX cloverJMX){
            try {
                cloverJMX.removeNotificationListener(this);
            } catch (ListenerNotFoundException e) {
                loggger.warn("Listener not found", e);
            }
        }

        private void printNotification(Notification notification){
            String type = notification.getType();
            String message = notification.getMessage();
            long sequenceNumber = notification.getSequenceNumber();
            long timeStamp = notification.getTimeStamp();
            Object userData = notification.getUserData();
            Object source = notification.getSource();
            String notification1 = "notification" +
                    type + "\n" +
                    message + "\n" +
                    sequenceNumber + "\n" +
                    timeStamp + "\n" +
                    userData + "\n" +
                    source + "\n";

            loggger.info(notification1);
        }
    }
}
