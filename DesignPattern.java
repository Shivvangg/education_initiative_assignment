import java.util.ArrayList;
import java.util.List;

// Observer Pattern

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

// Observer Pattern Demo
public class ObserverPatternDemo {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        DisplayScreen displayScreen = new DisplayScreen();

        weatherStation.attach(displayScreen);
        weatherStation.setTemperature(25);
    }
}

// Strategy Pattern

interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

class ShoppingCart {
    private List<Integer> items = new ArrayList<>();
    private PaymentStrategy paymentStrategy;

    public void addItem(int item) {
        items.add(item);
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout() {
        int total = items.stream().mapToInt(Integer::intValue).sum();
        if (paymentStrategy != null) {
            paymentStrategy.pay(total);
        }
    }
}

// Strategy Pattern Demo
public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(50);
        cart.addItem(150);
        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout();
    }
}

// Singleton Pattern

class Logger {
    private static Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println(message);
    }
}

// Singleton Pattern Demo
public class SingletonPatternDemo {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        logger1.log("This is the first log message.");

        Logger logger2 = Logger.getInstance();
        logger2.log("This is the second log message.");

        System.out.println(logger1 == logger2); // Both should be the same instance
    }
}

// Factory Method Pattern

interface Notification {
    void send();
}

class EmailNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending Email Notification.");
    }
}

class SMSNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending SMS Notification.");
    }
}

class NotificationFactory {
    public static Notification createNotification(String type) {
        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SMSNotification();
            default:
                throw new IllegalArgumentException("Unknown notification type.");
        }
    }
}

// Factory Method Pattern Demo
public class FactoryMethodPatternDemo {
    public static void main(String[] args) {
        Notification notification = NotificationFactory.createNotification("email");
        notification.send();
    }
}

// Adapter Pattern

interface MediaPlayer {
    void play(String audioType, String filename);
}

class AudioPlayer implements MediaPlayer {
    @Override
    public void play(String audioType, String filename) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing MP3 file: " + filename);
        } else if (audioType.equalsIgnoreCase("wav")) {
            System.out.println("Playing WAV file: " + filename);
        } else {
            System.out.println("Invalid audio type.");
        }
    }
}

interface AdvancedMediaPlayer {
    void playVlc(String filename);
    void playMp4(String filename);
}

class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String filename) {
        System.out.println("Playing VLC file: " + filename);
    }

    @Override
    public void playMp4(String filename) {
        // Do nothing
    }
}

class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String filename) {
        // Do nothing
    }

    @Override
    public void playMp4(String filename) {
        System.out.println("Playing MP4 file: " + filename);
    }
}

class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String filename) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer.playVlc(filename);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer.playMp4(filename);
        }
    }
}

// Adapter Pattern Demo
public class AdapterPatternDemo {
    public static void main(String[] args) {
        MediaPlayer player = new AudioPlayer();
        player.play("mp3", "song.mp3");

        // Using MediaAdapter to play VLC and MP4 files
        MediaPlayer vlcPlayer = new MediaAdapter("vlc");
        vlcPlayer.play("vlc", "video.vlc");

        MediaPlayer mp4Player = new MediaAdapter("mp4");
        mp4Player.play("mp4", "movie.mp4");
    }
}
