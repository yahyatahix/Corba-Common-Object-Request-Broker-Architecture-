// CounterDelegate.java
  import javax.swing.*;
  public class CounterDelegate extends JPanel
      implements CounterOperations {
    private int count;
    private JTextField value;
    public CounterDelegate() {
      count = 0;
      add(new JLabel("Counter value: ", JLabel.RIGHT));
      add(value =
        new JTextField((String.valueOf(count)), 10));
      value.setEditable(false);
    }
    public void inc() {
      value.setText(String.valueOf(++count));
    }
    public void dec() {
      value.setText(String.valueOf(--count));
    }
    public int value() {
      return count;
    }
}