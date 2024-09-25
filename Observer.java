//behavioural pattern

import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(float temperature);
}

class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    private float temperature;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature);
        }
    }
}

class DisplayScreen implements Observer {
    @Override
    public void update(float temperature) {
        System.out.println("Display Screen: Current temperature is " + temperature + "°C");
    }
}

// Usage
public class ObserverPatternDemo {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        DisplayScreen displayScreen = new DisplayScreen();

        weatherStation.attach(displayScreen);
        weatherStation.setTemperature(25);
    }
}
